package org.javeriana.cm.donlimpio.rest.api.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {
        "org.javeriana.cm.donlimpio.rest.api.config.swagger",
        "org.javeriana.cm.donlimpio.rest.api.config.web.adapter",
        "org.javeriana.cm.donlimpio.rest.api.config.persistence",
        "org.javeriana.cm.donlimpio.rest.api.config.util",
        "org.javeriana.cm.donlimpio.rest.api.service.controller"})
@EnableWebMvc
public class WebConfig {
}
