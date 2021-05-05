package testcases.model.hw3.submodels;
/**
 * @author Benjamin Chhen
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
public class Person {
    @JsonProperty(value = "person_id", required = true)
    private Integer person_id;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "gender")
    private String gender;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "birthday")
    private String birthday;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "deathday")
    private String deathday;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "birthplace")
    private String birthplace;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "biography")
    private String biography;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "popularity")
    private Float popularity;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "profile_path")
    private String profile_path;

    public Person() {
    }

    public Person(Integer person_id, String name, String birthday, Float popularity, String profile_path) {
        this.person_id = person_id;
        this.name = name;
        this.gender = null;
        this.birthday = birthday;
        this.deathday = null;
        this.birthplace = null;
        this.biography = null;
        this.popularity = popularity;
        this.profile_path = profile_path;
    }

    public Person(Integer person_id, String name, String gender, String birthday, String deathday, String birthplace, String biography, Float popularity, String profile_path) {
        this.person_id = person_id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.deathday = deathday;
        this.birthplace = birthplace;
        this.biography = biography;
        this.popularity = popularity;
        this.profile_path = profile_path;
    }


    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
