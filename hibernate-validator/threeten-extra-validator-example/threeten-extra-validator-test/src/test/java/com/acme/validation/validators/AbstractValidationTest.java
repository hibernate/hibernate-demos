package com.acme.validation.validators;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

import org.junit.Before;

/**
 * @author Marko Bekhta
 */
public abstract class AbstractValidationTest {

	protected Validator validator;

	@Before
	public void setUp() {
		final Configuration<HibernateValidatorConfiguration> configuration = Validation.byProvider( HibernateValidator.class ).configure();
		validator = configuration.buildValidatorFactory().getValidator();
	}
}
