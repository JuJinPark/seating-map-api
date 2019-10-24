//package com.gabia.internproject.service.OAuth.old;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.gabia.internproject.service.OAuth.OAuthConstants;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustStrategy;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//
//import javax.net.ssl.SSLContext;
//import java.io.UnsupportedEncodingException;
//
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//import java.util.HashMap;
//import java.util.Map;
//
//public class OAuthService {
//    private final String apiKey;
//    private final String apiSecret;
//    private final ApiProvider apiProvider;
//    private final String callback;
//    private final String responseType;
//    private final String scope;
//    private final Map<String, String> additionalParams;
//    private final Map<OAuthConstants, String> keyParmas=new HashMap<>();
//
//
//    public static class Builder {
//
//        private final String apiKey;
//
//        private  String apiSecret;
//        private  String callback;
//        private  String scope;
//        private  String responseType = "code";
//        private ApiProvider apiProvider;
//        private  Map<String, String> additionalParams= new HashMap<>();
//
//        public Builder(String apiKey) {
//            this.apiKey = apiKey;
//
//        }
//        public Builder apiSecret(String apiSecret) {
//            //emptycheck 로직
//            this.apiSecret = apiSecret;
//            return this;
//        }
//        public Builder callback(String callback) {
//            //emptycheck 로직
//            this.callback = callback;
//            return this;
//        }
//        public Builder scope(String scope) {
//            this.scope = scope;
//            return this;
//        }
//        public Builder responseType(String responseType) {
//            this.responseType = responseType;
//            return this;
//        }
//        public Builder apiProvider(ApiProvider apiProvider) {
//            this.apiProvider = apiProvider;
//            return this;
//        }
//        public Builder additionalParams(String key,String value) {
//
//            this.additionalParams.put(key,value);
//            return this;
//        }
//        public OAuthService build() {
//            return new OAuthService(this);
//        }
//
//    }
//    private OAuthService(Builder builder) {
//        apiKey  = builder.apiKey;
//        keyParmas.put(OAuthConstants.CLIENT_ID,apiKey);
//
//        apiSecret     = builder.apiSecret;
//        keyParmas.put(OAuthConstants.CLIENT_SECRET,apiSecret);
//
//        callback     = builder.callback;
//        keyParmas.put(OAuthConstants.REDIRECT_URI,callback);
//
//        scope          = builder.scope;
//        keyParmas.put(OAuthConstants.SCOPE,scope);
//
//        responseType       = builder.responseType;
//        keyParmas.put(OAuthConstants.RESPONSE_TYPE,responseType);
//
//        apiProvider = builder.apiProvider;
//        additionalParams=builder.additionalParams;
//
//    }
//
//
//    public ApiProvider getApi() {
//        return apiProvider;
//    }
//    public String getResponseType() {
//        return responseType;
//    }
//    public String getCallback() {
//        return callback;
//    }
//    public String getApiKey() {
//        return apiKey;
//    }
//    public String getScope() {
//        return scope;
//    }
//    public Map<String, String> getAdditionalParams(){return additionalParams; }
//
//    public Token getAccessToken(String code) throws UnsupportedEncodingException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//
//        OAuthRequest request = createAccessTokenRequest(code);
//        return sendAccessTokenRequest(request);
//    }
//
//    private Token sendAccessTokenRequest(OAuthRequest request) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//
//        RestTemplate restTemplate = new RestTemplate(skipSSLValidation());
//        System.out.println(request.getUrl());
//        Map<String,Object> response=restTemplate.exchange(request.getUrl(),request.getMethod(),request.getHttpEntity(),Map.class).getBody();
//        return convertMaptoToken(response);
//
//    }
//
//    private HttpComponentsClientHttpRequestFactory skipSSLValidation() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//                .loadTrustMaterial(null, acceptingTrustStrategy)
//                .build();
//
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(csf)
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory =
//                new HttpComponentsClientHttpRequestFactory();
//
//        requestFactory.setHttpClient(httpClient);
//
//        return requestFactory;
//
//    }
//
//    private Token convertMaptoToken(Map response){
//
//        if(response.get("access_token")==null){
//            response=(Map<String,Object>)response.get("data");
//        }
//
//        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//       return mapper.convertValue(response, Token.class);
//    }
//
//    private OAuthRequest createAccessTokenRequest(String code) throws UnsupportedEncodingException {
//        keyParmas.put(getApi().getAuthCodeParameterName(),code);
//        OAuthRequest request=new OAuthRequest(apiProvider.getAccessTokenEndpoint(),apiProvider.getAccessTokenVerb());
//                    request.setHeaders(apiProvider.getRequiredHeadersForAccessToken(),keyParmas,additionalParams,apiProvider.getDefaultValue());
//                    request.setBody(apiProvider.getRequiredBodyParametersForAccessToken(),keyParmas,additionalParams,apiProvider.getDefaultValue());
//                    request.setParameters(apiProvider.getRequiredParametersForAccessToken(),keyParmas,additionalParams,apiProvider.getDefaultValue());
//                    request.setUrlWithParamters();
//                    request.setHttpEntity();
//
//         return request;
//    }
//
//
//
//
//    public String getAuthorizationUrl() throws UnsupportedEncodingException {
//        return getAuthorizationUrl(null);
//    }
//
//    public String getAuthorizationUrl(String state) throws UnsupportedEncodingException {
//
//        return apiProvider.getAuthorizationUrl(getResponseType(), getApiKey(), getCallback(), getScope(), state, getAdditionalParams());
//    }
//
//
//
//}
