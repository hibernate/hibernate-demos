package org.hibernate.ogm.hiking;

import java.io.File;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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

@RunWith(Arquillian.class)
@Transactional
public class BeanValidationRepositoryIT {

	private static final String WEBAPP_SRC = "src/main/webapp/";

	@Inject
	private Validator validator;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
			.create( WebArchive.class, BeanValidationRepositoryIT.class.getSimpleName() + ".war" )
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
	public void shouldValidate() {
		Order order = new Order();
		order.number = "cdscds";
		order.customer = new Customer();
		order.customer.email = "not an email";
		order.customer.name = "Charlie";

		Set<ConstraintViolation<Order>> violations = validator.validate( order );
		violations.forEach( System.out::println );
		assertEquals( 2, violations.size() );

	}
}
