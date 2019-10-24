package com.gabia.internproject.service.OAuth;

import com.fasterxml.jackson.databind.JsonNode;
import com.gabia.internproject.exception.customExceptions.AccessTokenNotAcquiredException;
import com.gabia.internproject.exception.customExceptions.ApiExecutionException;

public interface OAuth2Service {

    String getAuthorizationUrl(OAuthAPIProvider serviceProvider, String callback);

    String getAccessToken(OAuthAPIProvider serviceProvider, String code, String callback) throws AccessTokenNotAcquiredException;

    JsonNode makeApiRequest(OAuth2ApiRequest requestData, OAuthAPIProvider serviceProvider, String token) throws ApiExecutionException;


}
