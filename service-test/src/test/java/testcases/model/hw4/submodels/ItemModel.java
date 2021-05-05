package testcases.model.hw4.submodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import testcases.model.hw3.submodels.BrowseMovieModel;

public class ItemModel {
    private String email;
    private Float unit_price;
    private Float discount;
    private Integer quantity;
    private String movie_id;
    private String movie_title;
    private String backdrop_path;

    private String poster_path;

    public ItemModel( @JsonProperty(value="email", required=true)String email,
                     @JsonProperty(value = "unit_price", required=true) Float unit_price,
                     @JsonProperty(value="discount", required=true) Float discount,
                     @JsonProperty(value="quantity", required=true) Integer quantity,
                     @JsonProperty(value="movie_id", required=true) String movie_id,
                     @JsonProperty(value="movie_title", required = true) String movie_title,
                     @JsonProperty(value="backdrop_path") String backdrop_path,
                     @JsonProperty(value="poster_path") String poster_path) {
        this.email = email;
        this.unit_price = unit_price;
        this.discount = discount;
        this.quantity = quantity;
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
    }

    public String getEmail() {
        return email;
    }

    public Float getUnit_price() {
        return unit_price;
    }

    public Float getDiscount() {
        return discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public boolean equals(Object o) {
        ItemModel that = (ItemModel) o;
        if (o == null || getClass() != o.getClass()) return false;

        if (o == this)
            return true;

        if (!that.email.equals(this.email))
                return false;
        /*
        if (!that.unit_price.equals(this.unit_price))
            return false;
        if (!that.discount.equals(this.discount))
            return false;

         */
        if (!that.quantity.equals(quantity))
            return false;
        if (!that.movie_id.equals(this.movie_id))
            return false;
        if (!that.movie_title.equals(this.movie_title))
            return false;
        if (this.poster_path == null && that.poster_path != null)
            return false;
        else if (this.poster_path != null && !this.poster_path.equals(that.poster_path))
            return false;

        if (that.backdrop_path == null && this.backdrop_path != null)
            return false;
        else if (this.poster_path!= null && !this.backdrop_path.equals(that.backdrop_path))
                return false;

        return true;
    }
}
