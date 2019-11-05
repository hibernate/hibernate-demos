package org.hibernate.demos.quarkus;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.demos.quarkus.domain.Client;
import org.hibernate.demos.quarkus.domain.BusinessManager;
import org.hibernate.demos.quarkus.dto.BusinessManagerCreateUpdateDto;
import org.hibernate.demos.quarkus.dto.ClientCreateUpdateDto;
import org.hibernate.demos.quarkus.dto.ClientMapper;
import org.hibernate.demos.quarkus.dto.ClientRetrieveDto;
import org.hibernate.demos.quarkus.dto.BusinessManagerRetrieveDto;
import org.hibernate.search.engine.search.common.BooleanOperator;
import org.hibernate.search.mapper.orm.Search;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

@Path("/")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

	@Inject
	ClientMapper mapper;

	@Inject
	EntityManagerFactory entityManagerFactory;

	@PUT
	@Path("/client")
	public ClientRetrieveDto createClient(ClientCreateUpdateDto dto) {
		Client client = new Client();
		mapper.fromDto( client, dto );
		client.persist();
		return mapper.toDto( client );
	}

	@GET
	@Path("/client/{id}")
	public ClientRetrieveDto retrieveClient(@PathParam("id") Long id) {
		Client client = findClient( id );
		return mapper.toDto( client );
	}

	@POST
	@Path("/client/{id}")
	public void updateClient(@PathParam("id") Long id, ClientCreateUpdateDto dto) {
		Client client = findClient( id );
		mapper.fromDto( client, dto );
	}

	@DELETE
	@Path("/client/{id}")
	public void deleteClient(@PathParam("id") Long id) {
		findClient( id ).delete();
	}

	@PUT
	@Path("/manager")
	public BusinessManagerRetrieveDto createBusinessManager(BusinessManagerCreateUpdateDto dto) {
		BusinessManager businessManager = new BusinessManager();
		mapper.fromDto( businessManager, dto );
		businessManager.persist();
		return mapper.toDto( businessManager );
	}

	@POST
	@Path("/manager/{id}")
	public void updateBusinessManager(@PathParam("id") Long id, BusinessManagerCreateUpdateDto dto) {
		BusinessManager businessManager = findBusinessManager( id );
		mapper.fromDto( businessManager, dto );
	}

	@DELETE
	@Path("/manager/{id}")
	public void deleteBusinessManager(@PathParam("id") Long id) {
		findBusinessManager( id ).delete();
	}

	@POST
	@Path("/client/{clientId}/manager/{managerId}")
	public void assignBusinessManager(@PathParam("clientId") Long clientId, @PathParam("managerId") Long managerId) {
		unAssignBusinessManager( clientId );
		Client client = findClient( clientId );
		BusinessManager manager = findBusinessManager( managerId );
		manager.assignedClients.add( client );
		client.assignedManager = manager;
	}

	@DELETE
	@Path("/client/{clientId}/manager")
	public void unAssignBusinessManager(@PathParam("clientId") Long clientId) {
		Client client = findClient( clientId );
		BusinessManager previousManager = client.assignedManager;
		if ( previousManager != null ) {
			previousManager.assignedClients.remove( client );
			client.assignedManager = null;
		}
	}

	@POST
	@Path("/client/reindex")
	@Transactional(TxType.NEVER)
	public void reindex() throws InterruptedException {
		Search.mapping( entityManagerFactory )
				.scope( Client.class )
				.massIndexer()
				.startAndWait();
	}

	@Transactional(TxType.NEVER)
	void reindexOnStart(@Observes StartupEvent event) throws InterruptedException {
		if ( "dev".equals( ProfileManager.getActiveProfile() ) ) {
			reindex();
		}
	}

	@GET
	@Path("/client/search")
	public List<ClientRetrieveDto> search(@QueryParam("terms") String terms) {
		List<Client> result = Search.session( Panache.getEntityManager() )
				.search( Client.class )
				.predicate( f -> f.simpleQueryString()
						.fields( "name", "assignedManager.name" )
						.matching( terms )
						.defaultOperator( BooleanOperator.AND )
				)
				.fetchHits( 20 );

		return result.stream().map( mapper::toDto ).collect( Collectors.toList() );
	}

	private Client findClient(Long id) {
		Client found = Client.findById( id );
		if ( found == null ) {
			throw new NotFoundException();
		}
		return found;
	}

	private BusinessManager findBusinessManager(Long id) {
		BusinessManager found = BusinessManager.findById( id );
		if ( found == null ) {
			throw new NotFoundException();
		}
		return found;
	}
}