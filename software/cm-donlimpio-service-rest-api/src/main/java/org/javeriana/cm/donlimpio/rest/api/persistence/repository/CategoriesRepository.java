package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Categories;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.DocumentTypes;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoriesRepository extends CrudRepository<Categories, Integer> {
    @Override
    default <S extends Categories> S save(S s) {
        return null;
    }

    @Override
    Optional<Categories> findById(Integer id);

    @Override
    Iterable<Categories> findAll();

    @Override
    default boolean existsById(Integer id) {
        return false;
    }

    @Override
    default long count() {
        return 0;
    }
}
