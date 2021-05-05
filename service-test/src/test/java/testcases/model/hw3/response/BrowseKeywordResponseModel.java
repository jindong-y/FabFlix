package testcases.model.hw3.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.common.Result;
import testcases.model.hw3.submodels.BrowseMovieModel;
import testcases.model.hw3.submodels.MovieModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrowseKeywordResponseModel extends ResponseModel {

    @JsonProperty("movies")
    private BrowseMovieModel[] movies;

    public BrowseKeywordResponseModel()
    {

    }

    public BrowseKeywordResponseModel(Result result) {
        super(result);
    }

    public BrowseKeywordResponseModel(Result result, BrowseMovieModel[] movies) {
        super(result);
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if (!super.equals(o)) {
            return false;
        }

        BrowseKeywordResponseModel that = (BrowseKeywordResponseModel) o;

        if (this.movies == null)
        {
            return that.movies == null;
        }
        //Thumbnail response arrays were not equal.
        if (this.movies.length != that.movies.length)
            return false;
        else
        {
            for (int i = 0; i < this.movies.length; ++i)
            {
                if (!(this.movies[i].equals(that.movies[i])))
                    return false;

            }

            return true;
        }
    }

    public void setMovies(BrowseMovieModel[] movies) {
        this.movies = movies;
    }

    public BrowseMovieModel[] getMovies() {
        return movies;
    }
}
