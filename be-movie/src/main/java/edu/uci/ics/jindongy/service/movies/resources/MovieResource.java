package edu.uci.ics.jindongy.service.movies.resources;

import edu.uci.ics.jindongy.service.movies.MoviesService;
import edu.uci.ics.jindongy.service.movies.configs.IdmConfigs;
import edu.uci.ics.jindongy.service.movies.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.movies.model.Response.*;
import edu.uci.ics.jindongy.service.movies.base.MovieModel;
import edu.uci.ics.jindongy.service.movies.model.Resquest.IDMRequest;
import edu.uci.ics.jindongy.service.movies.model.Resquest.ThumbnailRequest;
import edu.uci.ics.jindongy.service.movies.model.data.GetMovieModel;
import edu.uci.ics.jindongy.service.movies.model.data.PersonModel;
import edu.uci.ics.jindongy.service.movies.model.data.ThumbnailModel;
import org.checkerframework.checker.units.qual.A;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("")
public class MovieResource {


    private Integer limit;
    private Integer offset;
    private String orderby;
    private String direction;


    private static HttpHeaders headers;

    public static HttpHeaders getHeaders() {
        return headers;
    }

    private boolean checkPrivilege(HttpHeaders headers, boolean hidden) throws BadRequestException {
        String email = headers.getHeaderString("email");

        Response response = sendIDMRequest(new IDMRequest(email, 4), MoviesService.getIdmConfigs().getPrivilegePath());

        ServiceLogger.LOGGER.info("Reading response");
        IDMResponse payload = null;

        try {
            payload = response.readEntity(IDMResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            ServiceLogger.LOGGER.severe("reading response error");

        }

        int rc = payload.getResultCode();
        ServiceLogger.LOGGER.info("Received resultCode: " + payload.getResultCode());
        ServiceLogger.LOGGER.info("Received Message: " + payload.getMessage());
        if (rc == 140) {
            return hidden;
        }
        if (rc == 141) {
            return false;
        }
        throw new BadRequestException("Privilege request error\n");

    }

    private Response sendIDMRequest(IDMRequest idmRequest, String endpoint) {
        try {
            ServiceLogger.LOGGER.info("Send request to IDM Privilege");


            IdmConfigs idmConfig = MoviesService.getIdmConfigs();


            URI uri;

            uri = UriBuilder.fromUri(idmConfig.getScheme() + idmConfig.getHostName() + idmConfig.getPath()).port(idmConfig.getPort()).build();
            ServiceLogger.LOGGER.info("Build uri: " + uri.toString());


            // Create a new Client
            ServiceLogger.LOGGER.info("Building client...");
            Client client = ClientBuilder.newClient();
            client.register(JacksonFeature.class);

            ServiceLogger.LOGGER.info("Building WebTarget...");
            WebTarget webTarget = client.target(uri.toString()).path(endpoint);

            // Create an InvocationBuilder to create the HTTP request
            ServiceLogger.LOGGER.info("Starting invocation builder...");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

            // Send the request and save it to a Response
            ServiceLogger.LOGGER.info("Sending request...");
            ServiceLogger.LOGGER.info(Entity.entity(idmRequest, MediaType.APPLICATION_JSON).toString());
            Response response = invocationBuilder.post(Entity.entity(idmRequest, MediaType.APPLICATION_JSON));
            ServiceLogger.LOGGER.info("Request sent.");

            ServiceLogger.LOGGER.info("Received status " + response.getStatus());
            return response;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Response resposeAfterQuery(PreparedStatement ps, Boolean hidden, HttpHeaders headers) throws SQLException {
        ServiceLogger.LOGGER.info("SQL Query: " + ps.toString());

        ResultSet rs = ps.executeQuery();
        ServiceLogger.LOGGER.info("Receive result set");

        ArrayList<MovieModel> movies = new ArrayList<>();

        while (rs.next()) {

            movies.add(new MovieModel(rs, hidden));
        }
        MovieModel[] movieArray = movies.toArray(new MovieModel[0]);

        return new MovieResponse(movieArray).buildResponse(headers);
    }

    private void checkAndAssignParam(Integer limit, Integer offset, String orderby, String direction, boolean isMovie) {
        this.limit = limit;
        this.offset = offset;
        this.orderby = orderby;
        this.direction = direction;
        if (limit != 10 && limit != 25 && limit != 50 && limit != 100) {
            this.limit = 10;
            ServiceLogger.LOGGER.warning("limit is invalid and replaced with default");
        }

        if (offset < 0 || offset % limit != 0) {
            this.offset = 0;
            ServiceLogger.LOGGER.warning("offset is invalid and replace with default ");
        }

        if (isMovie) {
            if (!orderby.equals("title") && !orderby.equals("rating") && !orderby.equals("year")) {
                this.orderby = "title";
                ServiceLogger.LOGGER.warning("orderby is invalid and replace with default ");
            }
        } else {
            if (!orderby.equals("name") && !orderby.equals("birthday") && !orderby.equals("popularity")) {
                this.orderby = "name";
                ServiceLogger.LOGGER.warning("orderby is invalid and replace with default ");
            }
        }

        if (!direction.equals("asc") && !direction.equals("desc")) {
            this.direction = "asc";
            ServiceLogger.LOGGER.warning("direction is invalid and replace with default ");
        }
    }



    private String sqlQueryTail(boolean isMovie) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format("ORDER BY %s %s, ", orderby, direction));
        if(isMovie) {
            if (orderby.equals("title")) {
                queryBuilder.append(String.format("%s %s ", "rating", "desc"));

            } else if (orderby.equals("rating")) {
                queryBuilder.append(String.format("%s %s ", "title", "asc"));
            } else {
                queryBuilder.append(String.format("%s %s ", "year", "desc"));
            }
        }else {
            if (orderby.equals("popularity")) {
                queryBuilder.append(String.format("%s %s ", "name", "asc"));
            } else {
                queryBuilder.append(String.format("%s %s ", "popularity", "desc"));
            }
        }
        queryBuilder.append(String.format("LIMIT %d ", limit));
        queryBuilder.append(String.format("OFFSET %d ", offset));
        return queryBuilder.toString();
    }

    @GET
    @Path("hello")
    public String hello() {
        return "hello";
    }


    @GET
    @Path("search")
    @Produces(APPLICATION_JSON)
    public Response search(@Context HttpHeaders headers,
                           @QueryParam("title") String title,
                           @QueryParam("year") Integer year,
                           @QueryParam("director") String director,
                           @QueryParam("genre") String genre,
                           @DefaultValue("true") @QueryParam("hidden") Boolean hidden,
                           @DefaultValue("10") @QueryParam("limit") Integer Limit,
                           @DefaultValue("0") @QueryParam("offset") Integer Offset,
                           @DefaultValue("title") @QueryParam("orderby") String Orderby,
                           @DefaultValue("asc") @QueryParam("direction") String Direction) throws SQLException {

        this.headers = headers;

        checkAndAssignParam(Limit, Offset, Orderby, Direction, true);

        try {
            hidden = checkPrivilege(headers, hidden);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new MovieResponse(null).buildResponse(headers);
        }

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT DISTINCT movie.movie_id,title,year,p.name as director,rating,backdrop_path,poster_path,hidden " +
                "FROM movie " +
                "LEFT JOIN person p ON p.person_id = movie.director_id " +
                "LEFT JOIN genre_in_movie gim ON movie.movie_id = gim.movie_id " +
                "LEFT JOIN genre g ON g.genre_id = gim.genre_id " +
                "Where 1=1 ");
        if (title != null) {
            queryBuilder.append("AND movie.title LIKE ? ");
        }
        if (year != null) {
            queryBuilder.append("AND movie.year = ? ");
        }
        if (director != null) {
            queryBuilder.append("AND p.name LIKE ? ");
        }
        if (genre != null) {
            queryBuilder.append("AND g.name LIKE ? ");
        }
        if (!hidden) {
            queryBuilder.append("AND movie.hidden = ? ");
        }

        queryBuilder.append(sqlQueryTail(true));

        ServiceLogger.LOGGER.info("Sanitize input ");

        int pos = 1;
        PreparedStatement ps = MoviesService.getCon().prepareStatement(queryBuilder.toString());
        if (title != null) {
            ps.setString(pos++, "%" + title + "%");
        }
        if (year != null) {
            ps.setInt(pos++, year);
        }
        if (director != null) {
            ps.setString(pos++, "%" + director+ "%");
        }
        if (genre != null) {
            ps.setString(pos++, "%" + genre+ "%");
        }
        if (!hidden) {
            ps.setBoolean(pos, false);
        }

        return resposeAfterQuery(ps, hidden, headers);


    }


