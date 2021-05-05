package testcases.model.hw3.submodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ThumbnailModel {

    @JsonProperty("movie_id")
    String movie_id;
    @JsonProperty("title")
    String title;
    @JsonProperty("backdrop_path")
    String backdrop_path;
    @JsonProperty("poster_path")
    String poster_path;

    public ThumbnailModel(@JsonProperty(value = "movie_id") String movie_id,
                          @JsonProperty(value = "title") String title,
                          @JsonProperty(value = "backdrop_path") String backdrop_path,
                          @JsonProperty(value = "poster_path") String poster_path) {
        this.movie_id = movie_id;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
    }

    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ThumbnailModel that = (ThumbnailModel) o;
        if (!(Objects.equals(this.movie_id, that.movie_id)))
            return false;

        if (!Objects.equals(this.title, that.title))
            return false;

        if (!Objects.equals(this.backdrop_path, that.backdrop_path))
            return false;

        //poster path not required, so if it's not nul then we can check. otherwise if it is null and the other one is not null then we hae an issue
        if (!Objects.equals(this.poster_path, that.poster_path))
            return false;
        // we good



        //All parameters were equal. We good.
        return true;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
