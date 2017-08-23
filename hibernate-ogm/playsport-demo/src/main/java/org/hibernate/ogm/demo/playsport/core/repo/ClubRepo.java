/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.hibernate.ogm.demo.playsport.core.repo;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.apache.lucene.search.Query;
import org.hibernate.ogm.demo.playsport.core.entity.Club;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
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

        log.info("Club \"{}\" has {} athletes!", club.getCode(), club.getAthletes().size());
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
