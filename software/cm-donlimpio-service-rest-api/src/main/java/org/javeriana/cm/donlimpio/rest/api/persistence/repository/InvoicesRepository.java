package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Invoices;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InvoicesRepository extends CrudRepository<Invoices, Long> {
    @Override
    <S extends Invoices> S save(S s);

    @Override
    Optional<Invoices> findById(Long id);

    @Override
    Iterable<Invoices> findAll();

    @Override
    default boolean existsById(Long id) {
        return false;
    }

    @Override
    default long count() {
        return 0;
    }
}
