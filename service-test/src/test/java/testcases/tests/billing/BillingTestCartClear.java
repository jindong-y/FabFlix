package testcases.tests.billing;

import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw4.response.CartClearResponseModel;
import testcases.model.hw4.response.CartDeleteResponseModel;
import testcases.model.hw4.response.CartInsertResponseModel;
import testcases.model.hw4.response.CartRetrieveResponseModel;
import testcases.model.hw4.submodels.ItemModel;
import testcases.socket.BillingSocket;
import testcases.tests.HeaderTest.HeaderCheck;
import testcases.tests.movie.UserAccounts;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BillingTestCartClear {

    @Test
    public void validClear(){

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail );
        headers.putSingle("session_id", UserAccounts.session_id );
        headers.putSingle("transaction_id", UserAccounts.transaction_id);

        String email = UserAccounts.validEmail;
        String movie_id = "tt4154796";
        Integer quantity = 1;

        //calling insert
        Result expectedResultInsert = Result.ITEM_INSERTION_SUCCESS;
        ServiceResponse<CartInsertResponseModel> responseInsert = BillingSocket.postCartInsert(headers, email, movie_id, quantity);
        assertEquals(expectedResultInsert.getStatus(), responseInsert.getStatus());

        //calling retrieve
        Result expectedResultRetrieveSuccess = Result.CART_RETRIEVE_SUCCESS;
        ServiceResponse<CartRetrieveResponseModel> responseRetrieveSuccess = BillingSocket.postCartRetrieve(headers, email);
        ItemModel[] itemModel = responseRetrieveSuccess.getEntity().getItems();

        assertEquals(expectedResultRetrieveSuccess.getStatus(), responseRetrieveSuccess.getStatus());
        assertEquals(1, itemModel.length);

        //calling clear
        Result expectedResultClear = Result.CART_CLEAR_SUCCESS;
        ServiceResponse<CartClearResponseModel> responseClear = BillingSocket.postCartClear(headers, email);
        assertEquals(expectedResultClear.getStatus(), responseClear.getStatus());

        //check headers
        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(responseClear.getHeaders())));

        //calling retrieve
        Result expectedResultRetrieve = Result.ITEM_DNE;
        ServiceResponse<CartRetrieveResponseModel> responseRetrieve = BillingSocket.postCartRetrieve(headers, email);
        assertEquals(expectedResultRetrieve.getStatus(), responseRetrieve.getStatus());

    }

    @Test
    public void invalidClear(){

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail );
        headers.putSingle("session_id", UserAccounts.session_id );

        String email = UserAccounts.validEmail;

        //calling clear
        Result expectedResultClear = Result.ITEM_DNE;
        ServiceResponse<CartClearResponseModel> responseClear = BillingSocket.postCartClear(headers, email);
        assertEquals(expectedResultClear.getStatus(), responseClear.getStatus());
    }

}
