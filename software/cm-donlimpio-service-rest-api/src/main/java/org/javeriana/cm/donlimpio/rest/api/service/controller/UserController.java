package org.javeriana.cm.donlimpio.rest.api.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User Service")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "User authentication")
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public void login() {

    }
}
