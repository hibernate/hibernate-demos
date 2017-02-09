package com.acme.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;

import org.threeten.extra.YearWeek;

/**
 * @author Marko Bekhta
 */
public class PastYearWeekValidator implements ConstraintValidator<Past, YearWeek> {

	@Override
	public void initialize(Past constraintAnnotation) {
	}

	public boolean isValid(YearWeek value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}
		return YearWeek.now().isAfter( value );
	}
}
