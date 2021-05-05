package testcases.socket;

import testcases.model.hw2.request.LoginRequestModel;
import testcases.model.hw2.request.PrivilegeRequestModel;
import testcases.model.hw2.request.RegisterRequestModel;
import testcases.model.hw2.request.SessionRequestModel;
import testcases.model.hw2.response.LoginResponseModel;
import testcases.model.hw2.response.PrivilegeResponseModel;
import testcases.model.hw2.response.RegisterResponseModel;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import edu.uci.ics.cs122b.test.util.ServiceSocket;
import edu.uci.ics.cs122b.test.util.ServiceSocketFactory;
import testcases.model.hw2.response.SessionResponseModel;

import java.net.URI;

public class IdmSocket
{
    private final static String SCHEME;
    private final static String ADDRESS;
    private final static String PORT;
    private final static String API_PATH;

    private final static URI FULL_URI;

    private final static String REGISTER_EP = "/register";
    private final static String LOGIN_EP = "/login";
    private final static String SESSION_EP = "/session";
    private final static String PRIVILEGE_EP = "/privilege";


    private final static ServiceSocketFactory SOCKET_FACTORY;

    static {
        SCHEME = "http://";
        ADDRESS = "127.0.0.1";
        PORT = ":12346";
        API_PATH = "/api/idm";

        FULL_URI = URI.create(SCHEME + ADDRESS + PORT + API_PATH);

        SOCKET_FACTORY = ServiceSocketFactory.createFactory(FULL_URI);
    }

    public static ServiceResponse<LoginResponseModel> postLogin(String email, char[] password)
    {
        LoginRequestModel requestModel = new LoginRequestModel(email, password);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(LOGIN_EP);

        return serviceSocket.post(LoginResponseModel.class, requestModel);
    }

    public static ServiceResponse<LoginResponseModel> postLogin(String request)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(LOGIN_EP);

        return serviceSocket.post(LoginResponseModel.class, request);
    }

    public static ServiceResponse<RegisterResponseModel> postRegister(String email, char[] password)
    {
        RegisterRequestModel requestModel = new RegisterRequestModel(email, password);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(REGISTER_EP);

        return serviceSocket.post(RegisterResponseModel.class, requestModel);
    }

    public static ServiceResponse<RegisterResponseModel> postRegister(String request)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(REGISTER_EP);

        return serviceSocket.post(RegisterResponseModel.class, request);
    }

    public static ServiceResponse<SessionResponseModel> postSession(String email, String session_id)
    {
        SessionRequestModel requestModel = new SessionRequestModel(email, session_id);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(SESSION_EP);

        return serviceSocket.post(SessionResponseModel.class, requestModel);
    }

    public static ServiceResponse<SessionResponseModel> postSession(String request)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(SESSION_EP);

        return serviceSocket.post(SessionResponseModel.class, request);
    }

    public static ServiceResponse<PrivilegeResponseModel> postPrivilege(String email, int plevel)
    {
        PrivilegeRequestModel requestModel = new PrivilegeRequestModel(email, plevel);

        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(PRIVILEGE_EP);

        return serviceSocket.post(PrivilegeResponseModel.class, requestModel);
    }

    public static ServiceResponse<PrivilegeResponseModel> postPrivilege(String request)
    {
        ServiceSocket serviceSocket = SOCKET_FACTORY.createSocket(PRIVILEGE_EP);

        return serviceSocket.post(PrivilegeResponseModel.class, request);
    }
}
