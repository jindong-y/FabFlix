package testcases.model.hw4.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartRetrieveRequestModel {

    private String email;

    public CartRetrieveRequestModel(@JsonProperty(value = "email", required= true) String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
