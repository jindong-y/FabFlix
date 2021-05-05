package edu.uci.ics.jindongy.service.billing.resources;


import edu.uci.ics.jindongy.service.billing.BillingService;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.billing.models.Request.DeleteRequest;
import edu.uci.ics.jindongy.service.billing.models.Request.UpdateRequest;
import edu.uci.ics.jindongy.service.billing.models.Response.RetrieveResponse;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.Request.BillingRequest;
import edu.uci.ics.jindongy.service.billing.models.base.Results;
import edu.uci.ics.jindongy.service.billing.models.data.ThumbnailModel;
import edu.uci.ics.jindongy.service.billing.models.data.itemModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.ws.rs.core.Response;

@Path("cart")
public class CartResource {


    public @Context
    HttpHeaders headers;

    @Context
    private UriInfo uri;


    private boolean isMovieValid(String movie_id) throws SQLException {
        String Q = "SELECT *\n" +
                "FROM movie_price\n" +
                "WHERE movie_id= ?;";

        PreparedStatement ps = BillingService.getCon().prepareStatement(Q);
        ps.setString(1, movie_id);
        return ps.executeQuery().next();
    }

    @POST
    @Path("insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(UpdateRequest args) throws Throwable {

        System.out.println(uri.getPath());

        //is valid user?
        try {
            if (new RequestToIDM().requestPrivilege(headers) == 14) {
                ServiceLogger.LOGGER.severe("User not found");
                return new ResponseModel(Results.USER_NOT_FOUND).buildResponse(headers);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel(Results.INTERNAL_SERVER_ERROR).buildResponse(headers);
        }
        ServiceLogger.LOGGER.info("user found");
        String email = args.email;
        String movie_id = args.movie_id;
        Integer quantity = args.quantity;

        //is valid quantity
        if (quantity <= 0) {
            ServiceLogger.LOGGER.severe("Quantity <=0");
            return new ResponseModel(Results.INVALID_QUANTITY).buildResponse(headers);
        }
        ServiceLogger.LOGGER.info("Quantity valid");

        //is movie_id valid
        if (!isMovieValid(movie_id)) {
            ServiceLogger.LOGGER.warning("Invalid movie_id");
            return new ResponseModel(Results.CART_OPERATION_FAILED).buildResponse(headers);
        }
        ServiceLogger.LOGGER.info("Movie_id valid");

        ServiceLogger.LOGGER.info("SQL query...");

        String Q = "INSERT INTO cart(email, movie_id, quantity)\n" +
                "VALUE (?,?,?) ";


        PreparedStatement ps = BillingService.getCon().prepareStatement(Q);
        ps.setString(1, email);
        ps.setString(2, movie_id);
        ps.setInt(3, quantity);
        try {
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            ServiceLogger.LOGGER.warning(e.getMessage());
            return new ResponseModel(Results.DUPLICATE_INSERTION).buildResponse(headers);
        }


        return new ResponseModel(Results.CART_ITEM_INSERT_SUCCESSFUL).buildResponse(headers);
    }


    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(UpdateRequest args) throws Exception {
        ServiceLogger.LOGGER.warning("/cart/update is called");

        String email = args.email;
        String movie_id = args.movie_id;
        Integer quantity = args.quantity;

        //is valid quantity
        if (quantity <= 0) {
            ServiceLogger.LOGGER.severe("Quantity <=0");
            return new ResponseModel(Results.INVALID_QUANTITY).buildResponse(headers);
        }
        ServiceLogger.LOGGER.info("Quantity valid");
        //is movie_id valid
        if (!isMovieValid(movie_id)) {
            ServiceLogger.LOGGER.warning("Invalid movie_id");
            return new ResponseModel(Results.CART_ITEM_NOT_EXIST).buildResponse(headers);
        }
        ServiceLogger.LOGGER.info("Movie_id valid");

        ServiceLogger.LOGGER.info("SQL query...");
        String Q = "UPDATE cart\n" +
                "SET quantity=?\n" +
                "WHERE email=?\n" +
                "And movie_id=?\n";

        PreparedStatement ps = BillingService.getCon().prepareStatement(Q);
        ps.setInt(1, quantity);
        ps.setString(2, email);
        ps.setString(3, movie_id);

        int numOfRowAffected = ps.executeUpdate();

        if (numOfRowAffected == 0) {
            return new ResponseModel(Results.CART_ITEM_NOT_EXIST).buildResponse(headers);
        }
        return new ResponseModel(Results.CART_UPDATED_SUCCESSFUL).buildResponse(headers);

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(DeleteRequest args) throws Throwable {

        String email = args.email;
        String movie_id = args.movie_id;

        String Q = "DELETE\n" +
                "FROM cart\n" +
                "WHERE email = ?\n" +
                "  AND movie_id = ?;";

        PreparedStatement ps = BillingService.getCon().prepareStatement(Q);
        ps.setString(1, email);
        ps.setString(2, movie_id);

        int numOfRowsAffected = ps.executeUpdate();
        if (numOfRowsAffected == 0) {
            return new ResponseModel(Results.CART_ITEM_NOT_EXIST).buildResponse(headers);
        }
        return new ResponseModel(Results.CART_ITEM_DELETE_SUCCESSFUL).buildResponse(headers);

    }

    @POST
    @Path("retrieve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieve(BillingRequest args) throws Throwable {
        ServiceLogger.LOGGER.warning("/cart/retrieve is called");

        String email = args.email;


        ServiceLogger.LOGGER.info("SQL Query");
        String Q = "SELECT email, unit_price, discount, quantity, cart.movie_id\n" +
                "\n" +
                "FROM cart\n" +
                "         join movie_price mp on cart.movie_id = mp.movie_id\n" +
                "WHERE cart.email = ?\n" +
                "  AND cart.movie_id in (\n" +
                "    SELECT movie_id\n" +
                "    FROM cart\n" +
                "    WHERE email = ?)\n" +
                "ORDER BY cart.movie_id;";

        PreparedStatement ps = BillingService.getCon().prepareStatement(Q);
        ps.setString(1, email);
        ps.setString(2, email);
        ServiceLogger.LOGGER.warning(ps.toString());
        ResultSet rs = ps.executeQuery();

        ArrayList<String> movie_idList = new ArrayList();
        ArrayList<itemModel> itemModelList = new ArrayList();
        while (rs.next()) {
            movie_idList.add(rs.getString("movie_id"));
            itemModelList.add(new itemModel(
                    rs.getString("email"),
                    rs.getFloat("unit_price"),
                    rs.getFloat("discount"),
                    rs.getInt("quantity")
            ));
        }
        if (itemModelList.size() == 0) {
            return new ResponseModel(Results.CART_ITEM_NOT_EXIST).buildResponse(headers);
        }
        String[] movie_ids = movie_idList.toArray(new String[]{});
        ServiceLogger.LOGGER.info("Build itemModels");
        ThumbnailModel[] thumbnailModels = RequestToMovie.retrieveThumbnail(movie_ids, headers);
        ServiceLogger.LOGGER.info(String.valueOf(itemModelList.size()));
        for (int i = 0; i < itemModelList.size(); i++) {

            itemModelList.get(i).setThumbnail(thumbnailModels[i]);
            ServiceLogger.LOGGER.info(String.valueOf(i));
        }
        itemModel[] items = itemModelList.toArray(new itemModel[]{});
        return new RetrieveResponse(Results.CART_RETRIEVED_SUCCESSFUL, items).buildResponse(headers);

    }

    @POST
    @Path("clear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clear(BillingRequest args) throws Throwable {
        ServiceLogger.LOGGER.warning("/cart/clear is called");
        String email = args.email;

        String Q = "DELETE\n" +
                "FROM cart\n" +
                "WHERE email = ?\n;";

        PreparedStatement ps = BillingService.getCon().prepareStatement(Q);
        ps.setString(1, email);
        int numOfRowAffected = ps.executeUpdate();

        if (numOfRowAffected == 0) {
            return new ResponseModel(Results.CART_ITEM_NOT_EXIST).buildResponse(headers);
        }
        return new ResponseModel(Results.CART_CLEAR_SUCCESSFUL).buildResponse(headers);
    }


}














