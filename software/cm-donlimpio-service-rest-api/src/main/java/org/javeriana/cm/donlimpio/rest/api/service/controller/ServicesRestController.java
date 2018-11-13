package org.javeriana.cm.donlimpio.rest.api.service.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.InvoicesController;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.PersonaController;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.ServicesController;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.javeriana.cm.donlimpio.rest.api.service.model.HttpRestResponse;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/services")
public class ServicesRestController extends AbstractController {

    @Autowired
    private PersonaController personaController;

    @Autowired
    private ServicesController servicesController;

    @Autowired
    private InvoicesController invoicesController;

    @ApiOperation("Returns all possible categories for a service")
    @RequestMapping(value = "/search/categories", method = RequestMethod.GET)
    public List<Categories> findCategories() {
        return servicesController.findAllCategories();
    }

    @ApiOperation("Returns a category by id")
    @RequestMapping(value = "/search/categories/{id}", method = RequestMethod.GET)
    public Categories findCategoriesById(@PathVariable int id) {
        return servicesController.findCategoriesById(id).get();
    }

    @ApiOperation("Returns all possible payment methods")
    @RequestMapping(value = "/search/payment_methods", method = RequestMethod.GET)
    public List<PaymentMethod> findPaymentMethods() {
        return servicesController.findAllPaymentMethods();
    }

    @ApiOperation("Register a service")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpRestResponse saveService(@RequestBody(required = true) Services service) {
        HttpRestResponse response;
        try {
            // Save person first
            Persona persona = personaController.savePersona(service.getPersona());
            PaymentMethod paymentMethod =
                    personaController.savePaymentMethod(service.getInvoice().getPaymentMethod());
            service.getInvoice().setPaymentMethod(paymentMethod);
            // Save invoice
            Invoices invoice = invoicesController.saveInvoice(service.getInvoice());
            // Save persona address
            PersonaAddresses personaAddresses = personaController.savePersonaAddress(service.getPersonaAddress());
            // Update objects
            service.setPersona(persona);
            service.setInvoice(invoice);
            service.setPersonaAddress(personaAddresses);
            servicesController.saveService(service);
            response = HttpRestResponse
                    .builder()
                    .success(true)
                    .message("Servicio agendado exitosamente!").build();
        } catch(Exception ex) {
            log.error("Error", ex);
            response = HttpRestResponse
                    .builder()
                    .success(false)
                    .message(String.format("Error al intentar crear servicio: %s", ex.getMessage())).build();
        }
        return response;
    }
}
