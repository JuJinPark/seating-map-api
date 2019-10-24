package com.gabia.internproject.service.OAuth.ScribeJava;

import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.service.OAuth.ScribeJava.Custom.HiworksApi20;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.api.DefaultApi20;

public class ScribeJavaApiFactory {
//    public static DefaultApi20 getApi(OAuthAPIs serviceProvider){
//        if (serviceProvider.equals("google")) {
//            return new ServiceBuilder("898505969152-hkcf91qo3gkj0jfjtshni5duqbe0uqc0.apps.googleusercontent.com")
//                    .apiSecret("6RBgEZXwPTXijKHmvgqfKGDo")
//                    .callback("http://localhost:8080/login/callback/google")
//                    .defaultScope("email")
//                    .build(GoogleApi20.instance());
//
//        } else if(serviceProvider.equals("hiworks")) {
//            return new HiworksServiceBuilder("f3xor1auc7d45n1zby6uapdggde9lqu65d5dee1e48d234.85217709.open.apps")
//                    .apiSecret("fpz4zdkdkdi60rp0tf9rp7ivhhvwjl4y")
//                    .accessType("online")
//                    .callback("http://seat.gabia.com:8080/login/callback/hiworks")
//                    .responseType("auth_code")
//                    .build(HiworksApi20.instance());
//
//        }
//        return null;
//
//    }

    public static DefaultApi20 getApi(OAuthAPIProvider serviceProvider){
        if (serviceProvider.equals(OAuthAPIProvider.GOOGLE)) {
            return GoogleApi20.instance();

        } else if(serviceProvider.equals(OAuthAPIProvider.HIWORKS)) {
            return HiworksApi20.instance();

        } else if(serviceProvider.equals(OAuthAPIProvider.GITHUB)){
            return GitHubApi.instance();
        }
        return null;

    }
}
