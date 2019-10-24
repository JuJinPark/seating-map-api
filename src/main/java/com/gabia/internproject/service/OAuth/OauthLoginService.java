package com.gabia.internproject.service.OAuth;

import com.fasterxml.jackson.databind.JsonNode;
import com.gabia.internproject.config.security.CurrentUser;
import com.gabia.internproject.dto.request.SocialLoginRegisterDTO;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;

import com.gabia.internproject.dto.response.SocialLoginUrlResponseDTO;
import com.gabia.internproject.exception.customExceptions.ApiExecutionException;
import com.gabia.internproject.exception.customExceptions.BusinessException;
import com.gabia.internproject.exception.customExceptions.CheckedBusinessException;
import com.gabia.internproject.service.EmployeeService;
import com.gabia.internproject.util.ParameterList;
import com.gabia.internproject.service.SocialLoginEmailService;

import com.gabia.internproject.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Service
public class OauthLoginService {

    OAuth2Service oauthService;

    EmployeeService employeeService;

    SocialLoginEmailService socialLoginEmailService;

    JwtTokenProvider jwtTokenProvider;

    private final OAuthAPIProvider DEFAULT_PROVIDER = OAuthAPIProvider.HIWORKS;

    @Autowired
    OauthLoginService(OAuth2Service oauthService, EmployeeService employeeService, SocialLoginEmailService socialLoginEmailService, JwtTokenProvider jwtTokenProvider) {
        this.oauthService = oauthService;
        this.employeeService = employeeService;
        this.socialLoginEmailService = socialLoginEmailService;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    public SocialLoginUrlResponseDTO getSocialLoginFormUrl(OAuthAPIProvider provider, String callback) {

        return new SocialLoginUrlResponseDTO(oauthService.getAuthorizationUrl(provider, callback));

    }

    public String registerEmail(OAuthAPIProvider provider, String code, CurrentUser loginUser, String callback) throws UnsupportedEncodingException {

        try {
            String accessToken = oauthService.getAccessToken(provider, code, callback);
            String userEmail = getUserEmail(provider, accessToken);
            socialLoginEmailService.registerSocialEmail(createDTOForRegister(userEmail,provider,loginUser));
        } catch (BusinessException e) {
            e.printStackTrace();
            return createRegisterRedirectionUrlWithResponseMsg(e.getErrorCode().getStatus(),e.getReason());
        } catch(CheckedBusinessException e){
            e.printStackTrace();
            return createRegisterRedirectionUrlWithResponseMsg(e.getErrorCode().getStatus(),e.getReason());
        }
        return createRegisterRedirectionUrlWithResponseMsg(200,"성공적으로 등록 되었습니다.");
    }

    public SocialLoginRegisterDTO createDTOForRegister(String email,OAuthAPIProvider provider, CurrentUser loginUser) {
        SocialLoginRegisterDTO request = new SocialLoginRegisterDTO();
        request.setEmailAddress(email);
        request.setEmployeeId(loginUser.getEmployeeId());
        request.setProvider(provider);
        return request;
    }

    public String processLogin(HttpServletResponse response, OAuthAPIProvider provider, String code, String callback) throws UnsupportedEncodingException {

        try {
            String accessToken = oauthService.getAccessToken(provider, code, callback);
            String userEmail = getUserEmail(provider, accessToken);
            EmployeeResponseDTO loginUser = getLoginUser(provider, userEmail);
            String jwt = jwtTokenProvider.createToken(loginUser,provider);
            createAndSetJWTCookie(response, jwt);
        } catch (BusinessException e) {
            e.printStackTrace();
            return createLoginRedirectionUrlWithResponseMsg(e.getErrorCode().getStatus(),e.getReason());
        }catch(CheckedBusinessException e){
            e.printStackTrace();
            return createLoginRedirectionUrlWithResponseMsg(e.getErrorCode().getStatus(),e.getReason());
        }

        return createLoginRedirectionUrlWithResponseMsg(200,"성공적으로 로그인 하셨습니다.");

    }

    @Transactional
    public EmployeeResponseDTO getLoginUser(OAuthAPIProvider provider, String userEmail) {

        if (!isDefaultApiProvider(provider)) {
        //   return socialLoginEmailService.getSocialLoginEmailByEmailAndProvider(userEmail,provider.getName()).getEmployee();
            return socialLoginEmailService.getSocialLoginEmailByEmailAndProvider(userEmail,provider).getEmployee();
        }
        return employeeService.getEmployeeByEmail(userEmail);

    }

    private boolean isDefaultApiProvider(OAuthAPIProvider provider) {
        return DEFAULT_PROVIDER.equals(provider);
    }


    public String getUserEmail(OAuthAPIProvider provider, String accessToken) throws ApiExecutionException {

        JsonNode result = oauthService.makeApiRequest(provider.getUserInfoRequest(), provider, accessToken);
        String UserEmail = result.get(provider.getUserInfoResponseKeys().get("userEmail")).asText();
        return UserEmail;
    }

    public void createAndSetJWTCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie(OAuthConstants.SEAT_API_JWT.getText(), value);
        cookie.setDomain("seat.gabia.com");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(600000);
        response.addCookie(cookie);

    }

    public String createRegisterRedirectionUrlWithResponseMsg(int status,String message) throws UnsupportedEncodingException {

        return createRedirectionUrlWithResponseMsg("register",status,message);
    }



    public String createLoginRedirectionUrlWithResponseMsg(int status,String message) throws UnsupportedEncodingException {

        return createRedirectionUrlWithResponseMsg("login",status,message);
    }

    private String createRedirectionUrlWithResponseMsg(String jobName,int status,String message) throws UnsupportedEncodingException {
        ParameterList parameterList=new ParameterList();
        parameterList.add(OAuthConstants.JOBNAME.getText(),jobName);
        parameterList.add(OAuthConstants.STATUS.getText(),status+"");
        parameterList.add(OAuthConstants.MESSAGE.getText(),message);

        return parameterList.appendTo(RedirectionURL.INFO.getURL());
    }


}
