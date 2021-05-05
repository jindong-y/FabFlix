package testcases.tests.idm;

import edu.uci.ics.cs122b.test.common.Json;
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Ignore;
import org.junit.Test;
import testcases.model.hw2.response.LoginResponseModel;
import testcases.model.hw2.response.PrivilegeResponseModel;
import testcases.model.hw2.response.RegisterResponseModel;
import testcases.model.hw2.response.SessionResponseModel;
import testcases.socket.IdmSocket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IdmTestLogin
{
    @Test
    public void loginSuccessfully()
    {
        Result expectedResult = Result.LOGGED_IN_SUCCESSFULLY;
        String password = "AAAbbb111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("AlreadyInUse@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginPasswordInvalidLength_EmptyString()
    {
        Result expectedResult = Result.PASSWORD_INVALID_LENGTH;
        String password = "";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("AlreadyInUse@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginPasswordInvalidLength_Null()
    {
        Result expectedResult = Result.PASSWORD_INVALID_LENGTH;

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("AlreadyInUse@uci.edu", null);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginEmailAddressInvalidLength_EmptyString()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_LENGTH;
        String password = "AAAaaa111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginEmailAddressInvalidLength_Null()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_LENGTH;
        String password = "AAAaaa111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin(null, passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginEmailAddressInvalidFormat_NoEmail()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        String password = "AAAaaa111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginEmailAddressInvalidFormat_NoAt()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        String password = "AAAaaa111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("peteruci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginEmailAddressInvalidFormat_NoDomain()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        String password = "AAAaaa111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("peter@.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginEmailAddressInvalidFormat_NoExtension()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        String password = "AAAaaa111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("peter@uci.", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void loginParse()
    {
        Result expectedResult = Result.JSON_PARSE_EXCEPTION;

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin(Json.PARSE_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
        assertEquals(null, response.getEntity().getSession_id());
    }

    @Test
    public void loginMapping()
    {
        Result expectedResult = Result.JSON_MAPPING_EXCEPTION;
        LoginResponseModel expectedModel = new LoginResponseModel(expectedResult);

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin(Json.MAPPING_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void loginPasswordNotMatch()
    {
        Result expectedResult = Result.PASSWORD_NOT_MATCH;
        LoginResponseModel expectedModel = new LoginResponseModel(expectedResult);
        String password = "BBBbbb333444";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("AlreadyInUse@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void loginUserNotFound()
    {
        Result expectedResult = Result.USER_NOT_FOUND;
        LoginResponseModel expectedModel = new LoginResponseModel(expectedResult);
        String password = "AAAbbb111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<LoginResponseModel> response = IdmSocket.postLogin("LoginDoesNotExist@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }
}
