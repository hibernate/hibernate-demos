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
package org.hibernate.ogm.demo.playsport.rest.resource;

import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.ogm.demo.playsport.core.entity.Athlete;
import org.hibernate.ogm.demo.playsport.core.repo.AthleteRepo;
import org.hibernate.ogm.demo.playsport.core.repo.ClubRepo;
import org.hibernate.ogm.demo.playsport.rest.exception.UispNotFoundException;
import org.hibernate.ogm.demo.playsport.core.entity.Club;
import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */

@Path("athlete")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AthleteResource {

    @Inject
    private Logger log;

    @Inject
    private ClubRepo clubRepo;

    @Inject
    private AthleteRepo athleteRepo;

    @POST
    public Athlete create(@Valid Athlete athlete) {

        athleteRepo.add(athlete);
        log.info("Create athlete {}", athlete.getId());

        return athlete;

    }

    @GET
    public List<Athlete> findAll() {

        return athleteRepo.findAll();

    }

    @Path("{id}")
    @GET
    public Athlete findById(@PathParam("id") String id) {

        return athleteRepo.findById(id);

    }

    @Path("uispCode/{uispCode}")
    @GET
    public List<Athlete> findByUispCode(@PathParam("uispCode") String uispCode) {

        return athleteRepo.findByUispCode(uispCode);

    }

    @Path("uispCode/{uispCode}/clubCode/{clubCode}")
    @PUT
    public Athlete joinClub(@PathParam("uispCode") String uispCode, @PathParam("clubCode") String clubCode) throws UispNotFoundException {

        List<Athlete> athleteList = athleteRepo.findByUispCode(uispCode);
        if (athleteList.isEmpty()) {
            throw new  UispNotFoundException(Athlete.class, "uispCode", uispCode);
        }

        List<Club> clubList = clubRepo.findByCode(clubCode);
        if (clubList.isEmpty()) {
            throw new  UispNotFoundException(Club.class, "code", clubCode);
        }

        Athlete athlete = athleteList.get(0);
        Club club = clubList.get(0);

        athlete = athleteRepo.addAthleteToClub(athlete, club);

        return athlete;

    }

    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") String id) throws UispNotFoundException {

        boolean delete = athleteRepo.delete(id);
        if (!delete) {
            throw new UispNotFoundException(Athlete.class, "id", id);
        }

    }

}
