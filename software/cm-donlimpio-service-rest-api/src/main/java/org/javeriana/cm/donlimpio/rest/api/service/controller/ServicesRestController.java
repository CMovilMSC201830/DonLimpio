package org.javeriana.cm.donlimpio.rest.api.service.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.ServicesController;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.*;
import org.javeriana.cm.donlimpio.rest.api.service.model.ServiceRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.javeriana.cm.donlimpio.rest.api.service.model.HttpRestResponse;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/services")
public class ServicesRestController extends AbstractController {

    @Autowired
    private ServicesController servicesController;

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
            Services services = servicesController.saveService(service);
            response = ServiceRestResponse
                    .builder()
                    .serviceId(services.getId())
                    .success(true)
                    .message("Servicio agendado exitosamente!").build();
        } catch(Exception ex) {
            ex.printStackTrace();
            response = ServiceRestResponse
                    .builder()
                    .success(false)
                    .message(String.format("Error al intentar crear servicio: %s", ex.getMessage())).build();
        }
        return response;
    }

    @ApiOperation("Returns the services by id")
    @RequestMapping(value = "/search/byid/{id}", method = RequestMethod.GET)
    public Optional<Services> searchServicesById(@PathVariable int id) {
        return servicesController.findServicesById(id);
    }

    @ApiOperation("Returns the services by persona id")
    @RequestMapping(value = "/search/bypersona/{id}", method = RequestMethod.GET)
    public List<Services> searchServicesByPersona(@PathVariable long id) {
        return servicesController.findAllServicesByPersona(id);
    }

    @ApiOperation("Updates a services's status")
    @RequestMapping(value = "/upadate/status/{serviceId}/{statusId}", method = RequestMethod.POST)
    public HttpRestResponse updateServiceByStatus(@PathVariable int serviceId, @PathVariable int statusId) {
        int result = servicesController.updateStatusByPersona(serviceId, statusId);
        String message = String.format("Actualizados %s entradas", result);
        if (result > 0) {
            return ServiceRestResponse
                    .builder()
                    .serviceId(serviceId)
                    .success(true)
                    .message(message).build();
        } else {
            return ServiceRestResponse
                    .builder()
                    .success(false)
                    .serviceId(serviceId)
                    .message(message).build();
        }
    }
}
