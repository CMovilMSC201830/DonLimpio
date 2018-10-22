package org.javeriana.cm.donlimpio.rest.api.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("Personas Resource")
@RestController
@RequestMapping("/personas")
public class PersonasRestController extends AbstractController {

    @ApiOperation("This is a test resource")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World!";
    }
}
