package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import org.javeriana.cm.donlimpio.rest.api.persistence.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    public void userAuthenticate(String username, String password) {
        personaRepository.findByUsernameAndPassword(username, password);
    }
}
