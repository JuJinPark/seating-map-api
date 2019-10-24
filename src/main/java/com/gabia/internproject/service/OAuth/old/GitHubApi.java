//package com.gabia.internproject.service.OAuth.old;
//
//
//
//import com.gabia.internproject.service.OAuth.OAuthConstants;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class GitHubApi extends ApiProvider {
//
//
//    private OAuthConstants[] requiredHeadersForAccessToken=new OAuthConstants[0];
//
//    private OAuthConstants[] requiredBodyParametersForAccessToken=new OAuthConstants[0];
//
//    private OAuthConstants[] requiredParametersForAccessToken ={
//            OAuthConstants.CLIENT_ID,
//            OAuthConstants.CLIENT_SECRET,
//            OAuthConstants.CODE};
//
//
//    private Map<OAuthConstants,String> defaultValue;
//
//    private GitHubApi() {
//        defaultValue= new HashMap<OAuthConstants, String>() ;
//
//    }
//
//
//    private static final GitHubApi INSTANCE = new GitHubApi();
//
//
//    public static GitHubApi instance() {
//
//        return INSTANCE;
//    }
//    @Override
//    public String getAccessTokenEndpoint() {
//        return "https://github.com/login/oauth/access_token";
//    }
//
//    @Override
//    public String getAuthorizationBaseUrl() {
//        return  "https://github.com/login/oauth/authorize";
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
//    @Override
//    public Map<OAuthConstants, String> getDefaultValue() {
//        return defaultValue;
//    }
//
//    @Override
//    public void setDefaultValue(OAuthConstants key, String value) {
//        defaultValue.put(key,value);
//
//    }
//
//
//}
