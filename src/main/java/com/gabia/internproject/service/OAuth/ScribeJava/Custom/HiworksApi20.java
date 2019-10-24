package com.gabia.internproject.service.OAuth.ScribeJava.Custom;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.ParameterList;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth2.clientauthentication.ClientAuthentication;
import com.github.scribejava.core.oauth2.clientauthentication.RequestBodyAuthenticationScheme;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HiworksApi20 extends DefaultApi20 {

    protected HiworksApi20() {
    }

    private static class InstanceHolder {
        private static final HiworksApi20 INSTANCE = new HiworksApi20();

    }

    public static HiworksApi20 instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {

        return "https://api.hiworks.com/open/auth/accesstoken";
    }

    @Override
    protected String getAuthorizationBaseUrl() {

        return "https://api.hiworks.com/open/auth/authform";
    }

    public ClientAuthentication getClientAuthentication() {
        return RequestBodyAuthenticationScheme.instance();
    }

//
//    @Override
//    public Verb getAccessTokenVerb() {
//        return Verb.GET;
//    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return HiworksJsonTokenExtractor.instance();
    }

    public OAuth20Service createService(String apiKey, String apiSecret, String callback, String defaultScope, String accessType,
                                        String responseType, OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig,
                                        HttpClient httpClient) {
        return new HiworksOAuth20Service(this, apiKey, apiSecret, callback, defaultScope,accessType, responseType, debugStream, userAgent,
                httpClientConfig, httpClient);
    }



    public String getAuthorizationUrl(String responseType, String apiKey, String callback,String accessType ,String scope, String state,
                                      Map<String, String> additionalParams) {
        final ParameterList parameters = new ParameterList(additionalParams);
        parameters.add(OAuthConstants.RESPONSE_TYPE, responseType);
        parameters.add(OAuthConstants.CLIENT_ID, apiKey);
        parameters.add("access_type",accessType);

        if (callback != null) {
            parameters.add(OAuthConstants.REDIRECT_URI, callback);
        }

        if (scope != null) {
            parameters.add(OAuthConstants.SCOPE, scope);
        }

        if (state != null) {
            parameters.add(OAuthConstants.STATE, state);
        }

        return parameters.appendTo(getAuthorizationBaseUrl());
    }



}

