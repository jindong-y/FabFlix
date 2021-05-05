package testcases.socket;

import java.net.URI;

import testcases.model.hw1.request.MathRequestModel;
import testcases.model.hw1.response.ReverseResponseModel;
import testcases.model.hw1.response.MathResponseModel;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import edu.uci.ics.cs122b.test.util.ServiceSocket;
import edu.uci.ics.cs122b.test.util.ServiceSocketFactory;

public class BasicSocket
{
    private final static String SCHEME;
    private final static String ADDRESS;
    private final static String PORT;
    private final static String API_PATH;

    private final static URI FULL_URI;

    private final static String HELLO_EP = "/hello";
    private final static String REVERSE_EP = "/reverse/";
    private final static String MATH_EP = "/math";

    private final static ServiceSocketFactory SOCKET_FACTORY;

    static {
        SCHEME = "http://";
        ADDRESS = "127.0.0.1";
        PORT = ":12345";
        API_PATH = "/api/basic";

        FULL_URI = URI.create(SCHEME + ADDRESS + PORT + API_PATH);

        SOCKET_FACTORY = ServiceSocketFactory.createFactory(FULL_URI);
    }

    public static ServiceResponse<ReverseResponseModel> getReverseString(String str)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(REVERSE_EP + str);

        return serviceSocket.get(ReverseResponseModel.class);
    }

    public static ServiceResponse<MathResponseModel> postMath(Integer num_x, Integer num_y, Integer num_z)
    {
        MathRequestModel request = new MathRequestModel(num_x, num_y, num_z);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(MATH_EP);

        return serviceSocket.post(MathResponseModel.class, request);
    }

    public static ServiceResponse<MathResponseModel> postMath(String request)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(MATH_EP);

        return serviceSocket.post(MathResponseModel.class, request);
    }

    public static ServiceResponse getHello()
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(HELLO_EP);

        return serviceSocket.get();
    }
}
