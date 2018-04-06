package org.hibernate.demo.message.post.core.service.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

	@Override
	public Response toResponse(ResourceNotFoundException e) {
		return Response.status( 404 )
			.entity( new ExceptionMessage( 404, e.getClass().toString(), e.getMessage() ) )
			.type( MediaType.APPLICATION_JSON )
			.build();
	}

}
