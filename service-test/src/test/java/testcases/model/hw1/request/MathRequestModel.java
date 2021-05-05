package testcases.model.hw1.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MathRequestModel
{
    @JsonProperty("num_x")
    private Integer num_x;
    @JsonProperty("num_y")
    private Integer num_y;
    @JsonProperty("num_z")
    private Integer num_z;

    public MathRequestModel()
    {
    }

    public MathRequestModel(Integer num_x, Integer num_y, Integer num_z)
    {
        this.num_x = num_x;
        this.num_y = num_y;
        this.num_z = num_z;
    }

    public Integer getNum_x()
    {
        return num_x;
    }

    public void setNum_x(Integer num_x)
    {
        this.num_x = num_x;
    }

    public Integer getNum_y()
    {
        return num_y;
    }

    public void setNum_y(Integer num_y)
    {
        this.num_y = num_y;
    }

    public Integer getNum_z()
    {
        return num_z;
    }

    public void setNum_z(Integer num_z)
    {
        this.num_z = num_z;
    }
}
