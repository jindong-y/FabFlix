package testcases.tests.movie;
//*********************************************
//
//                movieDetail Tests
//
//*********************************************

import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw3.response.GetMovieResponseModel;
import testcases.model.hw3.submodels.MovieModel;
import testcases.socket.MovieSocket;
import testcases.tests.HeaderTest.HeaderCheck;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.junit.Assert.*;

public class MovieTestMovieDetail {

    @Test
    public void getMovieByMovieIDFound_Privileged() {
        String email = UserAccounts.validEmail;
        String session_id = UserAccounts.session_id;
        String movieID = "tt0003740";

        Result expectedResult = Result.MOVIES_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email);
        headers.putSingle("session_id", session_id);
        headers.putSingle("transaction_id", UserAccounts.transaction_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();

        ServiceResponse<GetMovieResponseModel> actualResponse = MovieSocket.getMovieByID(headers, query, movieID);

        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(actualResponse.getHeaders())));


        MovieModel movie = actualResponse.getEntity().getMovie();

        assertEquals(expectedResult.getStatus(), actualResponse.getStatus());

        assertEquals("tt0003740", movie.getMovie_id());
        assertEquals("Cabiria", movie.getTitle());
        assertEquals(false, movie.getHidden());

        assertEquals(2, movie.getGenres().length);
        assertEquals(15, movie.getPeople().length);
    }


    @Test
    public void get_Movie_By_MovieID_Found_Not_Enough_Privilege() {
        //
        String email = UserAccounts.invalidEmail;
        String session_id = UserAccounts.session_id;
        String movieID = "tt0003740";

        Result expectedResult = Result.MOVIES_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email);
        headers.putSingle("session_id", session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();

        ServiceResponse<GetMovieResponseModel> actualResponse = MovieSocket.getMovieByID(headers, query, movieID);

        MovieModel movie = actualResponse.getEntity().getMovie();

        assertEquals(expectedResult.getStatus(), actualResponse.getStatus());

        assertEquals("tt0003740", movie.getMovie_id());
        assertEquals("Cabiria", movie.getTitle());
        assertEquals(null, movie.getHidden());

        assertEquals(2, movie.getGenres().length);
        assertEquals(15, movie.getPeople().length);
    }




    @Test
    public void get_Movie_By_MovieID_NOT_Found() {
        String email = UserAccounts.invalidEmail;
        String session_id = UserAccounts.session_id;
        String movieID = "tt000";

        Result expectedResult = Result.MOVIES_NOT_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email);
        headers.putSingle("session_id", session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();

        ServiceResponse<GetMovieResponseModel> actualResponse = MovieSocket.getMovieByID(headers, query, movieID);
        MovieModel movie = actualResponse.getEntity().getMovie();
        assertEquals(expectedResult.getStatus(), actualResponse.getStatus());
        assertNull(movie);
    }

}