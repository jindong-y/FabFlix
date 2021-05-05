package edu.uci.ics.jindongy.service.billing.models.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jvnet.hk2.annotations.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieModel {
    @JsonProperty
    private String movie_id;

    @JsonProperty
    private String title;

    @JsonProperty
    @Optional
    private Integer year;

    @JsonProperty
    @Optional
    private String director;

    @JsonProperty
    @Optional
    private Float rating;

    @JsonProperty
    @Optional
    private String budget;

    @JsonProperty
    @Optional
    private Integer num_votes;

    @JsonProperty
    @Optional
    private String revenue;

    @JsonProperty
    @Optional
    private String overview;

    @JsonProperty
    @Optional
    private String backdrop_path;

    @JsonProperty
    @Optional
    private String poster_path;

    @JsonProperty
    @Optional
    private Boolean hidden;

    public MovieModel() {
    }

    public MovieModel(ResultSet rs,boolean isPrivilege) throws SQLException {
        this.movie_id=rs.getString("movie_id");
        this.title=rs.getString("title");
        this.year=rs.getInt("year");
        this.director=rs.getString("director");
        this.rating=rs.getFloat("rating");
        this.backdrop_path=rs.getString("backdrop_path");
        this.poster_path=rs.getString("poster_path");
        this.hidden=isPrivilege ? rs.getBoolean("hidden") : null;
    }


    public MovieModel(String movie_id, String title, String backdrop_path, String poster_path) {
        this.movie_id = movie_id;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
    }


    //search, browse
    public MovieModel(String movie_id, String title, int year, String director, float rating, String backdrop_path, String poster_path, Boolean hidden) {
        this.movie_id = movie_id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.hidden = hidden;
    }
    //getmovie_id


    public MovieModel(String movie_id, String title, int year, String director, float rating, int num_votes, String budget, String revenue, String overview, String backdrop_path, String poster_path, Boolean hidden) {
        this.movie_id = movie_id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.num_votes = num_votes;
        this.budget = budget;
        this.revenue = revenue;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.hidden = hidden;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setNum_votes(int num_votes) {
        this.num_votes = num_votes;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
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
