package com.codemountain.codeblog.config;

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

@Configuration
public class SwaggerConfiguration {

    @Value("${swagger.host.url}")
    private String hostUrl;

    @Bean
    public Docket codeBlogApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Code-Blog Api")
                .description("Api for all Code-Blog client")
                .contact(new Contact("Henry Udorji", "https://codemountain.com", "henryUdorji@gmail.com"))
                .version("1.0")
                .license("Apache Licence Version 2.0")
                .build();
    }
}
