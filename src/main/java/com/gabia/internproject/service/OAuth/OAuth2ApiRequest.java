package com.gabia.internproject.service.OAuth;

import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OAuth2ApiRequest {
    private String url;
    private HttpMethod method;
    private Map<String,String> headers;


    OAuth2ApiRequest(String url,HttpMethod method){
        this.url=url;
        this.method=method;
        headers=new HashMap<>();

    }

    public void addHeader(OAuthConstants key, OAuthConstants value) {
        headers.put(key.getText(),value.getText());
    }






}
