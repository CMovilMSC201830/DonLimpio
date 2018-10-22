package org.javeriana.cm.donlimpio.rest.api.config.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ServiceProperties {

    @Value("${google.api.key}")
    private String googleAPIKey;

    @Value("${donlimpo.db.driver}")
    private String donlimpioDbDriver;
    @Value("${donlimpio.db.url}")
    private String donlimpioDbUrl;
    @Value("${donlimpio.db.username}")
    private String donlimpioDbUsername;
    @Value("${donlimpio.db.password}")
    private String donlimpioDbPassword;
    @Value("${donlimpio.db.packages.to.scan}")
    private String donlimpioDbPackagesToScan;
    @Value("${donlimpio.db.persistence.unit.name}")
    private String donlimpioDbPersistenceUnitName;
}