    @GET
    @Path("browse/{phrase:.*}")
    @Produces(APPLICATION_JSON)
    public Response browse(@Context HttpHeaders headers,
                           @PathParam("phrase") String phrase,
                           @DefaultValue("10") @QueryParam("limit") Integer Limit,
                           @DefaultValue("0") @QueryParam("offset") Integer Offset,
                           @DefaultValue("title") @QueryParam("orderby") String Orderby,
                           @DefaultValue("asc") @QueryParam("direction") String Direction) throws SQLException {

        this.headers = headers;
        checkAndAssignParam(Limit, Offset, Orderby, Direction, true);
        String[] phrases = phrase.split(",");
//        if (phrases.length == 0) {
//            ServiceLogger.LOGGER.warning("NO keyword in the phrase");
//            return ISE;
//        }
        ServiceLogger.LOGGER.info("phrases: " + phrase);


        Boolean hidden = true;
        try {
            hidden = checkPrivilege(headers, hidden);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new MovieResponse(null).buildResponse(headers);
        }

        //TODO USING GROUP BY and HAVING COUNT(ID)=num of keywords
        ServiceLogger.LOGGER.info("inital SQL query");
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT movie.movie_id,\n" +
                "       title,\n" +
                "       year,\n" +
                "       p.name as director,\n" +
                "       rating,\n" +
                "       backdrop_path,\n" +
                "       poster_path,\n" +
                "       hidden\n" +
                "FROM movie\n" +
                "         LEFT JOIN person p on movie.director_id = p.person_id\n" +
                "WHERE movie.movie_id IN (\n" +
                "    SELECT kim1.movie_id\n" +
                "    FROM keyword_in_movie kim1\n");


