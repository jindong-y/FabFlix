package testcases.model.hw4.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.common.Result;
import testcases.model.hw4.submodels.ItemModel;

public class CartRetrieveResponseModel  extends ResponseModel {
    @JsonProperty("items")
    private ItemModel[] items;
    public CartRetrieveResponseModel()
    {

    }
    public CartRetrieveResponseModel(Result result) {
        super(result);
    }

    public CartRetrieveResponseModel(Result result, ItemModel[] items)
    {
        super(result);
        this.items = items;
    }

    public ItemModel[] getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        CartRetrieveResponseModel that = (CartRetrieveResponseModel) o;
        if (items == null && that.getItems() != null)
            return false;
        else if (items == null && that.getItems() == null)
            return true;

        for (int i = 0; i < items.length; ++i)
        {
            int that_index = this.findId(that.getItems(), items[i].getMovie_id());
            if (that_index == -1)
                return false;
            if (!items[i].equals(that.getItems()[that_index]))
                return false;
        }
        return super.equals(o);
    }

    public int findId(ItemModel[] items, String movie_id)
    {
        for (int i = 0; i < items.length; ++i)
        {
            if (items[i].getMovie_id().equals(movie_id))
                return i;
        }
        return -1;
    }
}
