package edu.uci.ics.jindongy.service.movies.base;

import javax.ws.rs.core.Response.Status;

public enum Result {
    INTERNAL_SERVER_ERROR(Status.INTERNAL_SERVER_ERROR,-1,"Internal server error."),
    FOUND_MOVIE(Status.OK,210,"Found movie(s) with search parameters."),
    FOUND_PEOPLE(Status.OK,212,"Found people with search parameters."),
    NO_MOVIE_FOUND(Status.OK,211,"No movies found with search parameters."),
    NO_PEOPLE_FOUND(Status.OK,213,"No people found with search parameters.");

    private final Status status;
    private final int resultCode;
    private final String message;

    Result(Status status, int resultCode, String message) {
        this.status = status;
        this.resultCode = resultCode;
        this.message = message;
    }
    public Status getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }
}
