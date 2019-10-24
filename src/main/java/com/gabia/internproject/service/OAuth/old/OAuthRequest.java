//package com.gabia.internproject.service.OAuth.old;
//
//
//import com.gabia.internproject.service.OAuth.OAuthConstants;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Collections;
//import java.util.Map;
//
//public class OAuthRequest {
//    private String url;
//    private HttpMethod method;
//    private HttpHeaders headers=new HttpHeaders();
//    private ParameterList parameterList = new ParameterList();
//    private MultiValueMap<String, String> body=new LinkedMultiValueMap<String, String>();
//    private HttpEntity<?> httpEntity;
//
//    OAuthRequest(String url,HttpMethod method){
//        this.url=url;
//        this.method=method;
//
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//    }
//
//    public String findValueFrom(OAuthConstants key, Map<OAuthConstants, String> keyParams, Map<String, String> additionalParams, Map<OAuthConstants, String> defaultValue){
//
//        if(keyParams.get(key)!=null){
//            return keyParams.get(key);
//        }
//
//        if(additionalParams.get(key.getText())!=null){
//            return additionalParams.get(key.getText());
//        }
//
//        if(defaultValue.get(key)!=null){
//            return defaultValue.get(key);
//        }
//
//        return null;
//
//    }
//
//    public void setHeaders(OAuthConstants[] headers, Map<OAuthConstants, String> keyParams, Map<String, String> additionalParams, Map<OAuthConstants, String> defaultValue){
//
//       for(int i=0;i<headers.length;i++){
//           OAuthConstants headerName= headers[i];
//           String value=findValueFrom(headerName,keyParams,additionalParams,defaultValue);
//           if(value!=null){
//
//               addHeader(headerName,value);
//           }
//
//
//       }
//
//
//
//    }
//
//    public void setBody(OAuthConstants[] bodyParameters, Map<OAuthConstants, String> keyParams, Map<String, String> additionalParams, Map<OAuthConstants, String> defaultValue){
//
//        for(int i=0;i<bodyParameters.length;i++){
//            OAuthConstants bodyParametersName= bodyParameters[i];
//            String value=findValueFrom(bodyParametersName,keyParams,additionalParams,defaultValue);
//            if(value!=null){
//                addBodyParameter(bodyParametersName,value);
//            }
//
//
//        }
//    }
//
//    public void setParameters(OAuthConstants[] paramters, Map<OAuthConstants, String> keyParams, Map<String, String> additionalParams, Map<OAuthConstants, String> defaultValue){
//        for(int i=0;i<paramters.length;i++){
//            OAuthConstants paramName= paramters[i];
//            String value=findValueFrom(paramName,keyParams,additionalParams,defaultValue);
//            if(value!=null){
//                parameterList.add(paramName,value);
//            }
//        }
//
//    }
//
//    public void setUrlWithParamters() throws UnsupportedEncodingException {
//        this.url=parameterList.appendToWithoutEncoding(this.url);
//
//    }
//
//    public void setHttpEntity(){
//        if(body.size()==0){
//           httpEntity=new HttpEntity<>(headers);
//            return;
//        }
//        httpEntity=new HttpEntity<>(body,headers);
//
//    }
//
//
//    private void addHeader(OAuthConstants key, String value) {
//        headers.add(key.getText(),value);
//    }
//
//    private void addBodyParameter(OAuthConstants key, String value) {
//
//        body.add(key.getText(), value);
//    }
//
//
//    public HttpEntity<?> getHttpEntity() {
//        return httpEntity;
//    }
//
//    public HttpHeaders getHeaders() {
//        return headers;
//    }
//
//    public HttpMethod getMethod() {
//        return method;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public MultiValueMap<String, String> getBody() {
//        return body;
//    }
//}
