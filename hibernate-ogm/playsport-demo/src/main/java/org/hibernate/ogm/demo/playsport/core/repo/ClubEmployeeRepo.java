package org.hibernate.ogm.demo.playsport.core.repo;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.ogm.demo.playsport.core.entity.ClubEmployee;
import org.hibernate.ogm.demo.playsport.core.entity.Club;

@Stateless
public class ClubEmployeeRepo {

    @Inject
    private EntityManager em;

    public ClubEmployee createEmployee(ClubEmployee clubEmployee, Club club) {

        club = em.merge( club );
        clubEmployee.setCompany(club);
        club.getEmployees().add( clubEmployee );
        em.persist( clubEmployee );

        return clubEmployee;

    }

    public List<ClubEmployee> findAll() {
        return em.createQuery("select c from ClubEmployee c", ClubEmployee.class).getResultList();
    }

}
