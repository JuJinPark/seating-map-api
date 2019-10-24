package com.gabia.internproject.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.internproject.dto.ErrorDTO;
import com.gabia.internproject.exception.ErrorCode;
import com.gabia.internproject.exception.customExceptions.JwtTokenException;
import com.gabia.internproject.service.OAuth.OAuthConstants;
import com.gabia.internproject.service.OAuth.RedirectionURL;
import com.gabia.internproject.util.ParameterList;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ExceptionHandlerFilter  extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws UnsupportedEncodingException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtTokenException e) {
            setErrorResponse(request,response,e.getErrorCode(), e.getReason());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            setErrorResponse(request,response,ErrorCode.INTERNAL_SERVER_ERROR_FILTER,e.getMessage());
        }
    }

    public void setErrorResponse(HttpServletRequest request,HttpServletResponse response,ErrorCode errorCode,String reason) throws UnsupportedEncodingException {
            String ss=request.getRequestURL().toString();
        if(request.getRequestURL().toString().startsWith("http://seat.gabia.com:8080/register/callback")){
            redirectToErrorPage(response,errorCode.getStatus(),reason);
            return;
        }


        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json");

        ErrorDTO error=ErrorDTO.of(errorCode,reason);
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json= mapper.writeValueAsString(error);
            response.getWriter().write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectToErrorPage(HttpServletResponse response,int status,String message) throws UnsupportedEncodingException {

        ParameterList parameterList=new ParameterList();
        parameterList.add(OAuthConstants.JOBNAME.getText(),"register");
        parameterList.add(OAuthConstants.STATUS.getText(),status+"");
        parameterList.add(OAuthConstants.MESSAGE.getText(),message);

        response.setHeader("Location",parameterList.appendTo(RedirectionURL.INFO.getURL()));
        response.setStatus(302);
    }
}
