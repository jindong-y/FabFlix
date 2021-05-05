package edu.uci.ics.jindongy.service.billing.models.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;

import java.lang.invoke.SerializedLambda;

public class itemModel extends ThumbnailModel {
    @JsonProperty
    String email;

    @JsonProperty
    float unit_price;

    @JsonProperty
    float discount;

    @JsonProperty
    int quantity;

    @JsonProperty
    String movie_title;

    public itemModel( String email, float unit_price, float discount, int quantity) {
        this.email = email;
        this.unit_price = unit_price;
        this.discount = discount;
        this.quantity = quantity;
    }
    public void setThumbnail(ThumbnailModel thumbnail){
        ServiceLogger.LOGGER.info("Setting thumbnail");
        this.setMovie_id(thumbnail.getMovie_id());
        this.movie_title=thumbnail.getTitle();
        this.setBackdrop_path((thumbnail.getBackdrop_path()));
        this.setPoster_path(thumbnail.getPoster_path());
    }
}
