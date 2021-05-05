package testcases.socket;

import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import edu.uci.ics.cs122b.test.util.ServiceSocket;
import edu.uci.ics.cs122b.test.util.ServiceSocketFactory;
import testcases.model.hw3.request.ThumbnailRequestModel;
import testcases.model.hw3.response.ThumbnailResponseModel;
import testcases.model.hw4.request.*;
import testcases.model.hw4.response.*;

import javax.ws.rs.core.MultivaluedHashMap;
import java.net.URI;

public class BillingSocket {
    private final static String SCHEME;
    private final static String ADDRESS;
    private final static String PORT;
    private final static String API_PATH;

    private final static URI FULL_URI;

    private final static String CART_INSERT = "/cart/insert";
    private final static String CART_UPDATE = "/cart/update";
    private final static String CART_DELETE = "/cart/delete";
    private final static String CART_RETRIEVE = "/cart/retrieve";
    private final static String CART_CLEAR = "/cart/clear";
    private final static String ORDER_PLACE = "/order/place";
    private final static String ORDER_RETRIEVE = "/order/retrieve";
    private final static String ORDER_COMPLETE = "/order/complete";

    private final static ServiceSocketFactory SOCKET_FACTORY;

    static {
        SCHEME = "http://";
        ADDRESS = "127.0.0.1";
        PORT = ":12345";
        API_PATH = "/api/billing";


        FULL_URI = URI.create(SCHEME + ADDRESS + PORT + API_PATH);

        SOCKET_FACTORY = ServiceSocketFactory.createFactory(FULL_URI);
    }


    public static ServiceResponse<CartInsertResponseModel> postCartInsert(MultivaluedHashMap<String, Object> headers, String request)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_INSERT);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.post(CartInsertResponseModel.class, request);
    }

    public static ServiceResponse<CartInsertResponseModel> postCartInsert(MultivaluedHashMap<String, Object> headers, String email, String movie_id, Integer quantity)
    {
        CartInsertRequestModel requestModel = new CartInsertRequestModel(email, movie_id, quantity);
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_INSERT);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.post(CartInsertResponseModel.class, requestModel);
    }


    public static ServiceResponse<CartRetrieveResponseModel> postCartRetrieve(MultivaluedHashMap<String, Object> headers, String email)
    {
        CartRetrieveRequestModel requestModel = new CartRetrieveRequestModel(email);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_RETRIEVE);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.post(CartRetrieveResponseModel.class, requestModel);
    }

    public static ServiceResponse<CartRetrieveResponseModel> postCartRetrieveRequestStr(MultivaluedHashMap<String, Object> headers, String request)
    {

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_RETRIEVE);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.post(CartRetrieveResponseModel.class, request);
    }

    public static ServiceResponse<CartClearResponseModel> postCartClear(MultivaluedHashMap<String, Object> headers, String email)
    {
        CartClearRequestModel requestModel = new CartClearRequestModel(email);
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_CLEAR);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.post(CartClearResponseModel.class, requestModel);
    }

    public static ServiceResponse<CartClearResponseModel> postCartClearRequestStr(MultivaluedHashMap<String, Object> headers, String request)
    {

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_CLEAR);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.post(CartClearResponseModel.class, request);
    }

    public static ServiceResponse<CartUpdateResponseModel> postCartUpdate(MultivaluedHashMap<String, Object> headers, String email, String movie_id, Integer quantity){
        CartUpdateRequestModel requestModel = new CartUpdateRequestModel(email, movie_id, quantity);
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_UPDATE);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.post(CartUpdateResponseModel.class, requestModel);
    }

    public static ServiceResponse<CartUpdateResponseModel> postCartUpdate(MultivaluedHashMap<String, Object> headers, String request){
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_UPDATE);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.post(CartUpdateResponseModel.class, request);
    }

    public static ServiceResponse<CartDeleteResponseModel> postCartDelete(MultivaluedHashMap<String, Object> headers, String email, String movie_id)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_DELETE);
        serviceSocket = serviceSocket.headers(headers);
        CartDeleteRequestModel requestModel = new CartDeleteRequestModel(email, movie_id);

        return serviceSocket.post(CartDeleteResponseModel.class, requestModel);
    }



}
