package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import org.javeriana.cm.donlimpio.rest.api.exception.PersonaEntityNotFoundException;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ProfessionalServices;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.ProfessionalServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProfessionalController {

    @Autowired
    private ProfessionalServicesRepository professionalServicesRepository;

    public ProfessionalServices save(ProfessionalServices professionalServices, String uuid) throws PersonaEntityNotFoundException {
        return professionalServicesRepository.saveWithUUID(professionalServices, uuid);
    }

    public List<ProfessionalServices> findByUUID(String uuid) {
        return professionalServicesRepository.findAllByUUID(uuid);
    }
}
