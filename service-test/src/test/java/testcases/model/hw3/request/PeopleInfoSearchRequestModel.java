package testcases.model.hw3.request;
/**
 * @author Benjamin Chhen
 */

public class PeopleInfoSearchRequestModel  {

    private String name;
    private String birthday;
    private String movie_title;
    private Integer limit;
    private Integer offset;
    private String orderby;
    private String direction;

    public PeopleInfoSearchRequestModel(String name, String birthday, String movie_title, Integer limit, Integer offset, String orderby, String direction) {
        this.name = name;
        this.birthday = birthday;
        this.movie_title = movie_title;
        this.limit = limit;
        this.offset = offset;
        this.orderby = orderby;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
