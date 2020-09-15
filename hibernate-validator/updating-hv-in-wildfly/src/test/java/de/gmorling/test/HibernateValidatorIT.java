/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package de.gmorling.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Email;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gunnar Morling
 */
@RunWith(Arquillian.class)
public class HibernateValidatorIT {

	@Inject
	private Validator validator;

	@Deployment
	public static WebArchive createTestArchive() throws Exception {
		return ShrinkWrap.create( WebArchive.class, "mywar.war" )
			.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" );
	}

	@Test
	public void canUseContainerElementValidation() {
		Set<ConstraintViolation<MyEntity>> violations = validator.validate( new MyEntity() );
		assertEquals( 1, violations.size() );
		assertEquals( "emails[0].<list element>", violations.iterator().next().getPropertyPath().toString() );
	}

	public class MyEntity {

		public List<@Email String> emails = Arrays.asList( "notanemail" );
	}
}
