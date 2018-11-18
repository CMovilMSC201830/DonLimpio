package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Categories;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

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
}
