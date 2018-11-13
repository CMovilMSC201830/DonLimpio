package org.javeriana.cm.donlimpio.rest.api.main;

import org.javeriana.cm.donlimpio.rest.api.config.service.ServiceConfig;
import org.springframework.boot.SpringApplication;

public class App {
    public static void main(String[] args) {
        SpringApplication.run(ServiceConfig.class, args);
    }
}
