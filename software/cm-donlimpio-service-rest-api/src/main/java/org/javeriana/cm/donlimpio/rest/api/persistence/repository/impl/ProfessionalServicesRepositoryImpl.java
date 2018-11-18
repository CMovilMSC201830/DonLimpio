package org.javeriana.cm.donlimpio.rest.api.persistence.repository.impl;

import org.javeriana.cm.donlimpio.rest.api.exception.PersonaEntityNotFoundException;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Persona;
import org.javeriana.cm.donlimpio.rest.api.persistence.entity.ProfessionalServices;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.ProfessionalServicesRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProfessionalServicesRepositoryImpl implements ProfessionalServicesRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ProfessionalServices saveWithUUID(ProfessionalServices services, String uuid) throws PersonaEntityNotFoundException {
        Query query = em.createQuery("SELECT p FROM Persona p WHERE uuid = :uuid");
        query.setParameter("uuid", uuid);
        List<Persona> listPersonas = query.getResultList();
        Persona persona = (listPersonas != null && !listPersonas.isEmpty()) ? listPersonas.get(0) : null;
        if (persona == null) {
            throw new PersonaEntityNotFoundException("Persona not found by UUID");
        }
        services.setPersonaId(persona.getId());
        return em.merge(services);
    }

    @Override
    public List<ProfessionalServices> findAllByUUID(String uuid) {
        String str = "SELECT ps " +
                     "FROM ProfessionalServices ps " +
                     "INNER JOIN Persona p ON ps.personaId = p.id WHERE p.uuid = :uuid";
        Query query = em.createQuery(str);
        query.setParameter("uuid", uuid);
        return query.getResultList();
    }

    @Override
    public List<ProfessionalServices> findAllByCategoryId(int categoryId) {
        String str = "SELECT ps " +
                     "FROM ProfessionalServices ps " +
                     "WHERE categoryId = :categoryId";
        Query query = em.createQuery(str);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    @Override
    public <S extends ProfessionalServices> S save(S s) {
        return null;
    }

    @Override
    public <S extends ProfessionalServices> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<ProfessionalServices> findById(Long pk) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long pk) {
        return false;
    }

    @Override
    public Iterable<ProfessionalServices> findAll() {
        return null;
    }

    @Override
    public Iterable<ProfessionalServices> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(ProfessionalServices professionalServices) {

    }

    @Override
    public void deleteAll(Iterable<? extends ProfessionalServices> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
