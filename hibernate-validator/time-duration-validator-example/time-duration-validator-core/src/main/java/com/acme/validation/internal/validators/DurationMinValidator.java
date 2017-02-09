package com.acme.validation.internal.validators;

import java.time.Duration;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.acme.validation.constraints.DurationMin;

/**
 * @author Marko Bekhta
 */
public class DurationMinValidator implements ConstraintValidator<DurationMin, Duration> {

	private Duration duration;

	@Override
	public void initialize(DurationMin constraintAnnotation) {
		this.duration = Duration.of( constraintAnnotation.value(), constraintAnnotation.units() );
	}

	public boolean isValid(Duration value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}
		return duration.compareTo( value ) < 0;
	}
}
