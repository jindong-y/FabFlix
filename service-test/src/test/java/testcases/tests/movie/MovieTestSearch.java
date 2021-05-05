package testcases.tests.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw3.request.*;

import testcases.socket.IdmSocket;
import testcases.socket.MovieSocket;

import testcases.model.hw3.response.*;


import testcases.model.hw3.submodels.*;
import testcases.tests.HeaderTest.HeaderCheck;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.junit.Assert.*;


public class MovieTestSearch {

    @Test
    public void validPrivilege_validDirectorInvalidOffset(){

        Result expectedResult = Result.MOVIES_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail );
        headers.putSingle("session_id", UserAccounts.session_id );
        headers.putSingle("transaction_id", UserAccounts.transaction_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("title", "Avengers: Endgame");
        query.putSingle("hidden", "true");
        query.putSingle("offset", 59);


        ServiceResponse<MovieSearchResponseModel> response = MovieSocket.getSearch(headers, query);

        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(response.getHeaders())));

        Movie[] movieResults = response.getEntity().getMovies();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(1, movieResults.length);
        assertEquals("tt4154796", movieResults[0].getMovie_id());

        assertEquals(false, movieResults[0].getHidden());

    }

    @Test
    public void invalidPrivilege_validTitleInvalidLimit(){

        Result expectedResult = Result.MOVIES_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.invalidEmail );
        headers.putSingle("session_id", UserAccounts.session_id );

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("director", "Anthony Russo");
        query.putSingle("hidden", "true");
        query.putSingle("limit", 9000);


        ServiceResponse<MovieSearchResponseModel> response = MovieSocket.getSearch(headers, query);

        Movie[] movieResults = response.getEntity().getMovies();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(3, movieResults.length);
        assertEquals("tt4154796", movieResults[0].getMovie_id());
        assertEquals(null, movieResults[0].getHidden());


    }

    @Test
    public void invalidPrivilege_validGenreInvalidOrderby(){

        Result expectedResult = Result.MOVIES_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.invalidEmail );
        headers.putSingle("session_id", UserAccounts.session_id );

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("orderby", "notPopularity");
        query.putSingle("genre", "action");
        query.putSingle("title", "Avengers");


        ServiceResponse<MovieSearchResponseModel> response = MovieSocket.getSearch(headers, query);

        Movie[] movieResults = response.getEntity().getMovies();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(5, movieResults.length);
        assertEquals("tt4296026", movieResults[0].getMovie_id());
        assertEquals(null, movieResults[0].getHidden());


    }

    @Test
    public void invalidPrivilege_invalidTitle(){

        Result expectedResult = Result.MOVIES_NOT_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.invalidEmail );
        headers.putSingle("session_id", UserAccounts.session_id );

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("title", "MovieDoesNotExist");

        ServiceResponse<MovieSearchResponseModel> response = MovieSocket.getSearch(headers, query);

        Movie[] movieResults = response.getEntity().getMovies();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertNull(movieResults);


    }

}

