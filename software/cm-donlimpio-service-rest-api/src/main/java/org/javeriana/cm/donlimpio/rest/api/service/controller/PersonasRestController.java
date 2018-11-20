package org.javeriana.cm.donlimpio.rest.api.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.PersonaController;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.PersonaLocationController;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.DocumentTypes;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Persona;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PersonaLocation;
import org.javeriana.cm.donlimpio.rest.api.service.model.HttpRestResponse;
import org.javeriana.cm.donlimpio.rest.api.service.model.PersonaRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Personas Resources")
@RestController
@RequestMapping("/personas")
public class PersonasRestController extends AbstractController {

    @Autowired
    private PersonaController personaController;

    @Autowired
    private PersonaLocationController personaLocationController;

    @ApiOperation("Returns all possible document types for a person")
    @RequestMapping(value = "/search/document_types", method = RequestMethod.GET)
    public List<DocumentTypes> findDocumentTypes() {
        return personaController.findAllDocumentTypes();
    }

    @ApiOperation("Returns a document type by id")
    @RequestMapping(value = "/search/document_types/{id}", method = RequestMethod.GET)
    public DocumentTypes findDocumentTypes(@PathVariable int id) {
        return personaController.findDocumentTypesById(id).get();
    }

    @ApiOperation("Save a persona")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public HttpRestResponse savePersona(@RequestBody Persona persona) {
        PersonaRestResponse httpRestResponse;
        try {
            Persona response = personaController.savePersona(persona);
            httpRestResponse = PersonaRestResponse
                    .builder()
                    .message("Persona creada exitosamente!")
                    .success(true)
                    .personaId(response.getId())
                    .build();
        } catch(Exception ex) {
            httpRestResponse = PersonaRestResponse
                    .builder()
                    .message(ex.getMessage())
                    .success(false)
                    .personaId(-1)
                    .build();
        }
        return httpRestResponse;
    }

    @ApiOperation("Save a persona's location")
    @RequestMapping(value = "/location/save", method = RequestMethod.POST)
    public HttpRestResponse savePersonaLocation(@RequestBody PersonaLocation location) {
        HttpRestResponse httpRestResponse;
        try {
            personaLocationController.savePersonaLocation(location);
            httpRestResponse = HttpRestResponse
                    .parentBuilder()
                    .message("Ubicacion registrada con exito!")
                    .success(true)
                    .build();
        } catch(Exception ex) {
            ex.printStackTrace();
            httpRestResponse = HttpRestResponse
                    .parentBuilder()
                    .message(ex.getMessage())
                    .success(false)
                    .build();
        }
        return httpRestResponse;
    }

    @ApiOperation("Returns a Persona by UUID")
    @RequestMapping(value = "/search/byuuid/{uuid}", method = RequestMethod.GET)
    public List<Persona> searchPersonaByUUID(@PathVariable String uuid) {
        return personaController.findByUUID(uuid);
    }
}
