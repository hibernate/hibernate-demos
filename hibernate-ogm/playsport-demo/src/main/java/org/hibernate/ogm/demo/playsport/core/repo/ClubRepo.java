package org.hibernate.ogm.demo.playsport.core.repo;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.apache.lucene.search.Query;
import org.hibernate.ogm.demo.playsport.core.entity.Athlete;
import org.hibernate.ogm.demo.playsport.core.entity.Club;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;

@Stateless
public class ClubRepo {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public List<Club> findByName(String name) {
        return findByField("name", name);
    }

    public List<Club> findByCode(String code) {
        return findByField("code", code);
    }

    public void add(@Valid Club athlete) {
        em.persist(athlete);
    }

    public List<Club> findAll() {
        return em.createQuery("select c from Club c", Club.class).getResultList();
    }

    public Club findById(String id) {
        return em.find(Club.class, id);
    }

    public Club getClubByIdWithAthletes(String clubId) {
        Club club = em.find(Club.class, clubId);

        // enable lazy loading on attached entity
        int size = club.getAthletes().size();

        if (log.isDebugEnabled()) {
            log.debug("Club \"{}\" has {} athletes!", club.getCode(), size);
        }
        return club;
    }

    private List<Club> findByField(String fieldName, String fieldValue) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Club.class).get();

        Query query = queryBuilder.keyword().onField(fieldName).matching(fieldValue).createQuery();
        return fullTextEntityManager.createFullTextQuery(query, Club.class).getResultList();
    }

    public boolean delete(String id) {
        Club athlete = findById(id);

        if (athlete == null) {
            return false;
        }

        em.remove(athlete);
        return true;
    }

}
