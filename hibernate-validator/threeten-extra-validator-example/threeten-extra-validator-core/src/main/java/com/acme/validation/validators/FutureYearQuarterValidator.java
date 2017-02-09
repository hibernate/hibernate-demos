package com.acme.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;

import org.threeten.extra.YearQuarter;

/**
 * @author Marko Bekhta
 */
public class FutureYearQuarterValidator implements ConstraintValidator<Future, YearQuarter> {

	@Override
	public void initialize(Future constraintAnnotation) {
	}

	public boolean isValid(YearQuarter value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}
		return YearQuarter.now().isBefore( value );
	}
}
