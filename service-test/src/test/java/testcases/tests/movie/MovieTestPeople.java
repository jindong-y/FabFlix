package testcases.tests.movie;


import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw3.response.MovieSearchResponseModel;
import testcases.model.hw3.response.PeopleSearchResponseModel;
import testcases.model.hw3.submodels.Movie;
import testcases.socket.MovieSocket;
import testcases.tests.HeaderTest.HeaderCheck;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.junit.Assert.*;

public class MovieTestPeople {

    @Test
    public void invalidPrivilege_invalidName(){

        Result expectedResult = Result.MOVIES_NOT_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.invalidEmail );
        headers.putSingle("session_id", UserAccounts.session_id );
        headers.putSingle("transaction_id", UserAccounts.transaction_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("name", "nameDoesNotExist");

        ServiceResponse<PeopleSearchResponseModel> response = MovieSocket.getPeople(headers, query);

        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(response.getHeaders())));


        Movie[] movieResults = response.getEntity().getMovies();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertNull(movieResults);

    }

    @Test
    public void invalidPrivilege_validName(){

        Result expectedResult = Result.MOVIES_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.invalidEmail );
        headers.putSingle("session_id", UserAccounts.session_id );

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("name", "William Bryant");

        ServiceResponse<PeopleSearchResponseModel> response = MovieSocket.getPeople(headers, query);

        Movie[] movieResults = response.getEntity().getMovies();

        assertEquals(expectedResult.getStatus(), response.getStatus());

        assertEquals(2, movieResults.length);
        assertEquals("tt0068408", movieResults[0].getMovie_id());
        assertEquals(null, movieResults[0].getHidden());


    }

    @Test
    public void validPrivilege_validName(){

        Result expectedResult = Result.MOVIES_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail );
        headers.putSingle("session_id", UserAccounts.session_id );

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("name", "William Bryant");

        ServiceResponse<PeopleSearchResponseModel> response = MovieSocket.getPeople(headers, query);

        Movie[] movieResults = response.getEntity().getMovies();

        assertEquals(expectedResult.getStatus(), response.getStatus());

        assertEquals(2, movieResults.length);
        assertEquals("tt0068408", movieResults[0].getMovie_id());
        assertEquals(false, movieResults[0].getHidden());


    }
}