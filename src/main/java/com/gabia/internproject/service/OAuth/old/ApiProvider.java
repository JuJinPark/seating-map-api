//package com.gabia.internproject.service.OAuth.old;
//
//import com.gabia.internproject.service.OAuth.OAuthConstants;
//import org.springframework.http.HttpMethod;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Map;
//
//public abstract class ApiProvider {
//    public String getRefreshTokenEndpoint() {
//        return getAccessTokenEndpoint();
//    }
//
//    public HttpMethod getAccessTokenVerb() {
//        return HttpMethod.POST;
//    }
//
//
//
//
//    public String getAuthorizationUrl(String responseType, String apiKey, String callback, String scope, String state,
//                                      Map<String, String> additionalParams) throws UnsupportedEncodingException {
//        final ParameterList parameters = new ParameterList(additionalParams);
//        parameters.add(OAuthConstants.RESPONSE_TYPE, responseType);
//        parameters.add(OAuthConstants.CLIENT_ID, apiKey);
//
//        if (callback != null) {
//            parameters.add(OAuthConstants.REDIRECT_URI, callback);
//        }
//
//        if (scope != null) {
//            parameters.add(OAuthConstants.SCOPE, scope);
//        }
//
//        if (state != null) {
//            parameters.add(OAuthConstants.STATE, state);
//        }
//
//        return parameters.appendTo(getAuthorizationBaseUrl());
//    }
//
//    public abstract String getAccessTokenEndpoint();
//    public abstract String getAuthorizationBaseUrl();
//    public abstract OAuthConstants getAuthCodeParameterName();
//
//
//    public abstract OAuthConstants[] getRequiredHeadersForAccessToken();
//    public abstract OAuthConstants[] getRequiredParametersForAccessToken();
//    public abstract OAuthConstants[] getRequiredBodyParametersForAccessToken();
//    public abstract Map<OAuthConstants,String> getDefaultValue();
//    public abstract void setDefaultValue(OAuthConstants key, String value);
//
//
//}
