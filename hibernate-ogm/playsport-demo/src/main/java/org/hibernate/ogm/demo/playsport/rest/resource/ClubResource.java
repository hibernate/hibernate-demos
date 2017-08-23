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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.ogm.demo.playsport.core.entity.Club;
import org.hibernate.ogm.demo.playsport.core.repo.ClubRepo;
import org.hibernate.ogm.demo.playsport.rest.exception.UispNotFoundException;
import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */

@Path("club")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClubResource {

    @Inject
    private Logger log;

    @Inject
    private ClubRepo clubRepo;

    @POST
    public Club create(Club club) {

        clubRepo.add(club);
        log.info("Create club {}", club.getId());

        return club;

    }

    @GET
    public List<Club> findAll() {

        return clubRepo.findAll();

    }


    @Path("{id}")
    @GET
    public Club findById(@PathParam("id") String id) {

        return clubRepo.findById(id);

    }

    @Path("name/{name}")
    @GET
    public List<Club> findByName(@PathParam("name") String name) {

        return clubRepo.findByName(name);

    }

    @Path("code/{code}")
    @GET
    public List<Club> findByCode(@PathParam("code") String code) {

        return clubRepo.findByCode(code);

    }

    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") String id) throws UispNotFoundException {

        boolean delete = clubRepo.delete(id);
        if (!delete) {
            throw new  UispNotFoundException(Club.class, "id", id);
        }

    }

}
