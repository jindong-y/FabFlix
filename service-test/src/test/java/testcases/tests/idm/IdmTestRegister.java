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

public class IdmTestRegister
{
    @Test
    public void registerSuccessfully()
    {
        Result expectedResult = Result.USER_REGISTERED_SUCCESSFULLY;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAbbb111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("RegisterSuccess@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerEmailAlreadyInUse()
    {
        Result expectedResult = Result.EMAIL_ALREADY_IN_USE;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAbbb111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("AlreadyInUse@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerPasswordNotMeetLengthRequirementTooShort()
    {
        Result expectedResult = Result.PASSWORD_NOT_MEET_LENGTH_REQUIREMENTS;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "Aa1";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerPasswordNotMeetLengthRequirementTooLong()
    {
        Result expectedResult = Result.PASSWORD_NOT_MEET_LENGTH_REQUIREMENTS;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerEmailInvalidFormatNoEmail()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAAAaaaaa111";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerEmailInvalidFormatNoAT()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAAAaaaaa111";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peteruci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerEmailInvalidFormatNoDomain()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAAAaaaaa111";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerEmailInvalidFormatNoExtension()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAAAaaaaa111";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@uci.", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerEmailInvalidFormatContainsNotAlphanumericChar()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAAAaaaaa111";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter#$%^@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerEmailInvalidLengthEmptyString()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_LENGTH;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAAAaaaaa111";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerEmailInvalidLengthNull()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_LENGTH;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAAAAaaaaa111";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister(null, passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerParse()
    {
        Result expectedResult = Result.JSON_PARSE_EXCEPTION;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister(Json.PARSE_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerMapping()
    {
        Result expectedResult = Result.JSON_MAPPING_EXCEPTION;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister(Json.MAPPING_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerPasswordInvalidLengthEmpty()
    {
        Result expectedResult = Result.PASSWORD_INVALID_LENGTH;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerPasswordInvalidLengthNull()
    {
        Result expectedResult = Result.PASSWORD_INVALID_LENGTH;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@uci.edu", null);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerPasswordNotMeetCharacterRequirement_NoUpperCaseAlpha()
    {
        Result expectedResult = Result.PASSWORD_NOT_MEET_CHARACTER_REQUIREMENTS;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "aaabbb111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerPasswordNotMeetCharacterRequirement_NoLowerCaseAlpha()
    {
        Result expectedResult = Result.PASSWORD_NOT_MEET_CHARACTER_REQUIREMENTS;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAABBB111222";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void registerPasswordNotMeetCharacterRequirement_NoNumeric()
    {
        Result expectedResult = Result.PASSWORD_NOT_MEET_CHARACTER_REQUIREMENTS;
        RegisterResponseModel expectedModel = new RegisterResponseModel(expectedResult);
        String password = "AAABBBcccddd";
        char[] passwordArray = password.toCharArray();

        ServiceResponse<RegisterResponseModel> response = IdmSocket.postRegister("peter@uci.edu", passwordArray);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

}
