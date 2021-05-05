package testcases.model.hw3.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.common.Result;
import testcases.model.hw3.submodels.ThumbnailModel;

import java.util.Objects;

public class ThumbnailResponseModel extends ResponseModel {
    @JsonProperty("thumbnails")
    ThumbnailModel[] thumbnails;

    public ThumbnailResponseModel()
    {

    }

    public ThumbnailResponseModel(Result result)
    {
        super(result);

    }
    public ThumbnailResponseModel(Result result, ThumbnailModel[] thumbnails)
    {
        super(result);
        this.thumbnails = thumbnails;

    }
    public ThumbnailModel[] getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ThumbnailModel[] thumbnails) {
        this.thumbnails = thumbnails;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if (!super.equals(o))
        {
            return false;
        }

        ThumbnailResponseModel that = (ThumbnailResponseModel) o;
        if (this.thumbnails == null)
        {
            return that.thumbnails == null;
        }
        //Thumbnail response arrays were not equal.
        if (this.thumbnails.length != that.thumbnails.length)
            return false;
        else
        {
            for (int i = 0; i < this.thumbnails.length; ++i)
            {
                if (!(this.thumbnails[i].equals(that.thumbnails[i])))
                    return false;

            }

            return true;
        }
    }

    public int hashCode()
    {
        return Objects.hash(super.hashCode(), thumbnails);
    }

}
