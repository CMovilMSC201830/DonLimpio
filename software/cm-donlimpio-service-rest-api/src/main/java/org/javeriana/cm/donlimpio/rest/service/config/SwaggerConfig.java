package org.javeriana.cm.donlimpio.rest.service.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class to configure and enable Swagger
 */
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        String title = "DonLimpio REST API";
        String description = "DonLimpio REST services for Mobile App";
        String version = "1.0";
        String termsOfServiceUrl = "http://www.javeriana.edu.co";
        String contact = "http://www.javeriana.edu.co";
        String license = "Free";
        String licenseUrl = "http://www.javeriana.edu.co";

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo(title, description, version, termsOfServiceUrl, contact, license, licenseUrl))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
