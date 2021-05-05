package testcases.tests.apigateway;

import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw2.response.LoginResponseModel;
import testcases.model.hw3.response.GetMovieResponseModel;
import testcases.model.hw3.response.MovieSearchResponseModel;
import testcases.socket.ApiGatewaySocket;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ApiGatewayTestMovies {

    @Test
    public void movieSearch(){

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();

        headers.putSingle("email", "apiGatewayTest@uci.edu" );
        headers.putSingle("session_id", "d0235ee6d5701c537ff0803c1ae68252dcf0e0cd59ffa19391609d6802b4403470800e932e0caf59082ad4b9ca6840a080f3efee0103902ebe8c9b2b74a75bad" );

        MultivaluedHashMap<String, Object> query = new MultivaluedHashMap<>();
        query.putSingle("director", "Anthony Russo");
        query.putSingle("hidden", "true");
        query.putSingle("limit", 9000);

        ServiceResponse<ResponseModel> response = ApiGatewaySocket.apiGetMovieSearch(headers, query);

        //response.getHeaders().forEach((k, v)->{ System.out.println("Key: " + k + " Value: " + v); });

        int responseCount = 0;

        for (int i = 0; i < 20; ++i){
            ServiceResponse<MovieSearchResponseModel> responseReal = ApiGatewaySocket.apiGetReport(MovieSearchResponseModel.class, new MultivaluedHashMap<String, Object>(response.getHeaders()));
            if (responseReal.getStatus() != 204)
                responseCount++;
            else
                System.out.println(responseReal.getStatus());

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Sleep Interrupted");
                //e.printStackTrace();

            }

        }
        assertEquals(204, (int)response.getStatus());
        assertEquals(1, responseCount);

    }

    @Test
    public void movieGetMovieById(){

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();

        headers.putSingle("email", "apiGatewayTest@uci.edu" );
        headers.putSingle("session_id", "d0235ee6d5701c537ff0803c1ae68252dcf0e0cd59ffa19391609d6802b4403470800e932e0caf59082ad4b9ca6840a080f3efee0103902ebe8c9b2b74a75bad" );


        ServiceResponse<ResponseModel> response = ApiGatewaySocket.apiGetMovieByID(headers, "tt4154796");

        //response.getHeaders().forEach((k, v)->{ System.out.println("Key: " + k + " Value: " + v); });

        int responseCount = 0;

        for (int i = 0; i < 20; ++i){
            ServiceResponse<GetMovieResponseModel> responseReal = ApiGatewaySocket.apiGetReport(GetMovieResponseModel.class, new MultivaluedHashMap<String, Object>(response.getHeaders()));
            if (responseReal.getStatus() != 204)
                responseCount++;
            else
                System.out.println(responseReal.getStatus());

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Sleep Interrupted");
                //e.printStackTrace();

            }

        }
        assertEquals(204, (int)response.getStatus());
        assertEquals(1, responseCount);

    }

}
