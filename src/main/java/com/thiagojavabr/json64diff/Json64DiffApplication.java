package com.thiagojavabr.json64diff;

import com.thiagojavabr.json64diff.resource.JsonDiffResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;

/**
 * Spring Boot application for JSON base64 binary data comparison.
 */
@SpringBootApplication
@EnableSwagger2
public class Json64DiffApplication {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(JsonDiffResource.class.getPackageName()))
                .paths(any()).build().apiInfo(new ApiInfo("JSON base64 Diff API",
                        "A set of endpoints to create the sides of a object given the sides (left and right) in JSON binary data and also to compare their respective sides.", "1.0.0", null,
                        new Contact("Thiago Teixeira", "https://twitter.com/thiagojavabr", null),null, null));
    }

    public static void main(String[] args) {
        SpringApplication.run(Json64DiffApplication.class, args);
    }
}
