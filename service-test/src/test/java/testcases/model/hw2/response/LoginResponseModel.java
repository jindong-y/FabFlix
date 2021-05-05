package testcases.model.hw2.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.base.ResponseModel;

import java.util.Objects;

public class LoginResponseModel extends ResponseModel
{
    @JsonProperty("session_id")
    private String session_id;

    public LoginResponseModel()
    {
    }

    public LoginResponseModel(Result result, String session_id)
    {
        super(result);
        this.session_id = session_id;
    }

    public LoginResponseModel(Result result)
    {
        super(result);
    }

    public String getSession_id()
    {
        return session_id;
    }

    public void setSession_id(String session_id)
    {
        this.session_id = session_id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if (!super.equals(o)) return false;

        LoginResponseModel that = (LoginResponseModel) o;

        return Objects.equals(session_id, that.session_id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), session_id);
    }
}
