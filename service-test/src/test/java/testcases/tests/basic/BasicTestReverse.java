package testcases.tests.basic;

import edu.uci.ics.cs122b.test.common.Json;
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw1.response.MathResponseModel;
import testcases.model.hw1.response.ReverseResponseModel;
import testcases.socket.BasicSocket;

import static org.junit.Assert.assertEquals;

public class BasicTestReverse
{
    //*********************************************
    //
    //                Reverse Tests
    //
    //*********************************************
    @Test
    public void reverseStringLowerCase()
    {
        Result expectedResult = Result.STRING_SUCCESSFULLY_REVERSED;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, "test");

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("tset");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void reverseStringUpperCase()
    {
        Result expectedResult = Result.STRING_SUCCESSFULLY_REVERSED;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, "TSET");

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("TEST");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void reverseStringMix()
    {
        Result expectedResult = Result.STRING_SUCCESSFULLY_REVERSED;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, "tSeT");

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("TeSt");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void reverseStringSpace()
    {
        Result expectedResult = Result.STRING_SUCCESSFULLY_REVERSED;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, "t s eT");

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("Te s t");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void reverseStringNumber()
    {
        Result expectedResult = Result.STRING_SUCCESSFULLY_REVERSED;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, "4321");

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("1234");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void reverseStringUnderScore()
    {
        Result expectedResult = Result.STRING_SUCCESSFULLY_REVERSED;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, "__b321A_");

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("_A123b__");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void reverseStringInvalidEdge()
    {
        Result expectedResult = Result.STRING_CONTAINS_INVALID_CHARACTERS;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, null);

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("!ABC!");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void reverseStringInvalidMid()
    {
        Result expectedResult = Result.STRING_CONTAINS_INVALID_CHARACTERS;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, null);

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("AB!CD");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void reverseStringInvalidAll()
    {
        Result expectedResult = Result.STRING_CONTAINS_INVALID_CHARACTERS;
        ReverseResponseModel expectedModel = new ReverseResponseModel(expectedResult, null);

        ServiceResponse<ReverseResponseModel> response = BasicSocket.getReverseString("!!!!");

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

}
