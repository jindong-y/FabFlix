package testcases.model.hw3.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.common.Result;
import testcases.model.hw3.submodels.PeopleModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetPersonByIDResponseModel extends ResponseModel{
        @JsonProperty("person")
        PeopleModel person;

        public GetPersonByIDResponseModel() {
        }

        public GetPersonByIDResponseModel(Result result){
            super(result);
        }

        public GetPersonByIDResponseModel(Result result, PeopleModel person){
            super(result);
            this.person = person;
        }


        public PeopleModel getPerson() {
            return person;
        }

        public void setPerson(PeopleModel person) {
            this.person = person;
        }

}
