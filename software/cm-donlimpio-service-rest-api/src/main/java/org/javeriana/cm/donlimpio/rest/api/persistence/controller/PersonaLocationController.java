package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PersonaLocation;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.PersonaLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PersonaLocationController {

    @Autowired
    private PersonaLocationRepository personaLocationRepository;

    public PersonaLocation savePersonaLocation(PersonaLocation personaLocation) throws FirebaseException {
        PersonaLocation response = personaLocationRepository.save(personaLocation);
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("Professional_Location");
            DatabaseReference dbRefChild = ref.child(response.getUuid());
            dbRefChild.setValueAsync(response);
        } catch(Exception ex) {
            throw new FirebaseException(ex.getMessage());
        }
        return response;
    }
}
