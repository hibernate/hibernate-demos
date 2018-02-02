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

@Stateless
public class AthleteRepo {

    @Inject
    private EntityManager em;

    public void add(@Valid Athlete athlete) {
        em.persist(athlete);
    }

    public List<Athlete> findAll() {
        return em.createQuery("select c from Athlete c", Athlete.class).getResultList();
    }

    public Athlete findById(String id) {
        return em.find(Athlete.class, id);
    }

    public List<Athlete> findByUispCode(String uispCode) {
        return findByField("uispCode", uispCode);
    }

    private List<Athlete> findByField(String fieldName, String fieldValue) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Athlete.class).get();

        Query query = queryBuilder.keyword().onField(fieldName).matching(fieldValue).createQuery();
        return fullTextEntityManager.createFullTextQuery(query, Athlete.class).getResultList();
    }

    public Athlete createAthlete(Athlete athlete, Club club) {

        club = em.merge( club );
        athlete.setClub(club);
        club.getAthletes().add( athlete );
        em.persist( athlete );

        return athlete;
    }

    public Athlete addAthleteToClub(Athlete athlete, Club club) {

        club = em.merge(club);
        athlete = em.merge(athlete);

        athlete.setClub(club);
        club.getAthletes().add( athlete );

        return athlete;

    }

    public boolean delete(String id) {
        Athlete athlete = findById(id);

        if (athlete == null) {
            return false;
        }

        em.remove(athlete);
        return true;
    }

}
