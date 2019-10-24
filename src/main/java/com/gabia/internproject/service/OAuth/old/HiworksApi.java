//package com.gabia.internproject.service.OAuth.old;
//
//import com.gabia.internproject.service.OAuth.OAuthConstants;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class HiworksApi extends ApiProvider {
//    private OAuthConstants[] requiredHeadersForAccessToken={OAuthConstants.CONTENT_TYPE};
//
//    private OAuthConstants[] requiredBodyParametersForAccessToken ={
//            OAuthConstants.CLIENT_ID,
//            OAuthConstants.CLIENT_SECRET,
//            OAuthConstants.AUTH_CODE,
//            OAuthConstants.GRANT_TYPE,
//            OAuthConstants.ACCESS_TYPE
//
//    };
//
//    private OAuthConstants[] requiredParametersForAccessToken=new OAuthConstants[0];
//
//
//    private Map<OAuthConstants,String> defaultValue;
//
//    private HiworksApi() {
//
//        defaultValue= new HashMap<OAuthConstants, String>() ;
//        defaultValue.put(OAuthConstants.GRANT_TYPE,"authorization_code");
//        defaultValue.put(OAuthConstants.ACCESS_TYPE,"offline");
//        defaultValue.put(OAuthConstants.CONTENT_TYPE,"multipart/form-data");
//    }
//
//    private static final HiworksApi INSTANCE = new HiworksApi();
//
//
//    public static HiworksApi instance() {
//        return INSTANCE;
//    }
//    @Override
//    public String getAccessTokenEndpoint() {
//        return "https://api.hiworks.com/open/auth/accesstoken";
//    }
//
//    @Override
//    public String getAuthorizationBaseUrl() {
//        return "https://api.hiworks.com/open/auth/authform";
//    }
//
//
//    @Override
//    public OAuthConstants getAuthCodeParameterName() {
//        return OAuthConstants.AUTH_CODE;
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
