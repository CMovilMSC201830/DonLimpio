package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.exception.PersonaEntityNotFoundException;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Services;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ServicesRepository extends CrudRepository<Services, Integer> {
    @Override
    <S extends Services> S save(S s);

    @Override
    Optional<Services> findById(Integer pk);

    @Override
    boolean existsById(Integer pk);

    @Override
    default long count() {
        return 0;
    }

    List<Services> findAllByPersona(long personaId);

    List<Services> findAllByUUID(String uuid);

    Services saveWithUUID(Services services) throws PersonaEntityNotFoundException;

    int updateStatusById(int serviceId, int status);
}