        for (int i = 1; i < phrases.length; i++) {
            queryBuilder.append(String.format("JOIN keyword_in_movie kim%d ON kim1.movie_id=kim%d.movie_id\n", i + 1, i + 1));
        }
        queryBuilder.append("WHERE kim1.keyword_id IN (\n" +
                "        SELECT keyword_id\n" +
                "        FROM keyword\n" +
                "        WHERE keyword.name LIKE ?\n" +
                "    )");
        int numPlaceholder = 1;
        for (int i = 1; i < phrases.length; i++) {
            queryBuilder.append(String.format("  AND kim%d.keyword_id IN (\n" +
                    "        SELECT keyword_id\n" +
                    "        FROM keyword\n" +
                    "        WHERE keyword.name LIKE ?\n" +
                    "    )\n", i + 1));
            numPlaceholder++;
        }
        queryBuilder.append(")\n");


        //hidden movies won't show if invalid privilege
        if (!hidden) {
            queryBuilder.append("AND movie.hidden = 0\n");
        }

        queryBuilder.append(sqlQueryTail(true));
        ServiceLogger.LOGGER.info("Sanitize inputs");

        PreparedStatement ps = MoviesService.getCon().prepareStatement(queryBuilder.toString());

        if (phrase.equals("")) {
            ps.setString(1, "%");
        } else {
            int pos = 1;
            while (pos <= numPlaceholder) {
                ps.setString(pos++, phrases[pos - 2]);
            }
        }

