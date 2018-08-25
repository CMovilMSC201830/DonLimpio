package org.javeriana.cm.donlimpio.rest.service.config.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ServiceProperties {

    @Value("${google.api.key}")
    private String googleAPIKey;
}
