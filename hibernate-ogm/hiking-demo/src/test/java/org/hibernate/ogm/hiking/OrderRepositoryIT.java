package org.hibernate.ogm.hiking;

import java.io.File;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.business.Customer;
import org.hibernate.ogm.hiking.model.business.Order;
import org.hibernate.ogm.hiking.repository.HikeRepository;
import org.hibernate.ogm.hiking.repository.business.OrderRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@Transactional
public class OrderRepositoryIT {

	private static final String WEBAPP_SRC = "src/main/webapp/";

	@Inject
	private OrderRepository orderRepository;

	@PersistenceContext(unitName="hike-PU-JTA")
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
			.create( WebArchive.class, OrderRepositoryIT.class.getSimpleName() + ".war" )
			.addPackage( Hike.class.getPackage() )
			.addPackage( Order.class.getPackage() )
			.addPackage( HikeRepository.class.getPackage() )
			.addPackage( OrderRepository.class.getPackage() )
			.addAsResource( "META-INF/persistence.xml" )
			.addAsWebInfResource( new File( WEBAPP_SRC + "WEB-INF/beans.xml" ) )
			.addAsResource( new StringAsset(
					"Dependencies: org.hibernate.ogm:main services, org.hibernate.ogm.mongodb:main services" ),
					"META-INF/MANIFEST.MF"
			);
	}

	@Test
	public void shouldPersistOrder() {
		Order order = new Order();
		order.customer = new Customer();
		order.customer.email = "jesuischarlie@hibernate.org";
		order.customer.name = "Charlie";
		order = orderRepository.createOrder(order);
		assertNotNull( order.number );
		assertNotNull( order.id );
		assertEquals( "Charlie", order.customer.name );

		//entityManager.flush();

		order = orderRepository.getOrderByNumber( order.number );

		assertNotNull( order );
		assertNotNull( order.customer );
	}
}
