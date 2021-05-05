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

public class IdmTestSession
{
    public static final int EXPECTED_SESSION_ID_LENGTH = 64;
    public static final String USER_SESSION     = "3c441bdac83d2e5027c6bf237121ddcebf4f6046a8db136d8026048c0b0cdabf2ac6301db8d09172bb100211f9b742f1245b921630e6fa9fa63d806f99177cbc";

    public static final String SESSION_ACTIVE   = "15b99dfdec7e615846abe541b535ab7fb5af17f263e577dfeb4ebd4618d043e9161f3f753afb972a12d775d31568010a77be008b883084b3afa663b11d1cf2cc";
    public static final String SESSION_EXPIRED  = "cdf061488306a2f2e3d97260564eaa3be5a17defc514a690a9b41b4fa7335757b66c4da6e4bdd6570cc77033dd887ab62ca53cea452247461dedca35737c126f";
    public static final String SESSION_CLOSED   = "8a5c59cceac13d6d8a4ea43e0178aedf9dbec9cb86a0d7b3b4bf7fd3d0780b501b801bab816a7edc45cc06cb0f57b6a933eace485350db9e7a4f4b5d8eda2ffb";
    public static final String SESSION_REVOKED  = "15b99dfdec7e615846abe541b535ab7fb5af17f263e577dfeb4ebd4618d043e9161f3f753afb972a12d775d31568010a77be008b883084b3afa663b11d1cf2c4";
    public static final String SESSION_NOTFOUND = "ef6fbf756c921e93bb11ec2ec4b55e5a81425addaf060554fd94dd4675fd7a8df01e27cd9479042142eaac6129aad8fe98083100de3fddfd7c052f32b0c7295a";

    @Test
    public void tokenLengthTooShort()
    {
        Result expectedResult = Result.TOKEN_INVALID_LENGTH;
        String session_id = "TOOSHORT";

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("peter@uci.edu", session_id);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void tokenLengthTooLong()
    {
        Result expectedResult = Result.TOKEN_INVALID_LENGTH;
        String session_id = "TOOLONG" + USER_SESSION;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("peter@uci.edu", session_id);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void sessionTokenInvalidLength_EmptyString()
    {
        Result expectedResult = Result.TOKEN_INVALID_LENGTH;
        String session_id = "";

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("peter@uci.edu", session_id);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    @Ignore
    public void sessionTokenInvalidLength_Null()
    {
        Result expectedResult = Result.TOKEN_INVALID_LENGTH;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("peter@uci.edu", null);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void sessionEmailAddressInvalidFormat_NoEmail()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("@uci.edu", USER_SESSION);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void sessionEmailAddressInvalidFormat_NoAt()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("peteruci.edu", USER_SESSION);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void sessionEmailAddressInvalidFormat_NoDomain()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("peter@.edu", USER_SESSION);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void sessionEmailAddressInvalidFormat_NoExtension()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("peter@uci.", USER_SESSION);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }


    @Test
    public void sessionEmailAddressInvalidLength_EmptyString()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_LENGTH;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("", USER_SESSION);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void sessionEmailAddressInvalidLength_Null()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_LENGTH;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession(null, USER_SESSION);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void sessionParse()
    {
        Result expectedResult = Result.JSON_PARSE_EXCEPTION;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession(Json.PARSE_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
        assertEquals(null, response.getEntity().getSession_id());
    }

    @Test
    public void sessionMapping()
    {
        Result expectedResult = Result.JSON_MAPPING_EXCEPTION;
        SessionResponseModel expectedModel = new SessionResponseModel(expectedResult);

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession(Json.MAPPING_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void sessionUserNotFound()
    {
        Result expectedResult = Result.USER_NOT_FOUND;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("NotRealUser@uci.edu", USER_SESSION);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void sessionActive()
    {
        Result expectedResult = Result.SESSION_ACTIVE;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("ActiveSession@uci.edu", SESSION_ACTIVE);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
        assertNotNull(response.getEntity().getSession_id());
    }

    @Test
    public void sessionExpired()
    {
        Result expectedResult = Result.SESSION_EXPIRED;
        SessionResponseModel expectedModel = new SessionResponseModel(expectedResult);

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("ExpiredSession@uci.edu", SESSION_EXPIRED);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void sessionClosed()
    {
        Result expectedResult = Result.SESSION_CLOSED;
        SessionResponseModel expectedModel = new SessionResponseModel(expectedResult);

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("ClosedSession@uci.edu", SESSION_CLOSED);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void sessionRevoked()
    {
        Result expectedResult = Result.SESSION_REVOKED;
        SessionResponseModel expectedModel = new SessionResponseModel(expectedResult);

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("RevokedSession@uci.edu", SESSION_REVOKED);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void sessionNotFound()
    {
        Result expectedResult = Result.SESSION_NOT_FOUND;

        ServiceResponse<SessionResponseModel> response = IdmSocket.postSession("NotFoundSession@uci.edu", SESSION_NOTFOUND);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }
}
