package org.hibernate.demo.message.account.core.service.exception;

import javax.ejb.EJBException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EjbExceptionMapper implements ExceptionMapper<EJBException> {

	@Override
	public Response toResponse(EJBException e) {

		Throwable cause = getRootException( e );

		return Response.status( 500 )
			.entity( new ExceptionMessage( 234, cause.getClass().toString(), cause.getMessage() ) )
			.type( MediaType.APPLICATION_JSON )
			.build();
	}

	public static Throwable getRootException(Throwable exception){
		Throwable rootException=exception;
		while(rootException.getCause()!=null){
			rootException = rootException.getCause();
		}
		return rootException;
	}

}
