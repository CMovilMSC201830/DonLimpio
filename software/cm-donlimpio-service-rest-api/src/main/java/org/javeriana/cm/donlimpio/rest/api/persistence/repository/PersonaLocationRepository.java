package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PersonaLocation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonaLocationRepository extends CrudRepository<PersonaLocation, Long> {
    @Override
    <S extends PersonaLocation> S save(S s);

    @Override
    Optional<PersonaLocation> findById(Long id);

    @Override
    Iterable<PersonaLocation> findAll();

    @Override
    default boolean existsById(Long id) {
        return false;
    }

    @Override
    default long count() {
        return 0;
    }
}
