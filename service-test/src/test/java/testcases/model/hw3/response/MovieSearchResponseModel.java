package testcases.model.hw3.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.common.Result;
import testcases.model.hw3.submodels.Movie;

import java.awt.image.RescaleOp;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
@JsonPropertyOrder({"resultCode","message","movies"} )
public class MovieSearchResponseModel extends ResponseModel {

    private Movie[] movies;

    public MovieSearchResponseModel() {
    }

    public MovieSearchResponseModel(Result result, Movie[] movies) {
        super(result);
        this.movies = movies;
    }

    public MovieSearchResponseModel(Result result) {
        super(result);
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }

    public Movie[] getMovies() {
        return movies;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){return true;}
        if (o == null || getClass() != o.getClass()){return false;}
        if (!super.equals(o)) {return false;}
        MovieSearchResponseModel that = (MovieSearchResponseModel) o;
        return Objects.equals(movies, that.movies);
    }
}