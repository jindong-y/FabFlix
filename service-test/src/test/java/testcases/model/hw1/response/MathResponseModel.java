package testcases.model.hw1.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.base.ResponseModel;

import java.util.Objects;

public class MathResponseModel extends ResponseModel
{
    @JsonProperty("value")
    private Integer value;

    public MathResponseModel()
    {
    }

    public MathResponseModel(Result result, Integer value)
    {
        super(result);
        this.value = value;
    }

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
    {
        this.value = value;
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

        MathResponseModel that = (MathResponseModel) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), value);
    }
}
