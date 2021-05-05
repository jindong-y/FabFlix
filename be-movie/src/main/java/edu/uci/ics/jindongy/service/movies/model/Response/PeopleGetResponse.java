package edu.uci.ics.jindongy.service.movies.model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.movies.base.ResponseModel;
import edu.uci.ics.jindongy.service.movies.base.Result;
import edu.uci.ics.jindongy.service.movies.model.data.PersonModel;

public class PeopleGetResponse extends ResponseModel {
    @JsonProperty
    private PersonModel person;

    public PeopleGetResponse(PersonModel person) {
        if(person==null){
            this.setResult(Result.NO_PEOPLE_FOUND);
        }else {
            this.setResult(Result.FOUND_PEOPLE);
            this.person = person;
        }
    }
}
