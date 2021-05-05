package testcases.model.hw4.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartClearRequestModel {
    private String email;

    public CartClearRequestModel(@JsonProperty(value = "email", required=true) String email)
    {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
