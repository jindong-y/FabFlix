package testcases.model.hw1.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.base.ResponseModel;

import java.util.Objects;

public class ReverseResponseModel extends ResponseModel
{
    @JsonProperty("reversed")
    private String reversed;

    public ReverseResponseModel()
    {
        super();
    }

    public ReverseResponseModel(Result result, String reversed)
    {
        super(result);
        this.reversed = reversed;
    }

    public String getReversed()
    {
        return reversed;
    }

    public void setReversed(String reversed)
    {
        this.reversed = reversed;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        if (!super.equals(o))
            return false;

        ReverseResponseModel that = (ReverseResponseModel) o;

        return Objects.equals(reversed, that.reversed);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), reversed);
    }
}
