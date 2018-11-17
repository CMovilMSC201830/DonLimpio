package org.javeriana.cm.donlimpio.rest.api.persistence.repository.impl;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.PaymentMethod;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Persona;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Services;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.ServicesRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ServicesRepositoryImpl implements ServicesRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public <S extends Services> S save(S s) {
        Persona persona = em.merge(s.getPersona());
        s.getPersonaAddress().setPersonaId(persona.getId());
        em.merge(s.getPersonaAddress());
        s.getInvoice().getPaymentMethod().setPersona_doc_id(persona.getId());
        PaymentMethod paymentMethod = em.merge(s.getInvoice().getPaymentMethod());
        s.getInvoice().setPaymentMethod(paymentMethod);
        return em.merge(s);
    }

    @Override
    public int updateStatusById(int serviceId, int status) {
        String update = "UPDATE Services s SET status = :status WHERE id = :serviceId";
        Query query = em.createQuery(update);
        query.setParameter("status", status);
        query.setParameter("serviceId", serviceId);
        return query.executeUpdate();
    }

    @Override
    public <S extends Services> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Services> findById(Integer id) {
        String str = "SELECT s FROM Services s WHERE id = :id";
        Query query = em.createQuery(str);
        query.setParameter("id", id);
        List results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return Optional.of((Services) results.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Integer pk) {
        return false;
    }

    @Override
    public Iterable<Services> findAll() {
        return null;
    }

    @Override
    public Iterable<Services> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public List<Services> findAllByPersona(long personaId) {
        String str = "SELECT s FROM Services s WHERE s.persona.id = :personaId";
        Query query = em.createQuery(str);
        query.setParameter("personaId", personaId);
        return query.getResultList();
    }

    @Override
    public void deleteById(Integer pk) {

    }

    @Override
    public void delete(Services services) {

    }

    @Override
    public void deleteAll(Iterable<? extends Services> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
