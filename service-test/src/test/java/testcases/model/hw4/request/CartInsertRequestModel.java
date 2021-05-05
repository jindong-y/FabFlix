package testcases.model.hw4.request;

public class CartInsertRequestModel {
    private String email;
    private String movie_id;
    private Integer quantity;

    public CartInsertRequestModel(String email, String movie_id, Integer quantity) {
        this.email = email;
        this.movie_id = movie_id;
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
