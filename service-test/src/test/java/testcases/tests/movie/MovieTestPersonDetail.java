package testcases.tests.movie;
//*********************************************
//
//                personDetail Tests
//
//*********************************************
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw3.response.GetMovieResponseModel;
import testcases.model.hw3.response.GetPersonByIDResponseModel;
import testcases.model.hw3.submodels.MovieModel;
import testcases.model.hw3.submodels.PeopleModel;
import testcases.model.hw3.submodels.Person;
import testcases.socket.MovieSocket;
import testcases.tests.HeaderTest.HeaderCheck;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.junit.Assert.*;

public class MovieTestPersonDetail {

    @Test
    public void get_Person_By_PersonID() {
        String email = UserAccounts.invalidEmail;
        String session_id = UserAccounts.session_id;
        String personID = "49";

        Result expectedResult = Result.PEOPLE_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email);
        headers.putSingle("session_id", session_id);
        headers.putSingle("transaction_id", UserAccounts.transaction_id);


        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();

        ServiceResponse<GetPersonByIDResponseModel> actualResponse = MovieSocket.getPersonByID(headers, query, personID);

        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(actualResponse.getHeaders())));

        PeopleModel person = actualResponse.getEntity().getPerson();

        assertEquals(expectedResult.getStatus(), actualResponse.getStatus());
        assertEquals((Integer) 49, person.getPerson_id());
        assertEquals("Maria Bello", person.getName());
        assertEquals((Float) 6.265f, person.getPopularity());

    }

    @Test
    public void get_Person_By_PersonID_NOT_FOUND() {
        String email = UserAccounts.invalidEmail;
        String session_id = UserAccounts.session_id;
        String personID = "0";

        Result expectedResult = Result.PEOPLE_NOT_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", email);
        headers.putSingle("session_id", session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();

        ServiceResponse<GetPersonByIDResponseModel> actualResponse = MovieSocket.getPersonByID(headers, query, personID);

        PeopleModel person = actualResponse.getEntity().getPerson();

        assertEquals(expectedResult.getStatus(), actualResponse.getStatus());
        assertNull(person);

    }
}
