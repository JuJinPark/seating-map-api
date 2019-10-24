package com.gabia.internproject.service.OAuth.ScribeJava.Custom;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.utils.Preconditions;

import java.io.IOException;

public class HiworksJsonTokenExtractor extends OAuth2AccessTokenJsonExtractor {
    protected HiworksJsonTokenExtractor() {
    }

    private static class InstanceHolder {

        private static final HiworksJsonTokenExtractor INSTANCE = new HiworksJsonTokenExtractor();
    }

    public static HiworksJsonTokenExtractor instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public OAuth2AccessToken extract(Response response) throws IOException {
        final String body = response.getBody();
        Preconditions.checkEmptyString(body, "Response body is incorrect. Can't extract a token from an empty string");

        if (response.getCode() != 200) {
            generateError(body);
        }
        return createToken(body);
    }


    private OAuth2AccessToken createToken(String rawResponse) throws IOException {

        final JsonNode response = OBJECT_MAPPER.readTree(rawResponse).get("data");

        final JsonNode expiresInNode = response.get("expires_in");
        final JsonNode refreshToken = response.get(OAuthConstants.REFRESH_TOKEN);
        final JsonNode scope = response.get(OAuthConstants.SCOPE);
        final JsonNode tokenType = response.get("token_type");

        return createToken(extractRequiredParameter(response, OAuthConstants.ACCESS_TOKEN, rawResponse).asText(),
                tokenType == null ? null : tokenType.asText(), expiresInNode == null ? null : expiresInNode.asInt(),
                refreshToken == null ? null : refreshToken.asText(), scope == null ? null : scope.asText(), response,
                rawResponse);
    }
}
