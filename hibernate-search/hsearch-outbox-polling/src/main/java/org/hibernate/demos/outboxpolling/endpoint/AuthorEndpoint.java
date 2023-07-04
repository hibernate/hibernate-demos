package org.hibernate.demos.outboxpolling.endpoint;

import org.hibernate.demos.outboxpolling.dto.AuthorInputDto;
import org.hibernate.demos.outboxpolling.dto.AuthorOutputDto;
import org.hibernate.demos.outboxpolling.model.Author;

import jakarta.ws.rs.Path;

@Path("/author")
public class AuthorEndpoint
		extends AbstractCrudEndpoint<Author, AuthorInputDto, AuthorOutputDto> {
	@Override
	protected Author newInstance() {
		return new Author();
	}
}