        return resposeAfterQuery(ps, hidden, headers);


    }

    @GET
    @Path("get/{movie_id:.*}")
    @Produces(APPLICATION_JSON)
    public Response getMovie(@PathParam("movie_id") String movie_id,
                             @Context HttpHeaders headers) throws SQLException {

        if(movie_id.equals("")){
            ServiceLogger.LOGGER.warning("empty movie_id");
            return new MovieResponse(new MovieModel[]{}).buildResponse(headers);
        }

        this.headers = headers;


        Boolean isPrivilege;
        try {
            isPrivilege = checkPrivilege(headers, true);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new MovieResponse(null).buildResponse(headers);
        }

        ServiceLogger.LOGGER.info("Search for : " + movie_id);
        String Q = "SELECT movie_id,title,year,p.name as director,rating,num_votes,budget,revenue,overview,backdrop_path,poster_path,hidden\n" +
                "FROM movie\n" +
                "LEFT JOIN person p on movie.director_id = p.person_id\n" +
                "WHERE movie_id= ?\n";
        if (!isPrivilege) {
            Q += "AND hidden=false";
        }
        PreparedStatement ps = MoviesService.getCon().prepareStatement(Q);
        ps.setString(1, movie_id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return new GetMovieResponse(null).buildResponse(headers);
        }

        ServiceLogger.LOGGER.info("Found movie: " + movie_id);

        GetMovieModel movieModel = new GetMovieModel(rs, isPrivilege);

        ServiceLogger.LOGGER.info("Search genre");

        Q = "SELECT g.genre_id,g.name\n" +
                "FROM genre_in_movie\n" +
                "LEFT JOIN genre g on g.genre_id = genre_in_movie.genre_id\n" +
                "WHERE movie_id= ?;";
        ps = MoviesService.getCon().prepareStatement(Q);
        ps.setString(1, movie_id);
        rs = ps.executeQuery();
        ArrayList<GetMovieModel.GenreModel> genreList = new ArrayList();
        while (rs.next()) {
            genreList.add(movieModel
                            .new GenreModel(
                            rs.getInt("genre_id"),
                            rs.getString("name")
                    )
            );
        }

        ServiceLogger.LOGGER.info("Search for people");

        Q = "SELECT p.person_id, p.name\n" +
                "FROM person_in_movie\n" +
                "         LEFT JOIN person p on p.person_id = person_in_movie.person_id\n" +
                "WHERE movie_id = ?";
        ps = MoviesService.getCon().prepareStatement(Q);
        ps.setString(1, movie_id);
        rs = ps.executeQuery();
        ArrayList<PersonModel> personList = new ArrayList();
        while (rs.next()) {
            personList.add(
                    new PersonModel(
                            rs.getInt("person_id"),
                            rs.getString("name")
                    )
            );
        }
        movieModel.setGenres(genreList.toArray(new GetMovieModel.GenreModel[]{}));
        movieModel.setPeople(personList.toArray(new PersonModel[]{}));
        return new GetMovieResponse(movieModel).buildResponse(headers);
    }

    @POST
    @Path("thumbnail")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response thumbnail(@Context HttpHeaders headers, ThumbnailRequest args) throws SQLException {
        this.headers = headers;
        String[] movie_ids = args.getMovie_ids();
        StringBuilder queryBuilder = new StringBuilder
                ("SELECT movie_id, title, backdrop_path, poster_path\n" +
                        "FROM movie\n" +
                        "WHERE movie_id in (?" );
        for (int i = 2; i <= movie_ids.length; i++) {
            queryBuilder.append(", ?");
        }
        queryBuilder.append(")\n" +
                "ORDER BY FIELD(movie_id");
        for (int i = 1; i <= movie_ids.length; i++) {
            queryBuilder.append(", ?");
        }
        queryBuilder.append(");");

        PreparedStatement ps = MoviesService.getCon().prepareStatement(queryBuilder.toString());
        for (int i = 1; i <= movie_ids.length; i++) {
            ps.setString(i, movie_ids[i - 1]);
        }
        for (int i = movie_ids.length+1; i <= movie_ids.length*2; i++) {
            ps.setString(i, movie_ids[i - 1-movie_ids.length]);
        }
        ServiceLogger.LOGGER.info("Execute SQL query");
        ServiceLogger.LOGGER.warning(ps.toString());
        ResultSet rs = ps.executeQuery();

        ArrayList<ThumbnailModel> thumbnailList = new ArrayList();
        while (rs.next()) {
            thumbnailList.add(new ThumbnailModel(
                    rs.getString("movie_id"),
                    rs.getString("title"),
                    rs.getString("backdrop_path"),
                    rs.getString("poster_path")));
        }
        ThumbnailResponse thumbnailResponse = new ThumbnailResponse(thumbnailList.toArray(new ThumbnailModel[]{}));
        return thumbnailResponse.buildResponse(headers);
    }


    //return movies that the person is in
    @GET
    @Path("people")
    @Produces(APPLICATION_JSON)
    public Response people(@Context HttpHeaders headers,
                           @QueryParam("name") String name,
                           @DefaultValue("10") @QueryParam("limit") int limit,
                           @DefaultValue("0") @QueryParam("offset") int offset,
                           @DefaultValue("title") @QueryParam("orderby") String orderby,
                           @DefaultValue("asc") @QueryParam("direction") String direction) throws SQLException {
        this.headers = headers;
        checkAndAssignParam(limit, offset, orderby, direction, true);

        Boolean isPrivilege = true;
        try {
            isPrivilege = checkPrivilege(headers, true);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new MovieResponse(null).buildResponse(headers);
        }
        String Q = "SELECT movie.movie_id,\n" +
                "       title,\n" +
                "       year,\n" +
                "       p.name as director,\n" +
                "       rating,\n" +
                "       backdrop_path,\n" +
                "       poster_path,\n" +
                "       hidden\n" +
                "FROM movie\n" +
                "LEFT JOIN person_in_movie pim on movie.movie_id = pim.movie_id\n" +
                "LEFT JOIN person p on pim.person_id = p.person_id\n" +
                "WHERE p.name like ?\n";
        Q = Q + sqlQueryTail(true);
        List<Object> values = new ArrayList();
        values.add("%"+name+"%");
        ResultSet rs = executSQL(Q, values);
        ArrayList<MovieModel> movieList = new ArrayList();
        while (rs.next()) {
            movieList.add(new MovieModel(rs, isPrivilege));
        }

        return new MovieResponse(movieList.toArray(new MovieModel[]{})).buildResponse(headers);
    }


    //TODO is this necessary?
    private ResultSet executSQL(String query, List<Object> values) throws SQLException {
        PreparedStatement ps = MoviesService.getCon().prepareStatement(query);
        int i = 1;
        for (Object value : values) {
            if (value != null) {
                ps.setObject(i++, value);
            }
        }
        return ps.executeQuery();
    }


    //return people info
    @GET
    @Path("people/search")
    @Produces(APPLICATION_JSON)
    public Response peopleSearch(@Context HttpHeaders headers,
                                 @QueryParam("name") String name,
                                 @QueryParam("birthday") String birthday,
                                 @QueryParam("movie_title") String movie_title,
                                 @DefaultValue("10") @QueryParam("limit") int limit,
                                 @DefaultValue("0") @QueryParam("offset") int offset,
                                 @DefaultValue("name") @QueryParam("orderby") String orderby,
                                 @DefaultValue("asc") @QueryParam("direction") String direction) throws SQLException {
        this.headers = headers;
        checkAndAssignParam(limit, offset, orderby, direction, false);


        String Q = "SELECT DISTINCT person.person_id, name,birthday,popularity,profile_path\n" +
                "FROM person\n" +
                "LEFT JOIN person_in_movie pim on person.person_id = pim.person_id\n" +
                "LEFT JOIN movie m on pim.movie_id = m.movie_id\n" +
                "WHERE TRUE\n";
        if (name != null) {
            Q += "AND name like ?\n";
        }
        if (birthday != null) {
            Q += "AND birthday= ?\n";
        }
        if (movie_title != null) {
            Q += "AND m.title like ?\n";
        }
        Q+=sqlQueryTail(false);

        PreparedStatement ps=MoviesService.getCon().prepareStatement(Q);
        int i=1;
        if (name != null) {
            ps.setString(i++,"%"+name+"%");
        }
        if (birthday != null) {
            ps.setString(i++,birthday);
        }
        if (movie_title != null) {
            ps.setString(i++,"%"+movie_title+"%");
        }

        ResultSet rs=ps.executeQuery();

        ArrayList<PersonModel> peopleList = new ArrayList();
        while (rs.next()) {
            peopleList.add(new PersonModel(
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    rs.getString("birthday"),
                    rs.getFloat("popularity"),
                    rs.getString("profile_path")
            ));
        }
        return new PeopleSearchResponse(peopleList.toArray(new PersonModel[]{})).buildResponse(headers);
    }

    @GET
    @Path("people/get/{person_id}")
    @Produces(APPLICATION_JSON)
    public Response peopleGet(@Context HttpHeaders headers,
                              @PathParam("person_id") Integer person_id) throws SQLException {
        ServiceLogger.LOGGER.info("person_id: "+person_id);
        if(person_id==null){
            ServiceLogger.LOGGER.warning("person_id is empty");
            return new PeopleGetResponse(null).buildResponse(headers);
        }
        this.headers=headers;

        String Q="SELECT person.person_id, name,g.gender_name as gender,birthday,deathday,biography,birthplace,popularity,profile_path\n" +
                "FROM person\n" +
                "LEFT JOIN gender g on person.gender_id = g.gender_id\n" +
                "WHERE person_id = ?\n";
        PreparedStatement ps= MoviesService.getCon().prepareStatement(Q);
        ps.setInt(1,person_id);
        ResultSet rs=ps.executeQuery();
        if(!rs.next()){
            return new PeopleGetResponse(null).buildResponse(headers);

        }

        return new PeopleGetResponse(new PersonModel(
                rs.getInt("person_id"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("birthday"),
                rs.getString("deathday"),
                rs.getString("biography"),
                rs.getString("birthplace"),
                rs.getFloat("popularity"),
                rs.getString("profile_path")
        )).buildResponse(headers);
    }

}
