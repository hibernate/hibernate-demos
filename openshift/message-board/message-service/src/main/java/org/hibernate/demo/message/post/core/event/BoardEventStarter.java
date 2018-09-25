package org.hibernate.demo.message.post.core.event;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.ogm.datastore.infinispanremote.impl.InfinispanRemoteDatastoreProvider;
import org.hibernate.ogm.datastore.spi.DatastoreProvider;

import org.infinispan.client.hotrod.RemoteCache;

@Singleton
@Startup
public class BoardEventStarter {

	@Inject
	private EntityManagerFactory emf;

	@Inject
	private BoardEventListener boardEventListener;

	@PostConstruct
	public void registerBoardListener() {
		InfinispanRemoteDatastoreProvider provider = (InfinispanRemoteDatastoreProvider) emf.unwrap( SessionFactoryImplementor.class )
				.getServiceRegistry().getService( DatastoreProvider.class );

		RemoteCache<Object, Object> board = provider.getCache( "Board" );
		board.addClientListener( boardEventListener );
	}
}
