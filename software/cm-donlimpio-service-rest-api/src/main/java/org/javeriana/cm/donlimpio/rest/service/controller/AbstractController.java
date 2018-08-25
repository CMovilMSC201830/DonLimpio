package org.javeriana.cm.donlimpio.rest.service.controller;

import org.javeriana.cm.donlimpio.rest.service.config.util.ServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractController {
    @Autowired
    private ServiceProperties properties;
}
