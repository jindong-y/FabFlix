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

public class IdmTestPrivilege
{
    @Test
    public void privilegeLevelOutOfRange()
    {
        Result expectedResult = Result.PLEVEL_OUT_OF_RANGE;
        int plevel = 0;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege("peter@uci.edu", plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeLevelOutOfRange2()
    {
        Result expectedResult = Result.PLEVEL_OUT_OF_RANGE;
        int plevel = 6;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege("peter@uci.edu", plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeEmailAddressInvalidFormat_NoEmail()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        int plevel = 5;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege("@uci.edu", plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeEmailAddressInvalidFormat_NoAt()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        int plevel = 5;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege("peteruci.edu", plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeEmailAddressInvalidFormat_NoDomain()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        int plevel = 5;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege("peter@.edu", plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeEmailAddressInvalidFormat_NoExtension()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_FORMAT;
        int plevel = 5;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege("peter@uci.", plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }


    @Test
    public void privilegeEmailAddressInvalidLength_EmptyString()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_LENGTH;
        int plevel = 5;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege("", plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeEmailAddressInvalidLength_Null()
    {
        Result expectedResult = Result.EMAIL_ADDRESS_INVALID_LENGTH;
        int plevel = 5;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege(null, plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeParse()
    {
        Result expectedResult = Result.JSON_PARSE_EXCEPTION;
        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege(Json.PARSE_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeMapping()
    {
        Result expectedResult = Result.JSON_MAPPING_EXCEPTION;
        PrivilegeResponseModel expectedModel = new PrivilegeResponseModel(expectedResult);

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege(Json.MAPPING_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void privilegeUserNotFound()
    {
        Result expectedResult = Result.USER_NOT_FOUND;
        int plevel = 5;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege("NotRealUser@uci.edu", plevel);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedResult.getResultCode(), response.getEntity().getResultCode());
    }

    @Test
    public void privilegeUserLevel()
    {
        String userEmail = "UserLevel@uci.edu";
        Result sufficientResult = Result.SUFFICIENT_PRIVILEGE;
        Result insufficientResult = Result.INSUFFICIENT_PRIVILEGE;
        int plevel = 5;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege(userEmail, plevel);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(userEmail, 4);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(userEmail, 3);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(userEmail, 2);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(userEmail, 1);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());
    }

    @Test
    public void privilegeServiceLevel()
    {
        String serviceEmail = "ServiceLevel@uci.edu";
        Result sufficientResult = Result.SUFFICIENT_PRIVILEGE;
        Result insufficientResult = Result.INSUFFICIENT_PRIVILEGE;
        int plevel = 4;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege(serviceEmail, plevel);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(serviceEmail, 4);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(serviceEmail, 3);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(serviceEmail, 2);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(serviceEmail, 1);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());
    }

    @Test
    public void privilegeEmployeeLevel()
    {
        String employeeEmail = "EmployeeLevel@uci.edu";
        Result sufficientResult = Result.SUFFICIENT_PRIVILEGE;
        Result insufficientResult = Result.INSUFFICIENT_PRIVILEGE;
        int plevel = 3;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege(employeeEmail, plevel);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(employeeEmail, 4);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(employeeEmail, 3);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(employeeEmail, 2);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(employeeEmail, 1);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());
    }

    @Test
    public void privilegeAdminLevel()
    {
        String adminEmail = "AdminLevel@uci.edu";
        Result sufficientResult = Result.SUFFICIENT_PRIVILEGE;
        Result insufficientResult = Result.INSUFFICIENT_PRIVILEGE;
        int plevel = 2;

        ServiceResponse<PrivilegeResponseModel> response = IdmSocket.postPrivilege(adminEmail, plevel);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(adminEmail, 4);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(adminEmail, 3);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(adminEmail, 2);

        assertEquals(sufficientResult.getStatus(), response.getStatus());
        assertEquals(sufficientResult.getResultCode(), sufficientResult.getResultCode());

        response = IdmSocket.postPrivilege(adminEmail, 1);

        assertEquals(insufficientResult.getStatus(), response.getStatus());
        assertEquals(insufficientResult.getResultCode(), insufficientResult.getResultCode());
    }
}
