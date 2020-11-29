package com.codemountain.codeblog;

import com.codemountain.codeblog.config.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
@OpenAPIDefinition(info = @Info(title = "Code-Blog Api", version = "1.0", description = "Api for all Code-Blog client"))
@Import(SwaggerConfiguration.class)
public class CodeBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeBlogApplication.class, args);
	}

}
