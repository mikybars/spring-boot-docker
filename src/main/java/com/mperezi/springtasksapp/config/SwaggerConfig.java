package com.mperezi.springtasksapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String BASE_PACKAGE =
            SwaggerConfig.class.getPackage().getName().replaceAll("\\.config$", ".web.rest");

    @Value("${spring.application.name}") String appName;
    @Value("${custom.swagger.contact.name}") String name;
    @Value("${custom.swagger.contact.web}") String web;
    @Value("${custom.swagger.contact.email}") String email;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appName)
                .description("RESTful API Documentation")
                .contact(new Contact(name, web, email))
                .build();
    }
}
