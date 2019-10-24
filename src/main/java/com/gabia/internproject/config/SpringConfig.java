package com.gabia.internproject.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.internproject.config.security.HTMLCharacterEscapes;
import com.gabia.internproject.service.OAuth.OAuth2Service;
import com.gabia.internproject.service.OAuth.ScribeJava.OAuth2ServiceScribeImpl;
import com.gabia.internproject.util.JwtTokenProvider;
import com.gabia.internproject.util.ObjectMaping.MapStruct.MapStructUtil;
import com.gabia.internproject.util.ObjectMaping.ModelMapper.ModelMapperUtil;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Configuration
public class SpringConfig {

    @Bean
    public FilterRegistrationBean simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // *** URL below needs to match the Vue client URL and port ***
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }

    @Bean
    public WebMvcConfigurer getXSSConverter() {
        return new WebMvcConfigurer() {

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

                Iterator<HttpMessageConverter<?>> converterIterator = converters.iterator();
                while(converterIterator.hasNext()) {
                    // Do NOT add new one, MUST replace
                    HttpMessageConverter converter = converterIterator.next();
                    if (converter.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON))
                        converterIterator.remove();
                }
               converters.add(htmlEscapingConveter());
            }

            private HttpMessageConverter<?> htmlEscapingConveter() {
                ObjectMapper objectMapper = new ObjectMapper();
                //  ObjectMapper에 특수 문자 처리 기능 적용
                objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

                //  MessageConverter에 ObjectMapper 설정
                MappingJackson2HttpMessageConverter htmlEscapingConverter =
                        new MappingJackson2HttpMessageConverter();
                htmlEscapingConverter.setObjectMapper(objectMapper);

                return htmlEscapingConverter;
            }
        };
    }



}







