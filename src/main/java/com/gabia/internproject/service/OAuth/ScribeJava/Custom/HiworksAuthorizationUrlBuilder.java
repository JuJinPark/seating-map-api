package com.gabia.internproject.service.OAuth.ScribeJava.Custom;

import com.github.scribejava.core.oauth.AuthorizationUrlBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.pkce.PKCE;
import com.github.scribejava.core.pkce.PKCEService;

import java.util.HashMap;
import java.util.Map;

public class HiworksAuthorizationUrlBuilder {

    private final HiworksOAuth20Service oauth20Service;
    private String state;
    private Map<String, String> additionalParams;
    private PKCE pkce;
    private String scope;

    public HiworksAuthorizationUrlBuilder(HiworksOAuth20Service oauth20Service) {
        this.oauth20Service = oauth20Service;
    }

    public HiworksAuthorizationUrlBuilder state(String state) {
        this.state = state;
        return this;
    }

    public HiworksAuthorizationUrlBuilder additionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
        return this;
    }

    public HiworksAuthorizationUrlBuilder pkce(PKCE pkce) {
        this.pkce = pkce;
        return this;
    }

    public HiworksAuthorizationUrlBuilder initPKCE() {
        this.pkce = PKCEService.defaultInstance().generatePKCE();
        return this;
    }

    public HiworksAuthorizationUrlBuilder scope(String scope) {
        this.scope = scope;
        return this;
    }

    public PKCE getPkce() {
        return pkce;
    }

    public String build() {
        final Map<String, String> params;
        if (pkce == null) {
            params = additionalParams;
        } else {
            params = additionalParams == null ? new HashMap<String, String>() : new HashMap<>(additionalParams);
            params.putAll(pkce.getAuthorizationUrlParams());
        }
        return ((HiworksApi20)oauth20Service.getApi()).getAuthorizationUrl(oauth20Service.getResponseType(), oauth20Service.getApiKey(),
                oauth20Service.getCallback(),oauth20Service.getAccessType(), scope == null ? oauth20Service.getDefaultScope() : scope, state, params);
    }
}
