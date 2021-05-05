package testcases.model.hw3.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetPersonRequestModel {


        @JsonProperty("person_id")
        String person_id;

        public GetPersonRequestModel(String person_id) {
            this.person_id = person_id;
        }

        public String getPerson_id() {
            return person_id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }

}
