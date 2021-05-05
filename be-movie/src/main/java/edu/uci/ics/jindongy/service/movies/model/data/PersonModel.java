package edu.uci.ics.jindongy.service.movies.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jvnet.hk2.annotations.Optional;

import java.sql.ResultSet;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonModel {
    @JsonProperty
    private int person_id;
    @JsonProperty
    private String name;
    @JsonProperty
    @Optional
    private String gender;

    @JsonProperty
    @Optional
    private String birthday;

    @JsonProperty
    @Optional
    private String deathday;

    @JsonProperty
    @Optional
    private String biography;

    @JsonProperty
    @Optional
    private String birthplace;
    @JsonProperty
    @Optional
    private Float popularity;
    @JsonProperty
    @Optional
    private String profile_path;

    public PersonModel(int person_id, String name) {
        this.person_id = person_id;
        this.name = name;
    }

    public PersonModel(int person_id, String name, String birthday, Float popularity, String profile_path) {
        this.person_id = person_id;
        this.name = name;
        this.birthday = birthday;
        this.popularity = popularity;
        this.profile_path = profile_path;
    }

    public PersonModel(int person_id, String name, String gender, String birthday, String deathday, String biography, String birthplace, Float popularity, String profile_path) {
        this.person_id = person_id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.deathday = deathday;
        this.biography = biography;
        this.birthplace = birthplace;
        this.popularity = popularity;
        this.profile_path = profile_path;
    }
}
