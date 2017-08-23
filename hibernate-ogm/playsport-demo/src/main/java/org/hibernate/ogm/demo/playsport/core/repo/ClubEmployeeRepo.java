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

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.ogm.demo.playsport.core.entity.ClubEmployee;
import org.hibernate.ogm.demo.playsport.core.entity.Club;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
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

}
