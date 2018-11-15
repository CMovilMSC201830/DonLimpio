package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Categories;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PaymentMethod;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Services;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class ServicesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    @Autowired
    private ServicesRepository servicesRepositoryImpl;

    public List<Categories> findAllCategories() {
        List<Categories> result = new ArrayList<>();
        Iterable<Categories> iterable = categoriesRepository.findAll();
        Iterator<Categories> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public Optional<Categories> findCategoriesById(int categoryId) {
        return categoriesRepository.findById(categoryId);
    }

    public List<PaymentMethod> findAllPaymentMethods() {
        List<PaymentMethod> result = new ArrayList<>();
        Iterable<PaymentMethod> iterable = paymentMethodsRepository.findAll();
        Iterator<PaymentMethod> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public Services saveService(Services service) {
        return servicesRepositoryImpl.save(service);
    }

    public Optional<Services> findServicesById(int serviceId) {
        return servicesRepositoryImpl.findById(serviceId);
    }

    public List<Services> findAllServicesByPersona(long personaId) {
        return servicesRepositoryImpl.findAllByPersona(personaId);
    }

    public int updateStatusByPersona(int serviceId, int status) {
        return servicesRepositoryImpl.updateStatusById(serviceId, status);
    }
}
