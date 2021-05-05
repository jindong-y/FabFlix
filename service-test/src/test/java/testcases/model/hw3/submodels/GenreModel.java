package testcases.model.hw3.submodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenreModel {
    @JsonProperty(value ="genre_id", required = true)
    Integer genre_id;

    @JsonProperty(value ="name", required = true)
    String name;

    public GenreModel() {
    }

    public GenreModel(Integer genre_id,
                      String name) {
        this.genre_id = genre_id;
        this.name = name;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public String getName() {
        return name;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
