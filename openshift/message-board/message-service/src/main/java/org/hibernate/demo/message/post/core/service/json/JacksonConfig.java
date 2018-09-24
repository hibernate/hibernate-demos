package org.hibernate.demo.message.post.core.service.json;

import java.text.SimpleDateFormat;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides a custom Jackson {@link ObjectMapper}.
 *
 * This class could be used only if 'resteasy-jackson2-provider' module is enabled.
 * see jboss-deployment-structure.xml
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper> {

	private static final String DATE_FORMAT = "dd-MM-yy HH:mm:ss";

	@javax.enterprise.inject.Produces
	private ObjectMapper objectMapper;

	public JacksonConfig() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.setDateFormat( new SimpleDateFormat( DATE_FORMAT ) );
	}

	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}
}
