package com.gabia.internproject.controller;

import com.gabia.internproject.config.security.CurrentUser;
import com.gabia.internproject.dto.response.SocialLoginUrlResponseDTO;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.service.OAuth.OauthLoginService;
import com.gabia.internproject.util.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

@RestController
@ApiIgnore
public class OAuthController {

    OauthLoginService oauthLoginService;



    @Autowired
    OAuthController(OauthLoginService oauthLoginService, JwtTokenProvider jwtTokenProvider) {
        this.oauthLoginService = oauthLoginService;
    }

    @GetMapping("/login/hiworks")
    public void requestHiworksLoginForm(HttpServletResponse response) {
        SocialLoginUrlResponseDTO result = oauthLoginService.getSocialLoginFormUrl(OAuthAPIProvider.HIWORKS, OAuthAPIProvider.HIWORKS.getLoginCallBackUrl());
        response.setHeader("Location", result.getLoginUrl());
        response.setStatus(302);

    }

    @GetMapping(value = {"/login/callback/hiworks"}, params = {"auth_code"})
    public void hiworksLoginCallback(HttpServletResponse response, @RequestParam String auth_code) throws UnsupportedEncodingException {

        String UrlWithResponseMsg = oauthLoginService.processLogin(response, OAuthAPIProvider.HIWORKS, auth_code, OAuthAPIProvider.HIWORKS.getLoginCallBackUrl());
        response.setHeader("Location", UrlWithResponseMsg);
        response.setStatus(302);


    }

    @GetMapping("/login/google")
    public void requestGoogleLoginForms(HttpServletResponse response) {
        SocialLoginUrlResponseDTO result = oauthLoginService.getSocialLoginFormUrl(OAuthAPIProvider.GOOGLE, OAuthAPIProvider.GOOGLE.getLoginCallBackUrl());
        response.setHeader("Location", result.getLoginUrl());
        response.setStatus(302);


    }

    @GetMapping(value = {"/login/callback/google"}, params = {"code"})
    public void googleLoginCallback(HttpServletResponse response, @RequestParam String code) throws UnsupportedEncodingException {

        String UrlWithResponseMsg = oauthLoginService.processLogin(response, OAuthAPIProvider.GOOGLE, code, OAuthAPIProvider.GOOGLE.getLoginCallBackUrl());
        response.setHeader("Location", UrlWithResponseMsg);
        response.setStatus(302);

    }


    @GetMapping("/login/github")
    public void requestGithubLoginForms(HttpServletResponse response) {
        SocialLoginUrlResponseDTO result = oauthLoginService.getSocialLoginFormUrl(OAuthAPIProvider.GITHUB, OAuthAPIProvider.GITHUB.getLoginCallBackUrl());
        response.setHeader("Location", result.getLoginUrl());
        response.setStatus(302);

    }

    @GetMapping(value = {"/login/callback/github"}, params = {"code"})
    public void githubeLoginCallback(HttpServletResponse response, @RequestParam String code) throws UnsupportedEncodingException {

        String UrlWithResponseMsg = oauthLoginService.processLogin(response, OAuthAPIProvider.GITHUB, code, OAuthAPIProvider.GITHUB.getLoginCallBackUrl());
        response.setHeader("Location", UrlWithResponseMsg);
        response.setStatus(302);

    }

    @GetMapping(value = {"/register/google"})
    public void registerGoogleLogin(HttpServletResponse response) {
        SocialLoginUrlResponseDTO result = oauthLoginService.getSocialLoginFormUrl(OAuthAPIProvider.GOOGLE, OAuthAPIProvider.GOOGLE.getRegisterCallBackUrl());
        response.setHeader("Location", result.getLoginUrl());
        response.setStatus(302);
    }

    @GetMapping(value = {"/register/callback/google"})
    public void googleRegisterCallback(HttpServletResponse response, @RequestParam String code,  @AuthenticationPrincipal CurrentUser user) throws UnsupportedEncodingException {

        String UrlWithResponseMsg = oauthLoginService.registerEmail(OAuthAPIProvider.GOOGLE, code, user, OAuthAPIProvider.GOOGLE.getRegisterCallBackUrl());
        response.setHeader("Location", UrlWithResponseMsg);
        response.setStatus(302);

    }

    @GetMapping(value = {"/register/github"})
    public void registerGithubLogin(HttpServletResponse response) {
        SocialLoginUrlResponseDTO result = oauthLoginService.getSocialLoginFormUrl(OAuthAPIProvider.GITHUB, OAuthAPIProvider.GITHUB.getRegisterCallBackUrl());
        response.setHeader("Location",  result.getLoginUrl());
        response.setStatus(302);
    }

    @GetMapping(value = {"/register/callback/github"})
    public void githubRegisterCallback(HttpServletResponse response, @RequestParam String code, @AuthenticationPrincipal CurrentUser user) throws UnsupportedEncodingException {

        String UrlWithResponseMsg = oauthLoginService.registerEmail(OAuthAPIProvider.GITHUB, code, user, OAuthAPIProvider.GITHUB.getRegisterCallBackUrl());
        response.setHeader("Location", UrlWithResponseMsg);
        response.setStatus(302);


    }


}
