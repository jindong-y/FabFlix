package testcases.model.hw3.response;
/**
 * @author Benjamin Chhen
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import testcases.model.hw3.submodels.Movie;
import edu.uci.ics.cs122b.test.common.Result;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
@JsonPropertyOrder({"resultCode","message","person"} )
public class PeopleSearchResponseModel extends ResponseModel {
    private Movie[] movies;

    public PeopleSearchResponseModel() {
    }

    public PeopleSearchResponseModel(Result result, Movie[] movies) {
        super(result);
        this.movies = movies;
    }

    public Movie[] getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "PeopleSearchResponseModel{" +
                "movies=" + Arrays.toString(movies) +
                '}';
    }
}
