package edu.uci.ics.jindongy.service.movies.model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.movies.base.ResponseModel;
import edu.uci.ics.jindongy.service.movies.base.Result;
import edu.uci.ics.jindongy.service.movies.model.data.ThumbnailModel;

public class ThumbnailResponse extends ResponseModel {
    @JsonProperty
    private ThumbnailModel[] thumbnails;

    public ThumbnailResponse() {
    }

    public ThumbnailResponse(ThumbnailModel[] thumbnails){
        if(thumbnails==null){
            this.setResult(Result.INTERNAL_SERVER_ERROR);
        }else if(thumbnails.length==0){
            this.setResult(Result.NO_MOVIE_FOUND);
        }else{
            this.setResult(Result.FOUND_MOVIE);
        }
        this.thumbnails=thumbnails;
    }


}
