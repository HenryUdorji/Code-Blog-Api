package com.codemountain.codeblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CodeBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeBlogApplication.class, args);
	}

}
