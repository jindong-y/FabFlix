package testcases.model.hw2.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestModel
{
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private char[] password;

    public LoginRequestModel(String email, char[] password)
    {
        this.email = email;
        this.password = password;
    }

    public LoginRequestModel()
    {
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public char[] getPassword()
    {
        return password;
    }

    public void setPassword(char[] password)
    {
        this.password = password;
    }
}
