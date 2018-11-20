package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import org.javeriana.cm.donlimpio.rest.api.exception.PersonaEntityNotFoundException;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Persona;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PersonaProfessionalServices;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ProfessionalServices;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.ProfessionalServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
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

    public List<PersonaProfessionalServices> findByCategoryId(int categoryId) {
        List<PersonaProfessionalServices> personaProfessionalServices = new ArrayList<>();
        List result = professionalServicesRepository.findAllByCategoryId(categoryId);
        if (result == null) {
            return personaProfessionalServices;
        }
        for (Object object : result) {
            Object[] array = (Object[]) object;
            ProfessionalServices professionalServices = (ProfessionalServices) array[0];
            Persona persona = (Persona) array[1];
            PersonaProfessionalServices services = new PersonaProfessionalServices();
            services.setId(professionalServices.getId());
            services.setCategoryId(professionalServices.getCategoryId());
            services.setPersonaId(professionalServices.getPersonaId());
            services.setDescription(professionalServices.getDescription());
            services.setName(professionalServices.getName());
            services.setPrice(professionalServices.getPrice());
            services.setUuidUser(persona.getUuid());
            personaProfessionalServices.add(services);
        }
        return personaProfessionalServices;
    }
}
