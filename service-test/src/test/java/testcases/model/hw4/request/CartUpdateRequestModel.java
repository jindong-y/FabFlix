package testcases.model.hw4.request;

public class CartUpdateRequestModel {
    private String email;
    private String movie_id;
    private Integer quantity;

    public CartUpdateRequestModel(String email, String movie_id, Integer quantity) {
        this.email = email;
        this.movie_id = movie_id;
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
