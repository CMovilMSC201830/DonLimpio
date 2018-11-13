package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.DocumentTypes;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Persona;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DocumentTypesRepository extends CrudRepository<DocumentTypes, Integer> {
    @Override
    default <S extends DocumentTypes> S save(S s) {
        return null;
    }

    @Override
    default Optional<DocumentTypes> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    Iterable<DocumentTypes> findAll();

    @Override
    default boolean existsById(Integer id) {
        return false;
    }

    @Override
    default long count() {
        return 0;
    }
}
