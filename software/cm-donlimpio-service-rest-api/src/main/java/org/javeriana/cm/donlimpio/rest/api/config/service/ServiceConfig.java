package org.javeriana.cm.donlimpio.rest.api.config.service;

import org.javeriana.cm.donlimpio.rest.api.config.web.WebAppInitializer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {
        "org.javeriana.cm.donlimpio.rest.api.config.service",
})
public class ServiceConfig {

    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory() {
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        factory.setContextPath("/cm-donlimpio-service-rest-api");
        factory.setPort(9090);
        List initializers = new ArrayList<>();
        initializers.add(new WebAppInitializer());
        factory.setInitializers(initializers);
        return factory;
    }
}
