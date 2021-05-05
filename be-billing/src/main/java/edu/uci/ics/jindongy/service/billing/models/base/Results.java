package edu.uci.ics.jindongy.service.billing.models.base;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public enum Results {
    INTERNAL_SERVER_ERROR(-1,"Internal Server Error.", Status.INTERNAL_SERVER_ERROR),
    JSON_PARSE_EXCEPTION(-3,"JSON Parse Exception.",Status.BAD_REQUEST),
    JSON_MAPPING_EXCEPTION(-2,"JSON Mapping Exception.",Status.BAD_REQUEST),
    USER_NOT_FOUND(14,"User not found.",Status.OK),
    INVALID_QUANTITY(33,"Quantity has invalid value.",Status.OK),
    DUPLICATE_INSERTION(311,"Ducplication,insertion.",Status.OK),
    CART_ITEM_INSERT_SUCCESSFUL(3100,"Shopping cart item inserted successfully.",Status.OK),
    CART_OPERATION_FAILED(3150,"Shopping cart operation failed.",Status.OK),
    CART_UPDATED_SUCCESSFUL(3110,"Shopping cart item updated successfully.",Status.OK),
    CART_ITEM_NOT_EXIST(312,"Shopping cart item does not exist.",Status.OK),
    CART_ITEM_DELETE_SUCCESSFUL(3120,"Shopping cart item deleted successfully.",Status.OK),
    CART_RETRIEVED_SUCCESSFUL(3130,"Shopping cart retrieved successfully.",Status.OK),
    CART_CLEAR_SUCCESSFUL(3140,"Shopping cart cleared successfully.",Status.OK),
    ORDER_CREATE_FAILED(342,"Order create failed",Status.OK),
    ORDER_PLACE_SUCCESSFUL(3400,"Order placed successfully",Status.OK),
    ORDER_HISTORY_NOT_EXIST(313,"Order history does not exist",Status.OK),
    ORDER_RETRIEVED_SUCCESSFUL(3410,"Order retrieved successfully",Status.OK),
    ORDER_COMPLETED_SUCCESSFUL(3420,"Order is completed successfully.",Status.OK),
    TOKEN_NOT_FOUND(3421,"Token not found",Status.OK),
    ORDER_CANNOT_COMPLETE(3422,"Order can not be completetd",Status.OK),
    FOUND_MOVIE(210,"Found movie(s) with search parameters.",Status.OK),
    FOUND_PEOPLE(212,"Found people with search parameters.",Status.OK),
    NO_MOVIE_FOUND(211,"No movies found with search parameters.",Status.OK),
    NO_PEOPLE_FOUND(213,"No people found with search parameters.",Status.OK);

    private final int resultCode;
    private final String message;
    private final Status status;

    Results(int resultCode, String message, Status status) {
        this.resultCode = resultCode;
        this.message = message;
        this.status = status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }
}
