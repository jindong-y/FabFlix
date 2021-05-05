package testcases.tests.billing;

import edu.uci.ics.cs122b.test.common.Json;
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import org.junit.Test; // for @Test
import org.junit.Before; // for @Before
import org.junit.After;
import testcases.model.hw4.response.CartInsertResponseModel;
import testcases.model.hw4.response.CartRetrieveResponseModel;
import testcases.model.hw4.response.CartUpdateResponseModel;
import testcases.model.hw4.submodels.ItemModel;
import testcases.socket.BillingSocket;
import testcases.tests.HeaderTest.HeaderCheck;
import testcases.tests.movie.UserAccounts;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.junit.Assert.*;

public class BillingTestCartUpdate {
    public static void clearCart(String email, String session_id)
    {
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", session_id);
        BillingSocket.postCartClear(headers, email);

    }

    @Before
    public void clearCartBeforeEach()
    {
        //Clear the cart
        clearCart(UserAccounts.validEmail, UserAccounts.session_id);

    }

    @Test
    public void updateParse(){
        Result expectedResult = Result.JSON_PARSE_EXCEPTION;
        CartUpdateResponseModel expectedModel = new CartUpdateResponseModel(expectedResult);
        String email = UserAccounts.validEmail;
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id );

        ServiceResponse<CartUpdateResponseModel> response = BillingSocket.postCartUpdate(headers, Json.PARSE_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void updateMapping(){
        Result expectedResult = Result.JSON_MAPPING_EXCEPTION;
        CartUpdateResponseModel expectedModel = new CartUpdateResponseModel(expectedResult);
        String email = UserAccounts.validEmail;
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id );

        ServiceResponse<CartUpdateResponseModel> response = BillingSocket.postCartUpdate(headers, Json.MAPPING_EXCEP);

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(expectedModel, response.getEntity());
    }

    @Test
    public void update_QuantityInvalidValue(){
        Result expectedResult = Result.QUANTITY_INVALID;
        CartUpdateResponseModel expectedModel = new CartUpdateResponseModel(expectedResult);
        String email = UserAccounts.validEmail;
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id );
        headers.putSingle("transaction_id", UserAccounts.transaction_id);


        int insertQuantity = 4;
        ServiceResponse<CartInsertResponseModel> insertResponse = BillingSocket.postCartInsert(headers, UserAccounts.validEmail, "tt4154796", insertQuantity);

        int updateQuantity = -1;
        ServiceResponse<CartUpdateResponseModel> updateResponse = BillingSocket.postCartUpdate(headers, UserAccounts.validEmail, "tt4154796", updateQuantity);

        //check headers
        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(updateResponse.getHeaders())));

        assertEquals(expectedResult.getStatus(), updateResponse.getStatus());
        assertEquals(expectedModel, updateResponse.getEntity());
    }

    @Test
    public void update_ItemNotExists(){
        Result expectedResult = Result.ITEM_DNE;
        CartUpdateResponseModel expectedModel = new CartUpdateResponseModel(expectedResult);

        int insertQuantity = 4;
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail );
        headers.putSingle("session_id", UserAccounts.session_id );

        ServiceResponse<CartInsertResponseModel> insertResponse = BillingSocket.postCartInsert(headers, UserAccounts.validEmail, "tt0003740", insertQuantity);

        int updateQuantity = 5;
        ServiceResponse<CartUpdateResponseModel> updateResponse = BillingSocket.postCartUpdate(headers, UserAccounts.validEmail, "NONEXISTENT", updateQuantity);

        assertEquals(expectedResult.getStatus(), updateResponse.getStatus());
        assertEquals(expectedModel, updateResponse.getEntity());
    }

    @Test
    public void updateSuccessfully(){
        Result expectedResult = Result.ITEM_UPDATE_SUCCESS;
        CartUpdateResponseModel expectedModel = new CartUpdateResponseModel(expectedResult);

        int insertQuantity = 4;
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail );
        headers.putSingle("session_id", UserAccounts.session_id );

        ServiceResponse<CartInsertResponseModel> insertResponse = BillingSocket.postCartInsert(headers, UserAccounts.validEmail, "tt0006177", insertQuantity);

        int updateQuantity = 5;
        ServiceResponse<CartUpdateResponseModel> updateResponse = BillingSocket.postCartUpdate(headers, UserAccounts.validEmail, "tt0006177", updateQuantity);

        assertEquals(expectedResult.getStatus(), updateResponse.getStatus());
        assertEquals(expectedModel, updateResponse.getEntity());

        ServiceResponse<CartRetrieveResponseModel> retrieveResponse = BillingSocket.postCartRetrieve(headers,UserAccounts.validEmail);
        CartRetrieveResponseModel cartRetrieveResponseModel = retrieveResponse.getEntity();
        Integer actualQuantity = this.findQuantityByID(cartRetrieveResponseModel,"tt0006177");

        assertEquals((Integer)updateQuantity, actualQuantity);
    }

    @After
    public void clearCartAfterAll()
    {
        clearCart(UserAccounts.validEmail, UserAccounts.session_id);
        //clear cart
    }

    public int findQuantityByID(CartRetrieveResponseModel cartRetrieveResponseModel, String movie_id){
        ItemModel[] items = cartRetrieveResponseModel.getItems();
        for (int i = 0; i<items.length; ++i){
            if (items[i].getMovie_id().equals(movie_id)){
                return items[i].getQuantity();
            }
        }
        return -1;
    }
}
