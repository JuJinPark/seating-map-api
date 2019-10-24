//package com.gabia.internproject.service.OAuth.old;
//
//public class OAuthServiceFactory {
//    public static OAuthService getService(String serviceProvider){
//        if("google".equals(serviceProvider)){
//           return new OAuthService.Builder("898505969152-hkcf91qo3gkj0jfjtshni5duqbe0uqc0.apps.googleusercontent.com")
//                   .apiSecret("6RBgEZXwPTXijKHmvgqfKGDo")
//                   .callback("http://localhost:8080/login/callback/google")
//                   .additionalParams("access_type","offline")
//                   .additionalParams("prompt","consent")
//                   .scope("email")
//                   .apiProvider(GoogleApi.instance())
//                   .build();
//        }else if("hiworks".equals(serviceProvider)){
//            return new OAuthService.Builder("f3xor1auc7d45n1zby6uapdggde9lqu65d5dee1e48d234.85217709.open.apps")
//                    .apiSecret("fpz4zdkdkdi60rp0tf9rp7ivhhvwjl4y")
//                    .callback("http://seat.gabia.com:8080/login/callback/hiworks")
//                    .additionalParams("access_type","offline")
//                    .apiProvider(HiworksApi.instance())
//                    .build();
//        }else if("github".equals(serviceProvider)){
//            return new OAuthService.Builder("3d69aa3156aa86f6eb84")
//                    .apiSecret("9b1dc3460068922506d5c27b2d7155bd31f130c8")
//                    .callback("http://localhost:8080/login/callback/github")
//                    .apiProvider(GitHubApi.instance())
//                    .build();
//        }
//        return null;
//
//    }
//}
