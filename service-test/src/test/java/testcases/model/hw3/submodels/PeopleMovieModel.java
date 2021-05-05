package testcases.model.hw3.submodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PeopleMovieModel {

    @JsonProperty(value ="person_id", required = true)
    Integer person_id;

    @JsonProperty(value ="name", required = true)
    String name;

    public PeopleMovieModel() {
    }

    public PeopleMovieModel(Integer person_id, String name) {
        this.person_id = person_id;
        this.name = name;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public String getName() {
        return name;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
