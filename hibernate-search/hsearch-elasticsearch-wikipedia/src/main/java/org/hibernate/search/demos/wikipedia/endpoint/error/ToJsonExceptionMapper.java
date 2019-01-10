/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.search.demos.wikipedia.endpoint.error;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ToJsonExceptionMapper implements ExceptionMapper<Throwable> {
	@Override
	public Response toResponse(Throwable ex) {
		ErrorEntity errorEntity = new ErrorEntity();
		Response.Status status;
		if ( ex instanceof WebApplicationException ) {
			Response originalResponse = ( (WebApplicationException) ex ).getResponse();
			status = Response.Status.fromStatusCode( originalResponse.getStatus() );
		}
		else {
			status = Response.Status.INTERNAL_SERVER_ERROR;
		}

		errorEntity.setStatus( status );
		errorEntity.setMessage( ex.getMessage() );

		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace( new PrintWriter( errorStackTrace ) );
		errorEntity.setStackTrace( errorStackTrace.toString() );

		return Response.status( status )
				.entity( errorEntity )
				.type( MediaType.APPLICATION_JSON )
				.build();
	}
}
