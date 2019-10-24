package com.gabia.internproject.service.OAuth.ScribeJava.Custom;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.ServiceBuilderOAuth20;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.utils.Preconditions;

import java.io.OutputStream;

public class HiworksServiceBuilder implements ServiceBuilderOAuth20 {
    private String accessType;
    private String callback;
    private String apiKey;
    private String apiSecret;
    private String scope;
    private OutputStream debugStream;
    private String responseType = "code";
    private String userAgent;

    private HttpClientConfig httpClientConfig;
    private HttpClient httpClient;

    public HiworksServiceBuilder(String apiKey) {
        apiKey(apiKey);
    }

    public HiworksServiceBuilder accessType(String accessType) {
        this.accessType = accessType;
        return this;
    }

    @Override
    public HiworksServiceBuilder callback(String callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public final HiworksServiceBuilder apiKey(String apiKey) {
        Preconditions.checkEmptyString(apiKey, "Invalid Api key");
        this.apiKey = apiKey;
        return this;
    }

    @Override
    public HiworksServiceBuilder apiSecret(String apiSecret) {
        Preconditions.checkEmptyString(apiSecret, "Invalid Api secret");
        this.apiSecret = apiSecret;
        return this;
    }


    @Override
    public ServiceBuilderOAuth20 defaultScope(String defaultScope) {
        return setScope(defaultScope);
    }

    private HiworksServiceBuilder setScope(String scope) {
        Preconditions.checkEmptyString(scope, "Invalid OAuth scope");
        this.scope = scope;
        return this;
    }

    @Override
    public HiworksServiceBuilder debugStream(OutputStream debugStream) {
        Preconditions.checkNotNull(debugStream, "debug stream can't be null");
        this.debugStream = debugStream;
        return this;
    }

    @Override
    public ServiceBuilderOAuth20 responseType(String responseType) {
        Preconditions.checkEmptyString(responseType, "Invalid OAuth responseType");
        this.responseType = responseType;
        return this;
    }

    @Override
    public HiworksServiceBuilder httpClientConfig(HttpClientConfig httpClientConfig) {
        Preconditions.checkNotNull(httpClientConfig, "httpClientConfig can't be null");
        this.httpClientConfig = httpClientConfig;
        return this;
    }

    @Override
    public HiworksServiceBuilder httpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    @Override
    public HiworksServiceBuilder userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public HiworksServiceBuilder debug() {
        return debugStream(System.out);
    }



    @Override
    public OAuth20Service build(DefaultApi20 api) {

        return ((HiworksApi20)api).createService(apiKey, apiSecret, callback, scope, accessType,responseType, debugStream, userAgent,
                httpClientConfig, httpClient);
    }
}
