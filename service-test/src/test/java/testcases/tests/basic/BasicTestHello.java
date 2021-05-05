package testcases.tests.basic;

import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.common.Json;
import testcases.socket.BasicSocket;
import testcases.model.hw1.response.*;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicTestHello
{
    //*********************************************
    //
    //                Hello Test
    //
    //*********************************************
    @Test
    public void hello()
    {
        Integer expectedStatus = 200;
        ServiceResponse response = BasicSocket.getHello();

        assertEquals(response.getStatus(), expectedStatus);
    }
}
