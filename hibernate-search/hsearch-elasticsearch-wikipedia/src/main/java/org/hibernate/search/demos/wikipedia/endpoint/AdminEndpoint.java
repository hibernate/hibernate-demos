package org.hibernate.search.demos.wikipedia.endpoint;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import org.hibernate.CacheMode;
import org.hibernate.search.demos.wikipedia.data.Page;
import org.hibernate.search.demos.wikipedia.data.User;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Path("/admin")
@Transactional(readOnly = true)
public class AdminEndpoint {
	
	@PersistenceContext
	private EntityManager em;

	@POST
	@Path("/reindex")
	public Response reindex(@QueryParam("limit") Long limit,
			@QueryParam("idfetchsize") Integer idfetchsize,
			@QueryParam("fetchsize") Integer fetchsize,
			@QueryParam("threads") Integer threads) {
		SearchSession searchSession = Search.session( em );

		MassIndexer indexer = searchSession.massIndexer( Page.class, User.class )
				.dropAndCreateSchemaOnStart( true )
				.typesToIndexInParallel( 2 )
				.batchSizeToLoadObjects( fetchsize == null ? 200 : fetchsize )
				.idFetchSize( idfetchsize == null ? 200 : idfetchsize )
				.threadsToLoadObjects( threads == null ? 5 :threads )
				.cacheMode( CacheMode.IGNORE ); // Cache is likely to do more harm than good in our case (very few relations)
		if ( limit != null ) {
			indexer.limitIndexedObjectsTo( limit );
		}

		indexer.start();

		return Response.accepted().build();
	}

}
