package com.gabia.internproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerApi() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.gabia.internproject.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false) // 기본으로 세팅되는 200,401,403,404 메시지를 표시 하지 않음
                .globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.PATCH, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getCustomizedResponseMessages());

    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("자리배치 프로그램 API 문서입니다.").version("1").build();
    }

    private List<ResponseMessage> getCustomizedResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
//        responseMessages.add(new ResponseMessageBuilder().code(201).message("Created").build());
        responseMessages.add(new ResponseMessageBuilder().code(200).message("AccessDenied").build());
        responseMessages.add(new ResponseMessageBuilder().code(403).message("AccessDenied").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("Internal server Error").build());
        return responseMessages;
    }
}