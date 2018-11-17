package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.DocumentTypes;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PaymentMethod;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Persona;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PersonaAddresses;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.DocumentTypesRepository;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.PaymentMethodsRepository;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.PersonaAddressRepository;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonaController {

    @Autowired
    private DocumentTypesRepository documentTypesRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaAddressRepository personaAddressRepository;

    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    public List<DocumentTypes> findAllDocumentTypes() {
        List<DocumentTypes> result = new ArrayList<>();
        Iterable<DocumentTypes> iterable = documentTypesRepository.findAll();
        Iterator<DocumentTypes> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public Optional<DocumentTypes> findDocumentTypesById(int documentType) {
        return documentTypesRepository.findById(documentType);
    }

    public Persona savePersona(Persona persona) {
        return personaRepository.save(persona);
    }
    public PersonaAddresses savePersonaAddress(PersonaAddresses persona) {
        return personaAddressRepository.save(persona);
    }
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodsRepository.save(paymentMethod);
    }
}
