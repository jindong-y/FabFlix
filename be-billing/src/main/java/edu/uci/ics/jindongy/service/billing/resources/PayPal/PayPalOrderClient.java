package edu.uci.ics.jindongy.service.billing.resources.PayPal;

import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.serializer.Json;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.billing.models.data.transactionModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayPalOrderClient {
    // Client id and secret retrieved from sandbox.
    private static final String clientId = "AURsd314DOqncgwUeDRe87mn_6N6mnvjfesHzgC4gdJtMldqkKQZEYedfFHXaaBa2b1CXTN_iknLFcRv";
    private static final String clientSecret = "EHZOrGVNL25UlmTqfl6rs3UYkVmTI1e23y11QvjN_UCObjmrpQ8bQmvdOVfetdhNklubPD4qqyzAzCfo";

    // Set up paypal environment
    public static final PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);

    //Create client for environment
    public static final PayPalHttpClient client = new PayPalHttpClient(environment);


    public static String[] createOrder(float value) throws IOException {
        // Construct a request object and set desired parameters
        // Here orderRequest creates a post request to paypal v2/checkout/orders

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        // Create Application Context
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("http://localhost:3000/index/order/complete")
                .cancelUrl("http://localhost:3000/index/order/complete");

        orderRequest.applicationContext(applicationContext);

        //Create Purchase Units list
        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
        purchaseUnits.add(new PurchaseUnitRequest().amountWithBreakdown(
                new AmountWithBreakdown().currencyCode("USD").value(String.valueOf(value))));
        orderRequest.purchaseUnits(purchaseUnits);

        //Create an OrdersCreateRequest Object
        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);

        //CAll API

        HttpResponse<Order> response = client.execute(request);
        Order order = response.result();

        //retrieve order_id(token)
        ServiceLogger.LOGGER.info("Order ID: " + order.id());

        order.links().forEach(link -> ServiceLogger.LOGGER.info(
                link.rel() + " => " + link.method() + ":" + link.href()
        ));
        return new String[]{order.id(),order.links().get(1).href()};


    }

    public static String captureOrder(String orderID) throws IOException {
        Order order;
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderID);

        HttpResponse<Order> response = client.execute(request);
        order = response.result();
        String captureID=order.purchaseUnits().get(0).payments().captures().get(0).id();
        ServiceLogger.LOGGER.info("Capture_ID:" + captureID);

        order.purchaseUnits().get(0).payments().captures().get(0).links().forEach(link -> {
            ServiceLogger.LOGGER.info("\t" + link.rel() + " => " + link.method()
                    + ":" + link.href());
        });
        return captureID;



    }


    public static transactionModel getOrder(String orderID) throws IOException {
        OrdersGetRequest request = new OrdersGetRequest(orderID);
        HttpResponse<Order> response = client.execute(request);
        ServiceLogger.LOGGER.info("Response Body:\n\t" + (new Json().serialize(response.result())));

        Order order=response.result();
        Capture capture=order.purchaseUnits().get(0).payments().captures().get(0);

        return new transactionModel(
                capture.id(),
                order.status(),
                capture.amount().value(),
                capture.amount().currencyCode(),
                capture.sellerReceivableBreakdown().paypalFee().value(),
                capture.sellerReceivableBreakdown().paypalFee().currencyCode(),
                capture.createTime(),
                capture.updateTime()
                );




    }

    public static void main(String[] args) throws IOException {
//       ServiceLogger.LOGGER.info(createOrder(100)[1]);
//        captureOrder("65186586UN255251C");
        getOrder("5F26598816795531H");
    }


}
