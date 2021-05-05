package testcases.model.hw3.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.common.Result;
import testcases.model.hw3.submodels.MovieModel;

public class GetMovieResponseModel extends ResponseModel{
    @JsonInclude(JsonInclude.Include.NON_NULL)

    @JsonProperty("movie")
    MovieModel movie;

    public GetMovieResponseModel() {
    }

    public GetMovieResponseModel(Result result){
        super(result);
    }

    public GetMovieResponseModel(Result result, MovieModel movie){
        super(result);
        this.movie = movie;
    }

    public MovieModel getMovie() {
        return movie;
    }

    public void setMovie(MovieModel movie) {
        this.movie = movie;
    }


}
