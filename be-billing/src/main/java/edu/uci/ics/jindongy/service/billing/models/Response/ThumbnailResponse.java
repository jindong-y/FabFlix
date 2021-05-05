package edu.uci.ics.jindongy.service.billing.models.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.base.Results;
import edu.uci.ics.jindongy.service.billing.models.data.ThumbnailModel;

public class ThumbnailResponse extends ResponseModel {
    @JsonProperty
    private ThumbnailModel[] thumbnails;

    public ThumbnailResponse() {
    }

    public ThumbnailResponse(ThumbnailModel[] thumbnails){
        if(thumbnails==null){
            this.setResult(Results.INTERNAL_SERVER_ERROR);
        }else if(thumbnails.length==0){
            this.setResult(Results.NO_MOVIE_FOUND);
        }else{
            this.setResult(Results.FOUND_MOVIE);
        }
        this.thumbnails=thumbnails;
    }

    public ThumbnailModel[] getThumbnails() {
        return thumbnails;
    }
}