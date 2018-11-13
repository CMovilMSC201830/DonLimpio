package org.javeriana.cm.donlimpio.rest.api.persistence.repository;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PaymentMethod;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentMethodsRepository extends CrudRepository<PaymentMethod, Integer> {
    @Override
    <S extends PaymentMethod> S save(S s);

    @Override
    Optional<PaymentMethod> findById(Integer id);

    @Override
    Iterable<PaymentMethod> findAll();

    @Override
    boolean existsById(Integer id);

    @Override
    default long count() {
        return 0;
    }
}
