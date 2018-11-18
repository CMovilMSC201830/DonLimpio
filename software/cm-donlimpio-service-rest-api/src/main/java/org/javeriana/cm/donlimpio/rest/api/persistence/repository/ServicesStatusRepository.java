package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ServicesStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServicesStatusRepository extends CrudRepository<ServicesStatus, Integer> {
    @Override
    <S extends ServicesStatus> S save(S s);

    @Override
    Optional<ServicesStatus> findById(Integer pk);

    @Override
    boolean existsById(Integer pk);

    @Override
    default long count() {
        return 0;
    }
}
