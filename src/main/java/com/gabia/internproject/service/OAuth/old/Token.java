package com.gabia.internproject.service.OAuth.old;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabia.internproject.util.StringValidationChecker;


public class Token {



    @JsonProperty("access_token")
        private String accessToken;
    @JsonProperty("token_type")
        private String tokenType;
    @JsonProperty("expires_in")
        private Integer expiresIn;
    @JsonProperty("refresh_token")
        private String refreshToken;

        private String scope;

        public Token(String accessToken) {
            this(accessToken, null);
        }

        public Token(String accessToken, String rawResponse) {
            this(accessToken, null, null, null, null, rawResponse);
        }

        public Token(String accessToken, String tokenType, Integer expiresIn, String refreshToken, String scope,
                                 String rawResponse) {

            StringValidationChecker.checkNotNull(accessToken, "access_token can't be null");
            this.accessToken = accessToken;
            this.tokenType = tokenType;
            this.expiresIn = expiresIn;
            this.refreshToken = refreshToken;
            this.scope = scope;
        }
        public Token(){};

        public String getAccessToken() {
            return accessToken;
        }

        public String setAccessToken(String accessToken) {
        return this.accessToken=accessToken;
    }

        public String getTokenType() {
            return tokenType;
        }

        public Integer getExpiresIn() {
            return expiresIn;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getScope() {
            return scope;
        }





}
