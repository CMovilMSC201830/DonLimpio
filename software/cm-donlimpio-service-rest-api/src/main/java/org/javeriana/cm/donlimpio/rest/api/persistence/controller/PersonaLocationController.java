package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PersonaLocation;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.PersonaLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PersonaLocationController {

    @Autowired
    private PersonaLocationRepository personaLocationRepository;

    public PersonaLocation savePersonaLocation(PersonaLocation personaLocation) {
        return personaLocationRepository.save(personaLocation);
    }
}
