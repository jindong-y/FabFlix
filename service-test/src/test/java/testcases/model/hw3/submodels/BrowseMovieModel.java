package testcases.model.hw3.submodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class BrowseMovieModel {

    @JsonProperty("movie_id")
    String movie_id;
    @JsonProperty("title")
    String title;
    @JsonProperty("year")
    Integer year;
    @JsonProperty("director")
    String director;
    @JsonProperty("rating")
    Float rating;

    @JsonProperty("backdrop_path")
    String backdrop_path;
    @JsonProperty("poster_path")
    String poster_path;
    @JsonProperty("hidden")
    Boolean hidden;


    public BrowseMovieModel(@JsonProperty(value = "movie_id", required = true) String movie_id,
                      @JsonProperty(value = "title",required = true) String title,
                      @JsonProperty(value = "year", required = true) Integer year,
                      @JsonProperty(value = "director", required = true) String director,
                      @JsonProperty(value = "rating", required = true) Float rating,
                      @JsonProperty(value = "backdrop_path") String backdrop_path,
                      @JsonProperty(value = "poster_path") String poster_path,
                      @JsonProperty(value = "hidden") Boolean hidden) {
        this.movie_id = movie_id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;

        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.hidden = hidden;

    }

    public String getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public Float getRating() {
        return rating;
    }




    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Boolean getHidden() {
        return hidden;
    }


    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BrowseMovieModel that = (BrowseMovieModel) o;

        if (!(Objects.equals(this.movie_id, that.movie_id)))
            return false;

        if (!Objects.equals(this.title, that.title))
            return false;

        if (!Objects.equals(this.year, that.year))
            return false;

        if (!Objects.equals(this.director, that.director))
            return false;
        if (!Objects.equals(this.rating, that.rating))
            return false;

        if (!Objects.equals(this.backdrop_path, that.backdrop_path))
            return false;

        //poster path not required, so if it's not nul then we can check. otherwise if it is null and the other one is not null then we hae an issue
        if (!Objects.equals(this.poster_path, that.poster_path))
            return false;

        if(!Objects.equals(this.hidden, that.hidden))
            return false;

        //All parameters were equal. We good.
        return true;
    }
}
