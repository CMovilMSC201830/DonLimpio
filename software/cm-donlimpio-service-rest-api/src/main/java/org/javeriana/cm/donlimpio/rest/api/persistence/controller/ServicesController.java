package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.javeriana.cm.donlimpio.rest.api.exception.PersonaEntityNotFoundException;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PaymentMethod;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ServiceRequest;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Services;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ServicesStatus;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.PaymentMethodsRepository;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.ServicesRepository;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.ServicesStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ServicesController {

    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    @Autowired
    private ServicesStatusRepository servicesStatusRepository;

    @Autowired
    private ServicesRepository servicesRepositoryImpl;

    public List<PaymentMethod> findAllPaymentMethods() {
        List<PaymentMethod> result = new ArrayList<>();
        Iterable<PaymentMethod> iterable = paymentMethodsRepository.findAll();
        Iterator<PaymentMethod> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public Services saveWithUUID(Services service) throws PersonaEntityNotFoundException {
        return servicesRepositoryImpl.saveWithUUID(service);
    }

    public Optional<Services> findServicesById(int serviceId) {
        return servicesRepositoryImpl.findById(serviceId);
    }

    public List<Services> findAllServicesByPersona(long personaId) {
        return servicesRepositoryImpl.findAllByPersona(personaId);
    }

    public List<Services> findAllServicesByUUID(String uuid) {
        return servicesRepositoryImpl.findAllByUUID(uuid);
    }

    public List<ServicesStatus> findAllServicesStatus() {
        List<ServicesStatus> result = new ArrayList<>();
        Iterable<ServicesStatus> iterable = servicesStatusRepository.findAll();
        Iterator<ServicesStatus> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public void registerServiceRequest(String uuid, int categoryId, int serviceStatus) throws FirebaseException {
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("Services_Request");
            DatabaseReference dbRefChild = ref.child(uuid);
            dbRefChild.setValueAsync(new ServiceRequest(categoryId, serviceStatus, new Date()));
        } catch(Exception ex) {
            throw new FirebaseException(ex.getMessage());
        }
    }

    public int updateStatusByPersona(int serviceId, int status) {
        return servicesRepositoryImpl.updateStatusById(serviceId, status);
    }
}
