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
