package edu.uci.ics.jindongy.service.movies.model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.movies.base.ResponseModel;
import edu.uci.ics.jindongy.service.movies.base.Result;
import edu.uci.ics.jindongy.service.movies.model.data.PersonModel;

public class PeopleSearchResponse extends ResponseModel {
    @JsonProperty
    private PersonModel[] people;

    public PeopleSearchResponse(PersonModel[] people) {
        if(people==null){
            this.setResult(Result.INTERNAL_SERVER_ERROR);
        }else if(people.length==0){
            this.setResult(Result.NO_PEOPLE_FOUND);
        }else{
            this.setResult(Result.FOUND_PEOPLE);
            this.people = people;
        }

    }
}
