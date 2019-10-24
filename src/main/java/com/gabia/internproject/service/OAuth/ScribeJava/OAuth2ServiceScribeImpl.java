package com.gabia.internproject.service.OAuth.ScribeJava;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.internproject.exception.customExceptions.AccessTokenNotAcquiredException;
import com.gabia.internproject.exception.customExceptions.ApiExecutionException;
import com.gabia.internproject.service.OAuth.OAuth2ApiRequest;
import com.gabia.internproject.service.OAuth.OAuth2Service;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.service.OAuth.ScribeJava.Custom.HiworksApi20;
import com.gabia.internproject.service.OAuth.ScribeJava.Custom.HiworksServiceBuilder;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Component
public class OAuth2ServiceScribeImpl implements OAuth2Service {

    ObjectMapper jsonMapper;

    public OAuth2ServiceScribeImpl() {

        jsonMapper = new ObjectMapper();
    }


    @Override
    public String getAuthorizationUrl(OAuthAPIProvider serviceProvider, String callback) {

        OAuth20Service oauthService = createScribeJavaOauthService(serviceProvider, callback);
        return oauthService.getAuthorizationUrl();
    }


    private OAuth20Service createScribeJavaOauthService(OAuthAPIProvider serviceProvider, String callback) {

        if (serviceProvider.equals(OAuthAPIProvider.HIWORKS)) {

            return createScribeJavaHiworksOauthService(serviceProvider, callback);
        }

        DefaultApi20 apiProvider = ScribeJavaApiFactory.getApi(serviceProvider);

        return new ServiceBuilder(serviceProvider.getApiKey())
                .apiSecret(serviceProvider.getApiSecret())
                .callback(callback)
                .defaultScope(serviceProvider.getScope())
                .build(apiProvider);


    }

    private OAuth20Service createScribeJavaHiworksOauthService(OAuthAPIProvider serviceProvider, String callback) {

        return new HiworksServiceBuilder(serviceProvider.getApiKey())
                .apiSecret(serviceProvider.getApiSecret())
                .callback(callback)
                .accessType(serviceProvider.getAccessType())
                .build(HiworksApi20.instance());


    }

    @Override
    public String getAccessToken(OAuthAPIProvider serviceProvider, String code, String callback) throws AccessTokenNotAcquiredException {

        OAuth20Service oauthService = createScribeJavaOauthService(serviceProvider, callback);
        try {
            OAuth2AccessToken token = oauthService.getAccessToken(code);
            return token.getAccessToken();
        } catch (Exception e) {
            throw new AccessTokenNotAcquiredException(e.getMessage());
        }


    }

    @Override
    public JsonNode makeApiRequest(OAuth2ApiRequest requestData, OAuthAPIProvider serviceProvider, String token) throws ApiExecutionException {

        OAuthRequest request = createOAuthRequest(requestData);

        OAuth20Service apiService = createScribeJavaOauthService(serviceProvider, null);

        apiService.signRequest(token, request);

        try {
            Response response = apiService.execute(request);
            return jsonMapper.readTree(response.getBody());
        } catch (Exception e) {
            throw new ApiExecutionException(e.getMessage());
        }



    }

    private OAuthRequest createOAuthRequest(OAuth2ApiRequest requestData) {

        Verb verb = getVerb(requestData.getMethod().toString());
        OAuthRequest requestForScribe = new OAuthRequest(verb, requestData.getUrl());
        setHeaders(requestForScribe, requestData);
        return requestForScribe;
    }

    private void setHeaders(OAuthRequest requestForScribe, OAuth2ApiRequest requestData) {

        Map<String, String> headers = requestData.getHeaders();
        Iterator it = headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            requestForScribe.addHeader((String) pair.getKey(), (String) pair.getValue());
            it.remove();
        }

    }

    private Verb getVerb(String methodName) {
        if (methodName.equals("GET")) {
            return Verb.GET;
        } else if (methodName.equals("PUT")) {
            return Verb.PUT;
        } else if (methodName.equals("POST")) {
            return Verb.POST;
        } else if (methodName.equals("PATCH")) {
            return Verb.PATCH;
        } else if (methodName.equals("DELETE")) {
            return Verb.DELETE;
        }
        return null;
    }


}
