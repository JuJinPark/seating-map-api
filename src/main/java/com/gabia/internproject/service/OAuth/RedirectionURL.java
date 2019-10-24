package com.gabia.internproject.service.OAuth;

import org.springframework.http.HttpMethod;

import java.util.HashMap;

public enum RedirectionURL {
   BASE("http://seat.gabia.com:3000"),
  INFO(RedirectionURL.BASE.getURL()+"/info"),
   REGISTER_INFO(RedirectionURL.BASE.getURL()+"/registerInfo");


   private String URL;

    RedirectionURL(String URL){
        this.URL = URL;
    }
    public String getURL(){
        return URL;
    }





}
