package com.acme.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;

import org.threeten.extra.YearQuarter;

/**
 * @author Marko Bekhta
 */
public class PastYearQuarterValidator implements ConstraintValidator<Past, YearQuarter> {

	@Override
	public void initialize(Past constraintAnnotation) {
	}

	public boolean isValid(YearQuarter value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}
		return YearQuarter.now().isAfter( value );
	}
}
