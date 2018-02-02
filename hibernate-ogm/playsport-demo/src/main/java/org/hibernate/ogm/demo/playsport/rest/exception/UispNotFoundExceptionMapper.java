package org.hibernate.ogm.demo.playsport.rest.exception;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

@Provider
public class UispNotFoundExceptionMapper implements ExceptionMapper<UispNotFoundException> {

    @Inject
    private Logger log;

    @Override
    public Response toResponse(UispNotFoundException exception) {

        return Response.status(404)
                .entity(new ExceptionDto(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();

    }
}
