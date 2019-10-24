package com.gabia.internproject.service.OAuth;

import org.springframework.http.HttpMethod;

import java.util.HashMap;

public enum OAuthAPIProvider {
    GOOGLE{
        @Override
        public OAuth2ApiRequest getUserInfoRequest() {
            return new OAuth2ApiRequest("https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET);
        }

        @Override
        public HashMap<String, String> getUserInfoResponseKeys() {
            HashMap<String,String> responseStructure = new HashMap();
            responseStructure.put("userEmail","email");

            return responseStructure;
        }

        @Override
        public String getApiKey() {
            return "898505969152-hkcf91qo3gkj0jfjtshni5duqbe0uqc0.apps.googleusercontent.com";
        }

        @Override
        public String getApiSecret() {
            return "6RBgEZXwPTXijKHmvgqfKGDo";
        }

        @Override
        public String getScope() {
            return "email";
        }

        @Override
        public String getAccessType() {
            return "";
        }

        @Override
        public String getLoginCallBackUrl() {
            return "http://seat.gabia.com:8080/login/callback/google";
        }

        @Override
        public String getRegisterCallBackUrl() {
            return "http://seat.gabia.com:8080/register/callback/google";
        }
    },

    GITHUB{
        @Override
        public OAuth2ApiRequest getUserInfoRequest() {
            OAuth2ApiRequest request = new OAuth2ApiRequest("https://api.github.com/user", HttpMethod.GET);
            request.addHeader(OAuthConstants.CONTENT_TYPE,OAuthConstants.APPLICATION_JSON);
            request.addHeader(OAuthConstants.ACCEPT,OAuthConstants.APPLICATION_JSON);
            return request;
        }

        @Override
        public HashMap<String, String> getUserInfoResponseKeys() {
            HashMap<String,String> responseStructure = new HashMap();
            responseStructure.put("userEmail","email");

            return responseStructure;
        }

        @Override
        public String getApiKey() {
            return "3d69aa3156aa86f6eb84";
        }

        @Override
        public String getApiSecret() {
            return "9b1dc3460068922506d5c27b2d7155bd31f130c8";
        }

        @Override
        public String getScope() {
            return "email";
        }

        @Override
        public String getAccessType() {
            return null;
        }

        @Override
        public String getLoginCallBackUrl() {
            return "http://seat.gabia.com:8080/login/callback/github";
        }

        @Override
        public String getRegisterCallBackUrl() {
            return "http://seat.gabia.com:8080/register/callback/github";
        }
    },

    HIWORKS{
        @Override
        public OAuth2ApiRequest getUserInfoRequest() {
            OAuth2ApiRequest request = new OAuth2ApiRequest("https://api.hiworks.com/user/v2/me", HttpMethod.GET);
            request.addHeader(OAuthConstants.CONTENT_TYPE,OAuthConstants.APPLICATION_JSON);
            request.addHeader(OAuthConstants.ACCEPT,OAuthConstants.APPLICATION_JSON);
            return request;
        }

        @Override
        public HashMap<String, String> getUserInfoResponseKeys() {
            HashMap<String,String> responseStructure = new HashMap();
            responseStructure.put("userEmail","user_id");

            return responseStructure;
        }

        @Override
        public String getApiKey() {
            return "f3xor1auc7d45n1zby6uapdggde9lqu65d5dee1e48d234.85217709.open.apps";
        }

        @Override
        public String getApiSecret() {
            return "fpz4zdkdkdi60rp0tf9rp7ivhhvwjl4y";
        }

        @Override
        public String getScope() {
            return "";
        }

        @Override
        public String getAccessType() {
            return "online";
        }

        @Override
        public String getLoginCallBackUrl() {
            return "http://seat.gabia.com:8080/login/callback/hiworks";
        }

        @Override
        public String getRegisterCallBackUrl() {
            return null;
        }
    };


    public abstract OAuth2ApiRequest getUserInfoRequest();
    public abstract HashMap<String,String> getUserInfoResponseKeys();
    public abstract String getApiKey();
    public abstract String getApiSecret();
    public abstract String getScope();
    public abstract String getAccessType();
    public abstract String getLoginCallBackUrl();
    public abstract String getRegisterCallBackUrl();


}
