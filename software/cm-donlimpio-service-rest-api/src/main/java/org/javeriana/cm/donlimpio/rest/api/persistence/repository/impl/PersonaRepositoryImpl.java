package org.javeriana.cm.donlimpio.rest.api.persistence.repository.impl;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Persona;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.PersonaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersonaRepositoryImpl implements PersonaRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public <S extends Persona> S save(S s) {
        return em.merge(s);
    }

    @Override
    public <S extends Persona> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Iterable<Persona> findAll() {
        return null;
    }

    @Override
    public Iterable<Persona> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public List<Persona> findByUUID(String uuid) {
        Query query = em.createQuery("SELECT p FROM Persona p WHERE uuid = :uuid");
        query.setParameter("uuid", uuid);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Persona persona) {

    }

    @Override
    public void deleteAll(Iterable<? extends Persona> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
