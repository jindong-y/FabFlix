package testcases.model.hw2.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.common.Result;

import java.util.Objects;


public class SessionResponseModel extends ResponseModel
{
    @JsonProperty("session_id")
    private String session_id;

    public SessionResponseModel()
    {
    }

    public SessionResponseModel(Result result, String session_id)
    {
        super(result);
        this.session_id = session_id;
    }

    public SessionResponseModel(Result result)
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SessionResponseModel that = (SessionResponseModel) o;
        return Objects.equals(session_id, that.session_id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), session_id);
    }


}
