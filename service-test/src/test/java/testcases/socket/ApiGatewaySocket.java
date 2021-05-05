package testcases.socket;

import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import edu.uci.ics.cs122b.test.util.ServiceSocket;
import edu.uci.ics.cs122b.test.util.ServiceSocketFactory;
import testcases.model.hw2.request.LoginRequestModel;
import testcases.model.hw2.response.LoginResponseModel;
import testcases.model.hw2.response.RegisterResponseModel;
import testcases.model.hw3.request.BrowseKeywordRequestModel;
import testcases.model.hw3.request.GetMovieRequestModel;
import testcases.model.hw3.request.GetPersonRequestModel;
import testcases.model.hw3.request.ThumbnailRequestModel;
import testcases.model.hw3.response.*;
import testcases.model.hw4.request.CartInsertRequestModel;

import javax.ws.rs.core.MultivaluedHashMap;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class ApiGatewaySocket {
    private final static String SCHEME;
    private final static String ADDRESS;
    private final static String PORT;
    private final static String API_PATH;

    private final static URI FULL_URI;

    private final static String API_GATEWAY_REPORT_EP = "/report";

    private final static String IDM_REGISTER_EP = "/idm/register";
    private final static String IDM_LOGIN_EP = "/idm/login";

    private final static String MOVIES_SEARCH_EP = "/movies/search";
    private final static String MOVIES_GET_PEOPLE_EP = "/movies/people/get/";
    private final static String MOVIES_GET_MOVIE_EP = "/movies/get/";

    private final static String CART_INSERT_EP = "/billing/cart/insert";
    private final static String CART_RETRIEVE_EP = "/billing/cart/retrieve";


    private final static ServiceSocketFactory SOCKET_FACTORY;

    static {
        SCHEME = "http://";
        ADDRESS = "127.0.0.1";
        PORT = ":12345";
        API_PATH = "/api/g";

        FULL_URI = URI.create(SCHEME + ADDRESS + PORT + API_PATH);

        SOCKET_FACTORY = ServiceSocketFactory.createFactory(FULL_URI);
    }

    public static <T extends ResponseModel> int ReportLoop(Class<T> responseClass, MultivaluedHashMap<String, Object> headers){
        int responseCount = 0;

        for (int i = 0; i < 10; ++i){
            ServiceResponse<T> responseReal = ApiGatewaySocket.apiGetReport(responseClass, headers);
            if (responseReal.getStatus() != 204)
                responseCount++;

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Sleep Interrupted");
                //e.printStackTrace();

            }
        }
        return responseCount;

    }

    public static <T extends ResponseModel> ServiceResponse<T> apiGetReport(Class<T> responseClass, MultivaluedHashMap<String, Object> headers)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(API_GATEWAY_REPORT_EP);
        serviceSocket = serviceSocket.headers(headers);
        return serviceSocket.get(responseClass);
    }


    public static ServiceResponse<ResponseModel> apiPostIdmRegister(String email, char[] password)
    {
        LoginRequestModel requestModel = new LoginRequestModel(email, password);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(IDM_REGISTER_EP);

        return serviceSocket.post(ResponseModel.class,requestModel);
    }

    public static ServiceResponse<ResponseModel> apiPostIdmLogin(String email, char[] password)
    {
        LoginRequestModel requestModel = new LoginRequestModel(email, password);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(IDM_LOGIN_EP);

        return serviceSocket.post(ResponseModel.class,requestModel);
    }

    public static ServiceResponse<ResponseModel> apiGetMovieSearch(MultivaluedHashMap<String, Object> headers, MultivaluedHashMap<String, Object> query)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(MOVIES_SEARCH_EP);
        serviceSocket = serviceSocket.headers(headers);
        serviceSocket = serviceSocket.queries(query);

        return serviceSocket.get(ResponseModel.class);

    }

    public static ServiceResponse<ResponseModel> apiGetMovieByID(MultivaluedHashMap<String, Object> headers, String movie_id)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(MOVIES_GET_MOVIE_EP+movie_id);
        serviceSocket = serviceSocket.headers(headers);

        return serviceSocket.get(ResponseModel.class);

    }

    public static ServiceResponse<ResponseModel> apiPostBillingCartInsert(MultivaluedHashMap<String, Object> headers, String email, String movie_id, Integer quantity)
    {
        CartInsertRequestModel requestModel = new CartInsertRequestModel(email,movie_id,quantity);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(CART_INSERT_EP);
        serviceSocket.headers(headers);

        return serviceSocket.post(ResponseModel.class, requestModel);

    }




}
