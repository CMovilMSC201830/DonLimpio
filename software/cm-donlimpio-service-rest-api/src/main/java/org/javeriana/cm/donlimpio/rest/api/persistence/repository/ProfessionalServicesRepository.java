package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.exception.PersonaEntityNotFoundException;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ProfessionalServices;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionalServicesRepository extends CrudRepository<ProfessionalServices, Long> {
    @Override
    <S extends ProfessionalServices> S save(S s);

    <S extends ProfessionalServices> ProfessionalServices saveWithUUID(ProfessionalServices services, String uuid) throws PersonaEntityNotFoundException;

    @Override
    Optional<ProfessionalServices> findById(Long pk);

    List<ProfessionalServices> findAllByUUID(String uuid);

    List<ProfessionalServices> findAllByCategoryId(int categoryId);

    @Override
    boolean existsById(Long pk);

    @Override
    default long count() {
        return 0;
    }
}
