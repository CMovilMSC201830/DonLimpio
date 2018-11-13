package org.javeriana.cm.donlimpio.rest.api.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.PersonaController;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.DocumentTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@Api("Personas Resources")
@RestController
@RequestMapping("/personas")
public class PersonasRestController extends AbstractController {

    @Autowired
    private PersonaController personaController;

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
}
