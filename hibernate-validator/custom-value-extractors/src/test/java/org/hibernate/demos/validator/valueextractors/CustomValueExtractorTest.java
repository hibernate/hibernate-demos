/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.validator.valueextractors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * @author Gunnar Morling
 */
public class CustomValueExtractorTest {

	public class Customer {
		private final Multimap<@NotBlank String, @NotBlank @Email String> emailsByType = HashMultimap.create();
	}

	@Test
	public void shouldUseCustomValueExtractoe() {
		Customer bean = new Customer();
		bean.emailsByType.put( "work", "bob@example.com" );
		bean.emailsByType.put( "work", "not-an-email" );

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Set<ConstraintViolation<Customer>> violations = validator.validate (bean );
		assertEquals( 1, violations.size() );

		ConstraintViolation<Customer> violation = violations.iterator().next();
		assertEquals( "not-an-email", violation.getInvalidValue() );
		assertEquals( Email.class, violation.getConstraintDescriptor().getAnnotation().annotationType() );

		Iterator<Node> pathNodes = violation.getPropertyPath().iterator();
		assertTrue( pathNodes.hasNext() );

		Node node = pathNodes.next();
		assertEquals( "emailsByType", node.getName() );
		assertEquals( ElementKind.PROPERTY, node.getKind() );

		assertTrue( pathNodes.hasNext() );
		node = pathNodes.next();
		assertEquals( "<multimap value>", node.getName() );
		assertEquals( ElementKind.CONTAINER_ELEMENT, node.getKind() );
		assertEquals( "work", node.getKey() );

		assertFalse( pathNodes.hasNext() );
	}
}
