package org.hibernate.demo.message.post.core.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.demo.message.post.core.entity.Message;

import org.slf4j.Logger;

@Path("/health")
@Stateless
public class HealthCheck {

	@Inject
	private Logger log;

	@Inject
	private MessageService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> healthCheck() {
		log.info( "healthCheck invoked by OCP" );

		return service.findMessagesByUser( "andrea" );
	}

}
