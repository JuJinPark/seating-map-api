//package com.gabia.internproject.service.OAuth.old;
//
//import com.gabia.internproject.service.OAuth.OAuthConstants;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class GoogleApi extends ApiProvider {
//    private OAuthConstants[] requiredHeadersForAccessToken=new OAuthConstants[0];
//
//    private OAuthConstants[] requiredBodyParametersForAccessToken=new OAuthConstants[0];
//
//    private OAuthConstants[] requiredParametersForAccessToken={
//            OAuthConstants.CLIENT_ID,
//            OAuthConstants.CLIENT_SECRET,
//            OAuthConstants.CODE,
//            OAuthConstants.GRANT_TYPE,
//            OAuthConstants.REDIRECT_URI
//    };
//
//
//    private Map<OAuthConstants,String> defaultValue;
//
//
//    private GoogleApi() {
//        defaultValue= new HashMap<OAuthConstants, String>() ;
//        defaultValue.put(OAuthConstants.GRANT_TYPE,"authorization_code");
//
//    }
//
//
//    private static final GoogleApi INSTANCE = new GoogleApi();
//
//
//    public static GoogleApi instance() {
//        return INSTANCE;
//    }
//
//
//    @Override
//    public String getAccessTokenEndpoint() {
//        return "https://www.googleapis.com/oauth2/v4/token";
//    }
//
//    @Override
//    public String getAuthorizationBaseUrl() {
//        return "https://accounts.google.com/o/oauth2/auth";
//    }
//
//    @Override
//    public OAuthConstants getAuthCodeParameterName() {
//        return OAuthConstants.CODE;
//    }
//
//    @Override
//    public OAuthConstants[] getRequiredHeadersForAccessToken() {
//
//        return requiredHeadersForAccessToken;
//    }
//
//    @Override
//    public OAuthConstants[] getRequiredParametersForAccessToken() {
//
//        return requiredParametersForAccessToken;
//    }
//
//    @Override
//    public OAuthConstants[] getRequiredBodyParametersForAccessToken() {
//        return requiredBodyParametersForAccessToken;
//    }
//
//
//    @Override
//    public Map<OAuthConstants, String> getDefaultValue() {
//        return defaultValue;
//    }
//
//    @Override
//    public void setDefaultValue(OAuthConstants key, String value) {
//        defaultValue.put(key,value);
//    }
//}
