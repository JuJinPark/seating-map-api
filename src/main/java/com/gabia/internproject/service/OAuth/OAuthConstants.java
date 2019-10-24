package com.gabia.internproject.service.OAuth;

public enum OAuthConstants {

     //HTTP headers

    CONTENT_TYPE("Content-Type"),
    ACCEPT("Accept"),

    //Header Type

    APPLICATION_JSON("application/json"),


    //cookie name
    SEAT_API_JWT("SEAT_API_JWT"),


    //redirection params
    STATUS("STATUS"),
    MESSAGE("MSG"),
    JOBNAME("JOBNAME");

    private String text;

    OAuthConstants(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }

}
