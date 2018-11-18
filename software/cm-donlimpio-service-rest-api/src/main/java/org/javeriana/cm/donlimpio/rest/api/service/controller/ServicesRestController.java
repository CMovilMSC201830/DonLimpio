package org.javeriana.cm.donlimpio.rest.api.service.controller;

import com.google.firebase.FirebaseException;
import io.swagger.annotations.ApiOperation;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.ServicesController;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PaymentMethod;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Services;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ServicesStatus;
import org.javeriana.cm.donlimpio.rest.api.service.model.HttpRestResponse;
import org.javeriana.cm.donlimpio.rest.api.service.model.ServiceRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
public class ServicesRestController extends AbstractController {

    @Autowired
    private ServicesController servicesController;

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
            Services services = servicesController.saveWithUUID(service);
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

    @ApiOperation("Returns the services by uuid")
    @RequestMapping(value = "/search/byuuid/{uuid}", method = RequestMethod.GET)
    public List<Services> searchServicesByPersona(@PathVariable String uuid) {
        return servicesController.findAllServicesByUUID(uuid);
    }

    @ApiOperation("Updates a services's status")
    @RequestMapping(value = "/update/status/{serviceId}/{statusId}", method = RequestMethod.POST)
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

    @ApiOperation("Returns all possible services's status")
    @RequestMapping(value = "/status/all", method = RequestMethod.GET)
    public List<ServicesStatus> searchServicesStatus() {
        return servicesController.findAllServicesStatus();
    }

    @ApiOperation("Service request notification")
    @RequestMapping(value = "/notify/request/{uuid}/{categoryId}/{serviceStatusId}",
            method = RequestMethod.GET)
    public HttpRestResponse notify(@PathVariable String uuid, @PathVariable Integer categoryId,
                                   @PathVariable Integer serviceStatusId) {
        HttpRestResponse response;
        try {
            servicesController.registerServiceRequest(uuid, categoryId, serviceStatusId);
            response = HttpRestResponse
                    .parentBuilder()
                    .success(true)
                    .message("Peticion de servicio registrada exitosamente!").build();
        } catch (FirebaseException e) {
            response = HttpRestResponse
                    .parentBuilder()
                    .success(false)
                    .message(String.format("Error al intentar registrar peticion de servicio: %s",
                            e.getMessage())).build();
        }
        return response;
    }
}
