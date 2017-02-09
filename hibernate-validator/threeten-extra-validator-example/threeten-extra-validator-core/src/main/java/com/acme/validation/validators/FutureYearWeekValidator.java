package com.acme.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;

import org.threeten.extra.YearWeek;

/**
 * @author Marko Bekhta
 */
public class FutureYearWeekValidator implements ConstraintValidator<Future, YearWeek> {

	@Override
	public void initialize(Future constraintAnnotation) {
	}

	public boolean isValid(YearWeek value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}
		return YearWeek.now().isBefore( value );
	}
}
