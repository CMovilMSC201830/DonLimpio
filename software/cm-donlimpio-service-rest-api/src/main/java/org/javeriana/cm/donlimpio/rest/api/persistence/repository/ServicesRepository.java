package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Services;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServicesRepository extends CrudRepository<Services, Long> {
    @Override
    <S extends Services> S save(S s);

    @Override
    Optional<Services> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    default long count() {
        return 0;
    }
}
