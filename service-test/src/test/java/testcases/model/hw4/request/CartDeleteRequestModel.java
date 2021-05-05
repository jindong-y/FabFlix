package testcases.model.hw4.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartDeleteRequestModel {

    private String email;

    private String movie_id;

    public CartDeleteRequestModel(
            @JsonProperty(value = "email", required=true) String email,
            @JsonProperty(value = "movie_id", required=true) String movie_id) {
        this.email = email;
        this.movie_id = movie_id;
    }

    public String getEmail() {
        return email;
    }

    public String getMovie_id() {
        return movie_id;
    }

}
