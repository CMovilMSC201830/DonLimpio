package org.javeriana.cm.donlimpio.rest.api.service.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.javeriana.cm.donlimpio.rest.api.persistence.controller.CategoriesController;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesRestController extends AbstractController {

    @Autowired
    private CategoriesController servicesController;

    @ApiOperation("Returns all possible categories for a service")
    @RequestMapping(value = "/search/all", method = RequestMethod.GET)
    public List<Categories> findCategories() {
        return servicesController.findAllCategories();
    }

    @ApiOperation("Returns a category by id")
    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    public Categories findCategoriesById(@PathVariable int id) {
        return servicesController.findCategoriesById(id).get();
    }
}
