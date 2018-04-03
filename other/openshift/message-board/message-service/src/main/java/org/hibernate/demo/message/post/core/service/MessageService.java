package org.hibernate.demo.message.post.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.demo.message.post.core.entity.Board;
import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.repo.BoardRepo;
import org.hibernate.demo.message.post.core.repo.MessageRepo;
import org.hibernate.demo.message.post.core.repo.TagRepo;
import org.hibernate.demo.message.post.core.service.exception.ResourceNotFoundException;

import org.slf4j.Logger;

@Path( "/messages" )
@Stateless
public class MessageService {

	@Inject
	private MessageRepo messages;

	@Inject
	private BoardRepo boards;

	@Inject
	private TagRepo tags;

	@Inject
	private Logger log;

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<Message> findMessagesByUser( @QueryParam( "username" ) String username ) {

		Board board = boards.find( username );
		if (board == null) {
			return new ArrayList<>();
		}

		log.info( "Load messages from board '{}'", username );
		log.info( "Board {}", board );

		return board.getMessages();

	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	public void addMessage( @Valid Message message ) {

		log.info( "Pusing message '{}' to the board '{}'", message.getBody(), message.getUsername()  );

		messages.add( message );
		Board board = boards.find( message.getUsername() );

		if ( board == null ) {
			log.info( "Board Not Exist. Creating a new one.", message.getUsername() );

			board = new Board( message );
			boards.add( board );

			log.info( "Board After Push {}", board );
			return;
		}

		log.info( "Board Before Push {}", board );

		board.pushMessage( message );
		boards.update( board );

		log.info( "Board After Push {}", board );

	}

	@Path( "{id}" )
	@DELETE
	public void deleteMessage( @PathParam( "id" ) Long id ) throws ResourceNotFoundException {

		Message message = messages.findById( id );
		if (message == null) {
			throw new ResourceNotFoundException( "message", id );
		}

		Board board = boards.find( message.getUsername() );

		log.info( "Popping message '{}' out from board '{}'", message.getBody(), message.getUsername() );
		log.info( "Board [Before Pop] {}", board );

		board.popMessage( message );
		if ( board.isEmpty() ) {
			boards.delete( board );
		} else {
			boards.update( board );
		}

		log.info( "Board [After Pop] {}", board );
		messages.remove( message );

	}


}
