package testcases.tests.billing;

import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testcases.model.hw4.response.CartInsertResponseModel;
import testcases.socket.BillingSocket;
import testcases.tests.HeaderTest.HeaderCheck;
import testcases.tests.movie.UserAccounts;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.junit.Assert.*;

public class BillingTestOrderInsert {
    /*
    Note: These will have to insert and retrieve movies due to logic dependencies.
     */
    public void clearCart(String email, String session_id)
    {
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", session_id);
        BillingSocket.postCartClear(headers, email);

    }
    @Before
    public void clearCartBeforeEach()
    {

        clearCart(UserAccounts.validEmail, UserAccounts.session_id);
        //Maybe log if cart clear unsuccessful?
    }

    @Test
    public void valid_CartInsertNearMaxIntQuantity()
    {
        int maxQuantity = 2_147_483_643;
        String email = UserAccounts.validEmail;
        Result expectedResult = Result.ITEM_INSERTION_SUCCESS;
        CartInsertResponseModel expectedModel = new CartInsertResponseModel(expectedResult);
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id );
        headers.putSingle("transaction_id", UserAccounts.transaction_id);

        ServiceResponse<CartInsertResponseModel> response = BillingSocket.postCartInsert(headers, email, "tt0038057", maxQuantity);

        //check headers
        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(response.getHeaders())));

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void valid_CartInsertNormal()
    {
        int quantity = 4;
        String email = UserAccounts.validEmail;
        Result expectedResult = Result.ITEM_INSERTION_SUCCESS;
        CartInsertResponseModel expectedModel = new CartInsertResponseModel(expectedResult);
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id );

        ServiceResponse<CartInsertResponseModel> response = BillingSocket.postCartInsert(headers, email, "tt0040366", quantity);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void valid_CartInsertDuplicate()
    {
        int quantity = 2;
        Result expectedResult = Result.ITEM_INSERTION_DUPLICATE;
        String email = UserAccounts.validEmail;
        CartInsertResponseModel expectedModel = new CartInsertResponseModel(expectedResult);
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id);

        BillingSocket.postCartInsert(headers, email, "tt0040338", quantity);
        ServiceResponse<CartInsertResponseModel> response = BillingSocket.postCartInsert(headers, email, "tt0040338", quantity);


        //Send second request to create duplicate entries.

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void valid_CartUserNotFound()
    {
        int quantity = 3000;
        String email = UserAccounts.NonExistantEmail;
        Result expectedResult = Result.USER_NOT_FOUND;
        CartInsertResponseModel expectedModel = new CartInsertResponseModel(expectedResult);
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email);
        headers.putSingle("session_id", UserAccounts.session_id);

        ServiceResponse<CartInsertResponseModel> response = BillingSocket.postCartInsert(headers, email, "tt0071866", quantity);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void valid_CartInvalidQuantity()
    {
        int quantity = -1;
        String email = UserAccounts.validEmail;
        Result expectedResult = Result.QUANTITY_INVALID;
        CartInsertResponseModel expectedModel = new CartInsertResponseModel(expectedResult);
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email);
        headers.putSingle("session_id", UserAccounts.session_id);

        ServiceResponse<CartInsertResponseModel> response = BillingSocket.postCartInsert(headers, email, "tt0071807", quantity);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void invalid_CartInvalidMovieId()
    {
        int quantity = 1;
        Result expectedResult = Result.CART_OPERATION_FAIL;
        String email = UserAccounts.validEmail;
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email);
        headers.putSingle("session_id", UserAccounts.session_id);
        ServiceResponse<CartInsertResponseModel> response = BillingSocket.postCartInsert(headers, email, "tt0lee7", quantity);
        CartInsertResponseModel expectedModel = new CartInsertResponseModel(expectedResult);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());

    }

    @After
    public void clearCartAfterAll()
    {
        clearCart(UserAccounts.validEmail, UserAccounts.session_id);
        //send request to clear cart
    }
}
