package testcases.model.hw3.response;
/**
 * @author Benjamin Chhen
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.uci.ics.cs122b.test.base.ResponseModel;
import edu.uci.ics.cs122b.test.common.Result;
import testcases.model.hw3.submodels.Person;

@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
@JsonPropertyOrder({"resultCode","message","people"} )
public class PeopleInfoSearchResponseModel extends ResponseModel {

    private Person[] people;

    public PeopleInfoSearchResponseModel() {
    }

    public PeopleInfoSearchResponseModel(Result result, Person[] people) {
        super(result);
        this.people = people;
    }

    public Person[] getPeople() {
        return people;
    }

}
