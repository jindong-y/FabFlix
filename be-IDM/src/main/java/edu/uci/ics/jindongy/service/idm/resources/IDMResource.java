package edu.uci.ics.jindongy.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.jindongy.service.idm.IDMService;
import edu.uci.ics.jindongy.service.idm.configs.ServiceConfigs;
import edu.uci.ics.jindongy.service.idm.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.idm.model.*;
import edu.uci.ics.jindongy.service.idm.security.Crypto;
import edu.uci.ics.jindongy.service.idm.security.Session;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.checkerframework.checker.units.qual.A;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("")
public class IDMResource {
    public final int ACTIVE_STATUS = 1;
    public final int CLOSED_STATUS = 2;
    public final int EXPIRED_STATUS = 3;
    public final int REVOKED_STATUS = 4;

    public final int PLEVEL_ROOT = 1;
    public final int PLEVEL_ADMIN = 2;
    public final int PLEVEL_EMPLOYEE = 3;
    public final int PLEVEL_SERVICE = 4;
    public final int PLEVEL_USER = 5;


    //TODO hello world
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        return Response.status(Response.Status.OK).entity("hello").build();
    }


    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        try {
            PreparedStatement ps = IDMService.getCon().prepareStatement("Update session\n" +
                    "SET status= 1,\n" +
                    "    time_created= CURRENT_TIMESTAMP,\n" +
                    "    last_used= CURRENT_TIMESTAMP,\n" +
                    "    expr_time= ADDTIME(CURRENT_TIMESTAMP, SEC_TO_TIME(180))\n" +
                    "WHERE session_id =\n" +
                    "      'b90d7b068c63fd11c0b63e97f3ecd434e8d08149c8d20f52c0693c91ba9168952da08184bc945bb8298f176eff979047d563b329e363dd7422c7f628b97e0872'\n");

            ps.executeUpdate();
        }catch (SQLException e){
            ServiceLogger.LOGGER.warning("testest!");
            return Response.status(Response.Status.OK).entity("nonon").build();
        }
        ServiceLogger.LOGGER.info("testest!");
        return Response.status(Response.Status.OK).entity("yes").build();
    }



    @Path("register")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response register(RegistRequest args) {


        String email = args.email;
        char[] password = args.password;
        if (email==null||email.length() > 50||email.length()==0) {
            //Case -10: Email address has invalid length.
            ServiceLogger.LOGGER.warning("Email address has invalid length");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-10, "Email address has invalid length"))
                    .build();
        }
        if (!email.matches("\\w+@\\w+\\.\\w+")) {
            //Case -11: Email address has invalid format.
            ServiceLogger.LOGGER.warning("Email address has invalid format.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-11, "Email address has invalid format."))
                    .build();
        }

        if (password==null||password.length > 16||password.length==0) {
            ServiceLogger.LOGGER.warning("Password has invalid length. should <=16");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-12, "Password has invalid length. <=16"))
                    .build();
        }
        if (password.length < 7) {
            ServiceLogger.LOGGER.warning("Password does not meet length requirements. should >=7");
            return Response.status(Response.Status.OK)
                    .entity(new IDMResponse(12, "Password does not meet length requirements. >=7"))
                    .build();
        }

        boolean psw = true;
        String stringPsw = new String(password);
        if (!(stringPsw.matches("\\w+"))) {
            ServiceLogger.LOGGER.warning("Password has invalid characters.");
            psw = false;
        } else if (!(stringPsw.matches("(.*[A-Z].*)"))) {
            ServiceLogger.LOGGER.warning("Password has no Uppercase.");
            psw = false;
        } else if (!(stringPsw.matches("(.*\\d.*)"))) {
            ServiceLogger.LOGGER.warning("Password has no number.");
            psw = false;
        } else if (!(stringPsw.matches("(.*[a-z].*)"))) {
            ServiceLogger.LOGGER.warning("Password has no lowercase.");
            psw = false;
        }
        if (!psw) {
            ServiceLogger.LOGGER.warning("Password does not meet character requirements. " + stringPsw);
            return Response.status(Response.Status.OK)
                    .entity(new IDMResponse(13, "Password does not meet character requirements."))
                    .build();
        }


        try {
            String checkEmailQuery = "SELECT email FROM user WHERE email= ?;";
            PreparedStatement ps = IDMService.getCon().prepareStatement(checkEmailQuery);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ServiceLogger.LOGGER.warning("Email already in use.");
                return Response.status(Response.Status.OK)
                        .entity(new IDMResponse(16, "Email already in use."))
                        .build();
            }
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("SQL query exception.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//            .entity(new IDMResponse(-1, " Internal Server Error."))
        }
        try {
            ServiceLogger.LOGGER.info("Received request to hash and salt and password");
            String registerQuery =
                    "INSERT INTO user " +
                            "(email,status,plevel,salt,pword)" +
                            "VALUE(?,?,?,?,?)";
            byte[] salt = Crypto.genSalt();
            byte[] hashedPword = Crypto.hashPassword(password, salt, Crypto.ITERATIONS, Crypto.KEY_LENGTH);
            String encodedSalt = Hex.encodeHexString(salt);
            String encodedPword = Hex.encodeHexString(hashedPword);
            PreparedStatement ps = IDMService.getCon().prepareStatement(registerQuery);
            ps.setString(1, email);
            ps.setInt(2, EXPIRED_STATUS);
            ps.setInt(3, PLEVEL_USER);
            ps.setString(4, encodedSalt);
            ps.setString(5, encodedPword);
            ServiceLogger.LOGGER.info("Trying insertion:new usr " + email);
            ps.executeUpdate();
            ServiceLogger.LOGGER.info("Trying insertion:new usr " + email);
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("Insert failed");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
        ServiceLogger.LOGGER.info("User registered successfully. " + email);
        return Response.status(Response.Status.OK)
                .entity(new IDMResponse(110, "User registered successfully."))
                .build();


    }

    private boolean isValidEmail(String email) {
        return email.matches("\\w+@\\w+\\.\\w+");
    }


    @Path("login")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response login(LoginRequest args) {


        String email = args.email;
        char[] password = args.password;

        if (password==null||password.length < 7 || password.length > 16) {
            ServiceLogger.LOGGER.warning("Password has invalid length.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-12, "Password has invalid length."))
                    .build();
        }
        if (email==null||email.length()==0||email.length() > 50) {
            //Case -10: Email address has invalid length.
            ServiceLogger.LOGGER.warning("Email address has invalid length");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-10, "Email address has invalid length"))
                    .build();
        }
        if (!isValidEmail(email)) {
            ServiceLogger.LOGGER.warning("Email address has invalid format.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-11, "Email address has invalid format."))
                    .build();
        }

        try {
            ServiceLogger.LOGGER.info("Received request to login");
            ServiceLogger.LOGGER.info("Received request to hash and salt and password");
            String LoginQuery =
                    "SELECT email, salt, pword FROM user " +
                            "WHERE email= ?";
            PreparedStatement ps = IDMService.getCon().prepareStatement(LoginQuery);
            ps.setString(1, email);
            ServiceLogger.LOGGER.info("Trying SELECT:" + email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ServiceLogger.LOGGER.warning("User not found.");
                return Response.status(Response.Status.OK)
                        .entity(new IDMResponse(14, "User not found."))
                        .build();
            }
            String encodedSalt = rs.getString("salt");
            String encodedPword = rs.getString("pword");


            byte[] salt = Hex.decodeHex(encodedSalt);
            byte[] hashedPword = Crypto.hashPassword(password, salt, Crypto.ITERATIONS, Crypto.KEY_LENGTH);
            ServiceLogger.LOGGER.info("Authenticating");
            String encodedLoginPassword = Hex.encodeHexString(hashedPword);
            if (!encodedLoginPassword.equals(encodedPword)) {
                ServiceLogger.LOGGER.info("Passwords do not match. ");
                return Response.status(Response.Status.OK)
                        .entity(new IDMResponse(11, "Passwords do not match."))
                        .build();
            }

        } catch (SQLException | DecoderException e) {
            ServiceLogger.LOGGER.warning("SQL query failed or Decode error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        try {
            ServiceLogger.LOGGER.info("Authentication Pass ");
            ServiceLogger.LOGGER.info("Check if existing an active session");

            String Q = "SELECT email,status,session_id FROM session Where email =?";
            PreparedStatement ps = IDMService.getCon().prepareStatement(Q);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getInt("status") == ACTIVE_STATUS) {
                    ServiceLogger.LOGGER.info("Update session");
                    String session_id=rs.getString("session_id");


                     ps = IDMService.getCon().prepareStatement("Update session\n" +
                            "SET status= ?,\n" +
                            "    time_created= CURRENT_TIMESTAMP,\n" +
                            "    last_used= CURRENT_TIMESTAMP,\n" +
                            "    expr_time= ADDTIME(CURRENT_TIMESTAMP, SEC_TO_TIME(?))\n" +
                            "WHERE session_id =\n" +
                            "      ?");
                    ps.setInt(1, ACTIVE_STATUS);
                    ps.setLong(2,Session.TOKEN_EXPR / 1000);
                    ps.setString(3,session_id);
                    try {
                        ps.executeUpdate();
                    }catch (SQLException e){
                        ServiceLogger.LOGGER.warning("Update failed");
                        throw e;
                    }
                    ServiceLogger.LOGGER.info("Update session succeeded.");
                    ServiceLogger.LOGGER.info("User logged in successfully. " + email);
                    return Response.status(Response.Status.OK)
                            .entity(new IDMResponse(120, "User logged in successfully.",session_id))
                            .build();
                }


            }
            ServiceLogger.LOGGER.info("Create a new session. ");
            String newSessionQ = "INSERT INTO session (session_id, email, status,expr_time)" +
                    "VALUES (?, ?, ?, ?);";
            Session newSession=  Session.createSession(email);

            PreparedStatement pst = IDMService.getCon().prepareStatement(newSessionQ);
            String newSessionID=newSession.getSessionID().toString();
                pst.setString(1, newSessionID);
                pst.setString(2, email);
                pst.setInt(3,ACTIVE_STATUS);
                pst.setTimestamp(4,newSession.getExprTime());
            try {
                pst.executeUpdate();
            }catch (SQLException e){
                ServiceLogger.LOGGER.warning("creat new session failed");
                throw e;
            }
            ServiceLogger.LOGGER.info("User logged in successfully. " + email);
            return Response.status(Response.Status.OK)
                    .entity(new IDMResponse(120, "User logged in successfully.",newSessionID))
                    .build();
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("SQL query failed");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }




    }


    @Path("session")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response session(SessionRequest args) {



        String email = args.email;
        String session_id =args.session_id;



        if(session_id==null||session_id.length()!=128){
            ServiceLogger.LOGGER.warning("Token has invalid length.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-13,"Token has invalid length."))
                    .build();
        }
        if (email==null||email.length()==0||email.length() > 50) {
            //Case -10: Email address has invalid length.
            ServiceLogger.LOGGER.warning("Email address has invalid length");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-10, "Email address has invalid length"))
                    .build();
        }
        if (!isValidEmail(email)) {
            ServiceLogger.LOGGER.warning("Email address has invalid format.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-11, "Email address has invalid format."))
                    .build();
        }



        try {
            ServiceLogger.LOGGER.info("Check user");
            String Q = "SELECT * FROM user Where email =?";
            PreparedStatement ps = IDMService.getCon().prepareStatement(Q);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                ServiceLogger.LOGGER.warning("User not found.");
                return Response.status(Response.Status.OK)
                        .entity(new IDMResponse(14, " User not found."))
                        .build();
            }

            ServiceLogger.LOGGER.info("Check session status");
            String SQ = "SELECT * FROM session Where email =?";
            ps = IDMService.getCon().prepareStatement(SQ);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if(!rs.next()||!rs.getString("session_id").equals(session_id)){
                ServiceLogger.LOGGER.warning("Session not found.");
                return Response.status(Response.Status.OK)
                        .entity(new IDMResponse(134, " Session not found."))
                        .build();
            }
            //String session_id = rs.getString("session_id");
//            int status = rs.getInt("status");
//            Timestamp last_used= rs.getTimestamp("last_used");
//            Timestamp time_created= rs.getTimestamp("time_created");
//            Timestamp expr_time= rs.getTimestamp("expr_time");
//
//            if(last_used.before(expr_time)){
//                String updateQ="UPDATE session" +
//                        "SET last_used= ?" +
//                        "WHERE session_id=?";
//
//
//            }






            switch(rs.getInt("status")){
                case ACTIVE_STATUS:
                    ServiceLogger.LOGGER.info("Session is active.");
                    return Response.status(Response.Status.OK)
                            .entity(new IDMResponse(130,"Session is active.",session_id))
                            .build();
                case CLOSED_STATUS:
                    ServiceLogger.LOGGER.info("Session is closed.");
                    return Response.status(Response.Status.OK)
                            .entity(new IDMResponse(132,"Session is closed."))
                            .build();
                case EXPIRED_STATUS:
                    ServiceLogger.LOGGER.info("Session is expired.");
                    return Response.status(Response.Status.OK)
                            .entity(new IDMResponse(131,"Session is expired."))
                            .build();
                case REVOKED_STATUS:
                    ServiceLogger.LOGGER.info("Session is revoked.");
                    return Response.status(Response.Status.OK)
                            .entity(new IDMResponse(133,"Session is revoked."))
                            .build();

            }
            ServiceLogger.LOGGER.warning("invalid status");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();



        }catch (SQLException e) {
            ServiceLogger.LOGGER.warning("SQL query failed");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }


    @Path("privilege")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response privilege(PrivilegeRequest args){



        int plevel= args.plevel;
        String email  = args.email;


        if(plevel>5||plevel<1){
            ServiceLogger.LOGGER.warning("Privilege level out of valid range.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-14,"Privilege level out of valid range."))
                    .build();
        }
        //TODO make this a method.

        if (email==null||email.length()==0||email.length() > 50) {
            //Case -10: Email address has invalid length.
            ServiceLogger.LOGGER.warning("Email address has invalid length");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-10, "Email address has invalid length"))
                    .build();
        }
        if (!isValidEmail(email)) {
            ServiceLogger.LOGGER.warning("Email address has invalid format.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new IDMResponse(-11, "Email address has invalid format."))
                    .build();
        }

        try {
            ServiceLogger.LOGGER.info("Check user");
            String Q = "SELECT * FROM user Where email =?";
            PreparedStatement ps = IDMService.getCon().prepareStatement(Q);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ServiceLogger.LOGGER.warning("User not found.");
                return Response.status(Response.Status.OK)
                        .entity(new IDMResponse(14, " User not found."))
                        .build();
            }
            if(plevel>=rs.getInt("plevel")){
                ServiceLogger.LOGGER.info("User has sufficient privilege level.");
                return Response.status(Response.Status.OK)
                        .entity(new IDMResponse(140, "User has sufficient privilege level."))
                        .build();
            }else{
                ServiceLogger.LOGGER.info("User has insufficient privilege level.");
                return Response.status(Response.Status.OK)
                        .entity(new IDMResponse(141, "User has insufficient privilege level."))
                        .build();
            }


        }catch (SQLException e) {
            ServiceLogger.LOGGER.warning("SQL query error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }



    }



}
