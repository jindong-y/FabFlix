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

import javax.jws.soap.SOAPBinding;
import javax.ws.rs.core.MultivaluedHashMap;

import static org.junit.Assert.*;

//*********************************************
//
//                people/search Tests
//
//*********************************************


public class MovieTestPeopleSearch {

    @Test
    public void validName() {

        Result expectedResult = Result.PEOPLE_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail);
        headers.putSingle("session_id", UserAccounts.session_id);
        headers.putSingle("transaction_id", UserAccounts.transaction_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("name", "Robert Downey Jr.");

        ServiceResponse<PeopleInfoSearchResponseModel> response = MovieSocket.getBrowseForPeopleInfo(headers, query);

        assertTrue(HeaderCheck.checkHeader(headers, new MultivaluedHashMap<String, Object>(response.getHeaders())));


        Person[] people = response.getEntity().getPeople();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(1, people.length);

        assertEquals((Integer) 3223, people[0].getPerson_id());

    }

    @Test
    public void validPartialNameOrderbyDirection() {

        Result expectedResult = Result.PEOPLE_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail);
        headers.putSingle("session_id", UserAccounts.session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("name", "pratt");
        query.putSingle("orderby", "popularity");
        query.putSingle("direction", "desc");

        ServiceResponse<PeopleInfoSearchResponseModel> response = MovieSocket.getBrowseForPeopleInfo(headers, query);

        Person[] people = response.getEntity().getPeople();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(10, people.length);
        assertEquals((Integer)73457, people[0].getPerson_id());

    }

    @Test
    public void validMovieTitleOrderbyDirection() {

        Result expectedResult = Result.PEOPLE_FOUND;

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail);
        headers.putSingle("session_id", UserAccounts.session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("movie_title", "Avengers");
        query.putSingle("orderby", "popularity");
        query.putSingle("direction", "desc");

        ServiceResponse<PeopleInfoSearchResponseModel> response = MovieSocket.getBrowseForPeopleInfo(headers, query);

        Person[] people = response.getEntity().getPeople();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(10, people.length);
        assertEquals((Integer)1245, people[0].getPerson_id());
        assertEquals((Integer)17605, people[9].getPerson_id());


    }

    @Test
    public void validyMovieTitleWithInvalidLimitandOffset() {

        Result expectedResult = Result.PEOPLE_FOUND;
        /*
        Person[] personModels = new Person[]{
                new Person(85,
                        "Johnny Depp",
                        "1963-06-09",
                        13.359f,
                        "/kbWValANhZI8rbWZXximXuMN4UN.jpg"),
                new Person(71580,
                        "Benedict Cumberbatch",
                        "1976-07-19",
                        9.403f,
                        "/wz3MRiMmoz6b5X3oSzMRC9nLxY1.jpg"),
                new Person(5081,
                        "Emily Blunt",
                        "1983-02-23",
                        8.948f,
                        "/jqlqq3knztTnty5rcMg5evqZRCa.jpg"),
                new Person(5530,
                        "James McAvoy",
                        "1979-04-21",
                        8.541f,
                        "/oPIfGm3mf4lbmO5pWwMvfTt5BM1.jpg"),
                new Person(7060,
                        "Martin Freeman",
                        "1971-09-08",
                        6.266f,
                        "/EOujE6oH4TdqShKWfsiBaup4FS.jpg"),
                new Person(3895,
                        "Michael Caine",
                        "1933-03-14",
                        5.164f,
                        "/fx6vcV7iTo2SNywQRPfh6v62wRR.jpg"),
                new Person(8727,
                        "Basil Rathbone",
                        "1892-06-13",
                        4.868f,
                        "/vHdw4YuybYJqISjZoLQtC2Hm3L0.jpg"),
                new Person(5294,
                        "Chiwetel Ejiofor",
                        "1977-07-10",
                        4.539f,
                        "/kq5DDnqqofoRI0t6ddtRlsJnNPT.jpg"),
                new Person(113,
                        "Christopher Lee",
                        "1922-05-27",
                        4.21f,
                        "/yzE2U4sS46sVRyCUKpgs3lYZaMy.jpg"),
                new Person(2462,
                        "Catherine McCormack",
                        "1972-01-01",
                        3.22f,
                        "/a8Jszh3rHQ2T4u1VyE4lXrb0uDI.jpg")
        };
        PeopleInfoSearchResponseModel expectedModel = new PeopleInfoSearchResponseModel(expectedResult, personModels);
*/
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail);
        headers.putSingle("session_id", UserAccounts.session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("movie_title", "Sherlock");
        query.putSingle("orderby", "popularity");
        query.putSingle("direction", "desc");
        query.putSingle("limit", 300);
        query.putSingle("offset", -4);

        ServiceResponse<PeopleInfoSearchResponseModel> response = MovieSocket.getBrowseForPeopleInfo(headers, query);

        Person[] people = response.getEntity().getPeople();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(10, people.length);
        assertEquals((Integer)85, people[0].getPerson_id());
        assertEquals((Integer)5081, people[2].getPerson_id());

    }

    @Test
    public void validBirthday() {

        Result expectedResult = Result.PEOPLE_FOUND;
        Person[] personModels = new Person[]{
                new Person(3,
                        "Harrison Ford",
                        "1942-07-13",
                        7.079f,
                        "/7CcoVFTogQgex2kJkXKMe8qHZrC.jpg")
        };

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail);
        headers.putSingle("session_id", UserAccounts.session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("birthday", "1942-07-13");

        ServiceResponse<PeopleInfoSearchResponseModel> response = MovieSocket.getBrowseForPeopleInfo(headers, query);

        Person[] people = response.getEntity().getPeople();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(1, people.length);
        assertEquals((Integer)3, people[0].getPerson_id());

    }

    @Test
    public void invalidName() {

        Result expectedResult = Result.PEOPLE_NOT_FOUND;

        PeopleInfoSearchResponseModel expectedModel = new PeopleInfoSearchResponseModel(expectedResult, null);

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail);
        headers.putSingle("session_id", UserAccounts.session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("name", "iDontExist");

        ServiceResponse<PeopleInfoSearchResponseModel> response = MovieSocket.getBrowseForPeopleInfo(headers, query);

        Person[] people = response.getEntity().getPeople();

        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertNull( people);

    }

    @Test
    public void invalidMovieTitleValidName() {

        Result expectedResult = Result.PEOPLE_NOT_FOUND;

        PeopleInfoSearchResponseModel expectedModel = new PeopleInfoSearchResponseModel(expectedResult, null);

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail);
        headers.putSingle("session_id", UserAccounts.session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("movie_title", "iDontExist");
        query.putSingle("name", "Downey");

        ServiceResponse<PeopleInfoSearchResponseModel> response = MovieSocket.getBrowseForPeopleInfo(headers, query);

        Person[] people = response.getEntity().getPeople();
        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertNull( people);
    }

    @Test
    public void invalidBirthday() {

        Result expectedResult = Result.PEOPLE_NOT_FOUND;

        PeopleInfoSearchResponseModel expectedModel = new PeopleInfoSearchResponseModel(expectedResult, null);

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("email", UserAccounts.validEmail);
        headers.putSingle("session_id", UserAccounts.session_id);

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("movie_title", "Sherlock");
        query.putSingle("birthday", "2019-12-25");

        ServiceResponse<PeopleInfoSearchResponseModel> response = MovieSocket.getBrowseForPeopleInfo(headers, query);

        Person[] people = response.getEntity().getPeople();
        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertNull( people);

    }

}