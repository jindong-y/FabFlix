package testcases.model.hw2.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionRequestModel
{
    @JsonProperty("email")
    private String email;
    @JsonProperty("session_id")
    private String session_id;

    public SessionRequestModel(String email, String session_id)
    {
        this.email = email;
        this.session_id = session_id;
    }

    public SessionRequestModel()
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

    public String getSession_id()
    {
        return session_id;
    }

    public void setSession_id(String session_id)
    {
        this.session_id = session_id;
    }
}
