package testcases.tests.basic;

import edu.uci.ics.cs122b.test.common.Json;
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw1.response.MathResponseModel;
import testcases.model.hw1.response.ReverseResponseModel;
import testcases.socket.BasicSocket;

import static org.junit.Assert.assertEquals;

public class BasicTestMath
{
    //*********************************************
    //
    //                Math Tests
    //
    //*********************************************
    @Test
    public void mathMapping()
    {
        Result expectedResult = Result.JSON_MAPPING_EXCEPTION;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, null);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(Json.MAPPING_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathParse()
    {
        Result expectedResult = Result.JSON_PARSE_EXCEPTION;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, null);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(Json.PARSE_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathNormal()
    {
        Result expectedResult = Result.CALCULATION_SUCCESSFUL;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, 5);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 2, 3);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathEdgeX1()
    {
        Result expectedResult = Result.CALCULATION_SUCCESSFUL;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, 1);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 1, 0);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathEdgeX2()
    {
        Result expectedResult = Result.CALCULATION_SUCCESSFUL;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, 99);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(99, 1, 0);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathEdgeY1()
    {
        Result expectedResult = Result.CALCULATION_SUCCESSFUL;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, 1);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 1, 0);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathEdgeY2()
    {
        Result expectedResult = Result.CALCULATION_SUCCESSFUL;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, 99);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 99, 0);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathEdgeZ1()
    {
        Result expectedResult = Result.CALCULATION_SUCCESSFUL;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, -9);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 1, -10);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathEdgeZ2()
    {
        Result expectedResult = Result.CALCULATION_SUCCESSFUL;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, 11);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 1, 10);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathPastLimitX1()
    {
        Result expectedResult = Result.DATA_CONTAINS_INVALID_INTEGERS;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, null);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(0, 1, 0);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathPastLimitX2()
    {
        Result expectedResult = Result.DATA_CONTAINS_INVALID_INTEGERS;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, null);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(100, 1, 0);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathPastLimitY1()
    {
        Result expectedResult = Result.DATA_CONTAINS_INVALID_INTEGERS;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, null);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 0, 0);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathPastLimitY2()
    {
        Result expectedResult = Result.DATA_CONTAINS_INVALID_INTEGERS;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, null);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 100, 0);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathPastLimitZ1()
    {
        Result expectedResult = Result.DATA_CONTAINS_INVALID_INTEGERS;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, null);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 1, -11);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void mathPastLimitZ2()
    {
        Result expectedResult = Result.DATA_CONTAINS_INVALID_INTEGERS;
        MathResponseModel expectedModel = new MathResponseModel(expectedResult, null);

        ServiceResponse<MathResponseModel> response = BasicSocket.postMath(1, 1, 11);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }
}
