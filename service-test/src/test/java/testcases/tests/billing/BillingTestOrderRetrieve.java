package testcases.tests.billing;

import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.*;
import org.junit.experimental.theories.suppliers.TestedOn;
import testcases.model.hw4.response.CartRetrieveResponseModel;
import testcases.model.hw4.submodels.ItemModel;
import testcases.socket.BillingSocket;
import testcases.tests.HeaderTest.HeaderCheck;
import testcases.tests.movie.UserAccounts;

import javax.ws.rs.core.MultivaluedHashMap;

import java.util.Hashtable;

import static org.junit.Assert.*;

public class BillingTestOrderRetrieve {
    private static Hashtable<String, Float> moviePriceTable = new Hashtable<>();
    private static Hashtable<String, Float> movieDiscountTable = new Hashtable<>();
    private static Hashtable<String, String> movieTitleTable = new Hashtable<>();

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
        //Clear the cart
        clearCart(UserAccounts.validEmail, UserAccounts.session_id);

    }

    @BeforeClass
    public static void initHashTables()
    {
        moviePriceTable = new Hashtable<>();
        movieDiscountTable = new Hashtable<>();
        movieTitleTable = new Hashtable<>();
        //Init all movie prices into the hash table before running tests.
        moviePriceTable.put("tt0040366", 0.1f);
        movieDiscountTable.put("tt0040366", 0.2f);
        movieTitleTable.put("tt0040366", "Force of Evil");

        moviePriceTable.put("tt3671086", 0.1f);
        movieDiscountTable.put("tt3671086", 0.2f);

        moviePriceTable.put("tt3675568", 0.1f);
        movieDiscountTable.put("tt3675568", 0.2f);
        movieTitleTable.put("tt3675568","Merchants of Doubt");

        moviePriceTable.put("tt0071853", 0.1f);
        movieDiscountTable.put("tt0071853", 0.2f);
        movieTitleTable.put("tt0071853","Monty Python and the Holy Grail");
    }


    @Test
    //This test will do a deep comparison of expected item and result.
    public  void valid_CartRetrieveOneItemDeepCompare()
    {
        int quantity = 4;

        ItemModel[] items = {new ItemModel(UserAccounts.validEmail, 0.1f, 0.2f,quantity,"tt0040366", "Force of Evil", "/e9aYe86CttMkpf7oulEtMqmZjNy.jpg", "/piKroeALv3ckaZe9QroagMDfVse.jpg")};

        String email = UserAccounts.validEmail;
        Result expectedResult = Result.CART_RETRIEVE_SUCCESS;
        CartRetrieveResponseModel expectedModel = new CartRetrieveResponseModel(expectedResult, items);
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id );
        headers.putSingle("transaction_id", UserAccounts.transaction_id);


        BillingSocket.postCartInsert(headers, email, "tt0040366", quantity);
        ServiceResponse<CartRetrieveResponseModel> responseRetrieve = BillingSocket.postCartRetrieve(headers, email);

        //check headers
        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(responseRetrieve.getHeaders())));

        assertEquals(expectedResult.getStatus(), responseRetrieve.getStatus());
        assertEquals(expectedResult.getResultCode(), responseRetrieve.getEntity().getResultCode());
        //test length
        assertEquals(1, responseRetrieve.getEntity().getItems().length);
        assertEquals(expectedModel, responseRetrieve.getEntity());
    }

    @Test
    public void valid_CartRetrieveThreeItem()
    {
        String[] movie_ids = {"tt0040366", "tt3675568", "tt0071853"};
        String email = UserAccounts.validEmail;
        Result expectedResult = Result.CART_RETRIEVE_SUCCESS;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id );

        BillingSocket.postCartInsert(headers, email, movie_ids[0], 5);
        BillingSocket.postCartInsert(headers, email, movie_ids[1], 1);
        BillingSocket.postCartInsert(headers, email, movie_ids[2], 2);
        ServiceResponse<CartRetrieveResponseModel> responseRetrieve = BillingSocket.postCartRetrieve(headers, email);
        ItemModel[] items = responseRetrieve.getEntity().getItems();
        assertEquals(items.length, movie_ids.length);
        assertEquals(movie_ids[0], items[this.findId(items, movie_ids[0])].getMovie_id());
        assertEquals(expectedResult.getStatus(), responseRetrieve.getStatus());
        assertEquals(expectedResult.getResultCode(), responseRetrieve.getEntity().getResultCode());

        assertEquals(movieTitleTable.get(movie_ids[2]), items[this.findId(items, movie_ids[2])].getMovie_title());
        // assertEquals(movieDiscountTable.get(movie_ids[1]), items[1].getDiscount());
        //assertEquals(moviePriceTable.get(movie_ids[0]), items[0].getUnit_price());

    }

    @Test
    public void valid_CartRetrieveEmpty()
    {
        Result expectedResult = Result.ITEM_DNE;
        String email = UserAccounts.validEmail;
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email );
        headers.putSingle("session_id", UserAccounts.session_id );

        ServiceResponse<CartRetrieveResponseModel> responseRetrieve = BillingSocket.postCartRetrieve(headers, email);
        assertEquals(expectedResult.getStatus(), responseRetrieve.getStatus());
        assertEquals(expectedResult.getResultCode(), responseRetrieve.getEntity().getResultCode());
        assertNull(responseRetrieve.getEntity().getItems());

    }
    @After
    public void clearCartAfterAll()
    {
        clearCart(UserAccounts.validEmail, UserAccounts.session_id);
        //clear cart
    }

    public int findId(ItemModel[] items, String movie_id)
    {
        for (int i = 0; i < items.length; ++i)
        {
            if (items[i].getMovie_id().equals(movie_id))
                return i;
        }
        return -1;
    }

}
