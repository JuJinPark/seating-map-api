package com.gabia.internproject.service.OAuth.ScribeJava.Custom;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.AuthorizationUrlBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.pkce.PKCE;

import java.io.OutputStream;
import java.util.Map;


public class HiworksOAuth20Service extends OAuth20Service {

    String accessType;

    public HiworksOAuth20Service(DefaultApi20 api, String apiKey, String apiSecret, String callback, String defaultScope, String accessType, String responseType, OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig, HttpClient httpClient) {
        super(api, apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent, httpClientConfig, httpClient);
        this.accessType=accessType;
    }

    public String getAuthorizationUrl() {
        return createHiworksAuthorizationUrlBuilder().build();
    }

    public String getAuthorizationUrl(String state) {
        return createHiworksAuthorizationUrlBuilder()
                .state(state)
                .build();
    }

    /**
     * Returns the URL where you should redirect your users to authenticate your application.
     *
     * @param additionalParams any additional GET params to add to the URL
     * @return the URL where you should redirect your users
     */
    public String getAuthorizationUrl(Map<String, String> additionalParams) {
        return createHiworksAuthorizationUrlBuilder()
                .additionalParams(additionalParams)
                .build();
    }

    public String getAuthorizationUrl(PKCE pkce) {
        return createHiworksAuthorizationUrlBuilder()
                .pkce(pkce)
                .build();
    }


    public HiworksAuthorizationUrlBuilder createHiworksAuthorizationUrlBuilder() {
        return new HiworksAuthorizationUrlBuilder(this);
    }


    public String getAccessType(){
        return accessType;
    }



    @Override
    protected OAuthRequest createAccessTokenRequest(AccessTokenRequestParams params) {
//        final OAuthRequest request = new OAuthRequest(super.getApi().getAccessTokenVerb(), super.getApi().getAccessTokenEndpoint());
//        request.addBodyParameter("client_id",super.getApiKey());
//        request.addBodyParameter("client_secret",super.getApiSecret());
//        request.addBodyParameter("auth_code",params.getCode());
//        request.addBodyParameter("grant_type", OAuthConstants.AUTHORIZATION_CODE);
//        request.addBodyParameter("access_type", accessType);
//
//        final String callback = getCallback();
//        if (callback != null) {
//            request.addParameter(OAuthConstants.REDIRECT_URI, callback);
//        }
//        final String scope = params.getScope();
//        if (scope != null) {
//            request.addParameter(OAuthConstants.SCOPE, scope);
//        } else if (super.getDefaultScope() != null) {
//            request.addParameter(OAuthConstants.SCOPE, super.getDefaultScope());
//        }
//        final String pkceCodeVerifier = params.getPkceCodeVerifier();
//        if (pkceCodeVerifier != null) {
//            request.addParameter(PKCE.PKCE_CODE_VERIFIER_PARAM, pkceCodeVerifier);
//        }
//        if (isDebug()) {
//            log("created access token request with body params [%s], query string params [%s]",
//                    request.getBodyParams().asFormUrlEncodedString(),
//                    request.getQueryStringParams().asFormUrlEncodedString());
//        }
//        return request;

        final OAuthRequest request=super.createAccessTokenRequest(params);
        request.addBodyParameter("access_type", accessType);
        request.addBodyParameter("auth_code",params.getCode());
        return request;
    }


}
