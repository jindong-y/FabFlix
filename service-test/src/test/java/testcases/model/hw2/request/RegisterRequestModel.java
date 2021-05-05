package testcases.model.hw2.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequestModel
{
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private char[] password;

    public RegisterRequestModel(String email, char[] password)
    {
        this.email = email;
        this.password = password;
    }

    public RegisterRequestModel()
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
