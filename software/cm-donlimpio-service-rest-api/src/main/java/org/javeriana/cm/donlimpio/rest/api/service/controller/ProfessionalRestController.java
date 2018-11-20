package org.javeriana.cm.donlimpio.rest.api.service.controller;

import io.swagger.annotations.ApiOperation;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.ProfessionalController;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PersonaProfessionalServices;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ProfessionalServices;
import org.javeriana.cm.donlimpio.rest.api.service.model.HttpRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professional")
public class ProfessionalRestController extends AbstractController {

    @Autowired
    private ProfessionalController professionalController;

    @ApiOperation("Register a professional's service")
    @RequestMapping(value = "/service/register", method = RequestMethod.POST)
    public HttpRestResponse saveService(@RequestBody(required = true) ProfessionalServices service,
                                        @RequestBody(required = true) String uuid) {
        HttpRestResponse response;
        try {
            professionalController.save(service, uuid);
            response = HttpRestResponse
                    .parentBuilder()
                    .success(true)
                    .message("Servicio ofrecido por el profesional registrado!")
                    .build();
        } catch(Exception ex) {
            ex.printStackTrace();
            response = HttpRestResponse
                    .parentBuilder()
                    .success(false)
                    .message(String.format("Error al intentar registrar servicio ofrecido por el profesional: %s",
                            ex.getMessage())).build();
        }
        return response;
    }

    @ApiOperation("Returns the professional's offered service by uuid")
    @RequestMapping(value = "/service/search/{uuid}", method = RequestMethod.GET)
    public List<ProfessionalServices> searchProfessionalServicesByUUID(@PathVariable String uuid) {
        return professionalController.findByUUID(uuid);
    }

    @ApiOperation("Returns the professional's offered service by category")
    @RequestMapping(value = "/service/search/bycategory/{categoryId}", method = RequestMethod.GET)
    public List<PersonaProfessionalServices> searchProfessionalServicesByCategoryId(@PathVariable int categoryId) {
        return professionalController.findByCategoryId(categoryId);
    }
}
