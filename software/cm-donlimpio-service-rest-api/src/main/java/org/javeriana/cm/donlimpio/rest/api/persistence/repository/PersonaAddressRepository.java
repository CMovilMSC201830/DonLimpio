package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PersonaAddresses;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonaAddressRepository extends CrudRepository<PersonaAddresses, Integer> {
    @Override
    <S extends PersonaAddresses> S save(S s);

    @Override
    Optional<PersonaAddresses> findById(Integer id);

    @Override
    boolean existsById(Integer id);

    @Override
    default long count() {
        return 0;
    }
}
