package testcases.model.hw2.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrivilegeRequestModel
{
    @JsonProperty("email")
    private String email;
    @JsonProperty("plevel")
    private Integer plevel;

    public PrivilegeRequestModel(String email, Integer plevel)
    {
        this.email = email;
        this.plevel = plevel;
    }

    public PrivilegeRequestModel()
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

    public Integer getPlevel()
    {
        return plevel;
    }

    public void setPlevel(int plevel)
    {
        this.plevel = plevel;
    }
}
