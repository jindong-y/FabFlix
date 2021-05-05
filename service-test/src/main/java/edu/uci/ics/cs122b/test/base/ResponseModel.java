package edu.uci.ics.cs122b.test.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.common.Result;

import java.util.Objects;

public abstract class ResponseModel
{
    @JsonProperty("resultCode")
    private int resultCode;
    @JsonProperty("message")
    private String message;

    public ResponseModel(){}

    public ResponseModel(Result result)
    {
        this.resultCode = result.getResultCode();
        this.message = result.getMessage();
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(int resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    // ---------------------------------------------------------------
    // Comparators
    // ---------------------------------------------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ResponseModel that = (ResponseModel) o;

        return resultCode == that.resultCode;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(resultCode);
    }
}
