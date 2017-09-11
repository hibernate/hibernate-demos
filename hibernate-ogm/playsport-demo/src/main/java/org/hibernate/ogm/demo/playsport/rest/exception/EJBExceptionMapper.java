package org.hibernate.ogm.demo.playsport.rest.exception;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

    @Inject
    private Logger log;

    @Override
    public Response toResponse(EJBException exception) {

        Throwable cause = ExceptionUtils.getRootCause(exception);
        log.error(exception.getMessage(), exception);

        return Response.serverError()
                .entity(new ExceptionDto(cause))
                .type(MediaType.APPLICATION_JSON)
                .build();

    }

}
