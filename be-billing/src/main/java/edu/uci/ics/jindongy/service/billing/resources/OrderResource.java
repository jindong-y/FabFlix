package edu.uci.ics.jindongy.service.billing.resources;

import com.braintreepayments.http.exceptions.HttpException;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.OrderRequest;
import edu.uci.ics.jindongy.service.billing.BillingService;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.billing.models.Request.BillingRequest;
import edu.uci.ics.jindongy.service.billing.models.Response.OrderRetrieveResponse;
import edu.uci.ics.jindongy.service.billing.models.Response.PlaceResponse;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.base.Results;
import edu.uci.ics.jindongy.service.billing.models.data.transactionModel;
import edu.uci.ics.jindongy.service.billing.resources.PayPal.PayPalOrderClient;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Path("order")
public class OrderResource {

    @Context
    HttpHeaders headers;

//    @GET
//    @Path("test")
//    public void test() throws InterruptedException {
//        Thread.sleep(3000);
//        ServiceLogger.LOGGER.info(headers.getHeaderString("email"));
//    }
//
//    @GET
//    @Path("test2")
//    public void test2() throws InterruptedException {
//        ServiceLogger.LOGGER.info(headers.getHeaderString("email"));
//    }
    @POST
    @Path("place")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response place(BillingRequest args) throws Throwable {

        String email = args.email;


        //Get total price
        String priceQ = "SELECT ROUND(SUM(unit_price*quantity*(1-discount)),2) as total\n" +
                "FROM cart\n" +
                "LEFT JOIN movie_price mp on cart.movie_id = mp.movie_id\n" +
                "WHERE email=?\n;";
        PreparedStatement ps = BillingService.getCon().prepareStatement(priceQ);

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            return new ResponseModel(Results.CART_ITEM_NOT_EXIST).buildResponse(headers);
        }
        float price = rs.getFloat("total");
        ServiceLogger.LOGGER.info("Total amount $"+price);
        //Create order and get token(order_id)
        String[] response  = PayPalOrderClient.createOrder(price);
        String token=response[0];
        //update table sale and transaction

        ps = BillingService.getCon().prepareStatement(
                "INSERT INTO sale (email, movie_id, quantity, sale_date)\n" +
                "SELECT email, movie_id, quantity, current_date\n" +
                "FROM cart\n" +
                "WHERE email = ?;\n"
                , Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,email);
        ps.executeUpdate();
        ResultSet keys_rs=ps.getGeneratedKeys();

        StringBuilder Q=new StringBuilder("INSERT INTO transaction(sale_id, token)\n");

        List<Integer> keys=new ArrayList<>();
        if(keys_rs.next()){
            Q.append(String.format("VALUE (%d, '%s')",keys_rs.getInt(1),token));
        }
        while(keys_rs.next()){
            Q.append(String.format(",\n(%d, '%s')",keys_rs.getInt(1),token));
        }
        ps = BillingService.getCon().prepareStatement(Q.toString());

        ServiceLogger.LOGGER.warning(ps.toString());
        ps.executeUpdate();
        return new PlaceResponse(Results.ORDER_PLACE_SUCCESSFUL,response[1],token ).buildResponse(headers);
    }

    @POST
    @Path("retrieve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieve(BillingRequest args) {
    try {
        ServiceLogger.LOGGER.warning("/order/retrieve get called");
        String email = args.email;


        //Get orderID(Token)
        String IdQ = "SELECT DISTINCT token, capture_id\n" +
                "FROM transaction\n" +
                "JOIN sale s on s.sale_id = transaction.sale_id\n" +
                "WHERE s.email= ? AND transaction.capture_id IS NOT NULL;";
        PreparedStatement ps = BillingService.getCon().prepareStatement(IdQ);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
//        ps.close();
        List<String> orderIDs = new ArrayList<>();
        while (rs.next()) {
            orderIDs.add(rs.getString("token"));
            ServiceLogger.LOGGER.warning("token:"+orderIDs.get(orderIDs.size()-1));
        }
        //return if no token found
        if (orderIDs.size() == 0) {
            return new ResponseModel(Results.ORDER_HISTORY_NOT_EXIST).buildResponse(headers);
        }

        //get transaction for each token.
        List<transactionModel> transactions = new ArrayList<>();
        for (String orderID : orderIDs) {
            //get transaction model for PayPal getOrder.(item is initially empty)
            transactionModel transaction=PayPalOrderClient.getOrder(orderID);

            //get items for this token
            String itemQ = "SELECT email,sale.movie_id,quantity,unit_price,discount,sale_date\n" +
                    "FROM sale\n" +
                    "JOIN transaction t on sale.sale_id = t.sale_id\n" +
                    "JOIN movie_price mp on sale.movie_id = mp.movie_id\n" +
                    "WHERE token= ?;";
            ps = BillingService.getCon().prepareStatement(itemQ);
            ps.setString(1,orderID);
            rs=ps.executeQuery();
            //append items into transaction model of this token
            while(rs.next()){
                transaction.addItems(
                        rs.getString("email"),
                        rs.getString("movie_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("discount"),
                        rs.getDate("sale_date").toString());
            }
            transactions.add(transaction);
        }

        return new OrderRetrieveResponse(Results.ORDER_RETRIEVED_SUCCESSFUL,transactions).buildResponse(headers);



    }catch (Exception e){
        e.printStackTrace();
        return new ResponseModel(Results.INTERNAL_SERVER_ERROR).buildResponse(headers);
    }

    }

    @Path("complete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response complete(@QueryParam("token") String token,
                             @QueryParam("PayerID")String payerID) throws Throwable{

        ServiceLogger.LOGGER.info("token:"+token);
        ServiceLogger.LOGGER.info("PayerID:"+payerID);
        try{
            //check token validity
            String Q = "SELECT *\n" +
                    "FROM transaction\n" +
                    "WHERE token=?;";
            PreparedStatement ps = BillingService.getCon().prepareStatement(Q);
            ps.setString(1, token);
            if(!ps.executeQuery().next()){
                return new ResponseModel(Results.TOKEN_NOT_FOUND).buildResponse(headers);
            }
            ServiceLogger.LOGGER.info("Token is valid");
            String captureID=PayPalOrderClient.captureOrder(token);
            //Update transaction table
            String Q2="UPDATE transaction\n" +
                    "SET capture_id= ?\n" +
                    "WHERE token=?;";
            ps=BillingService.getCon().prepareStatement(Q2);
            ps.setString(1,captureID);
            ps.setString(2,token);
            ps.executeUpdate();

            //clear cart
            String clearQ="DELETE FROM cart\n" +
                    "WHERE email=(\n" +
                    "    SELECT DISTINCT email\n" +
                    "    FROM sale\n" +
                    "    JOIN transaction t on sale.sale_id = t.sale_id\n" +
                    "    WHERE t.token=?\n" +
                    "    );";
            ps=BillingService.getCon().prepareStatement(clearQ);
            ps.setString(1,token);
            ps.executeUpdate();

            return new ResponseModel(Results.ORDER_COMPLETED_SUCCESSFUL).buildResponse(headers);

        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof HttpException){
                return new ResponseModel(Results.ORDER_CANNOT_COMPLETE).buildResponse(headers);
            }else {
                return new ResponseModel(Results.INTERNAL_SERVER_ERROR).buildResponse(headers);
            }

        }


    }
}

















