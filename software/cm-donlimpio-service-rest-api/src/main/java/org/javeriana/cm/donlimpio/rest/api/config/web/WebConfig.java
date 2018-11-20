package org.javeriana.cm.donlimpio.rest.api.config.web;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@ComponentScan(basePackages = {
        "org.javeriana.cm.donlimpio.rest.api.config.swagger",
        "org.javeriana.cm.donlimpio.rest.api.config.web.adapter",
        "org.javeriana.cm.donlimpio.rest.api.config.persistence",
        "org.javeriana.cm.donlimpio.rest.api.config.util",
        "org.javeriana.cm.donlimpio.rest.api.service.controller"})
@EnableWebMvc
public class WebConfig {
    @Bean
    public FirebaseApp initFirebaseSDK() throws IOException {
        InputStream serviceAccount = WebConfig.class.getResourceAsStream("/donlimpio-95d3b-firebase-adminsdk-fqaln-5bb220e2d0.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://donlimpio-95d3b.firebaseio.com/")
                .build();
        return FirebaseApp.initializeApp(options);
    }
}
