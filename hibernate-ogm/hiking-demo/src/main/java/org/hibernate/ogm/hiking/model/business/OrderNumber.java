package org.hibernate.ogm.hiking.model.business;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Emmanuel Bernard emmanuel@hibernate.org
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(OrderNumber.List.class)
@Constraint( validatedBy = OrderNumber.OrderNumberValidator.class )
public @interface OrderNumber {
	String message() default "Invalid order number, should be a UUID 8-4-4-4-12";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@interface List {
		OrderNumber[] value();

	}

	class OrderNumberValidator implements ConstraintValidator<OrderNumber, String> {


		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			if ( value == null ) return true;
			if ( value.length() != 8+1+4+1+4+1+4+1+12 ) return false;
			if (value.charAt( 8 ) != '-' || value.charAt( 8+1+4 ) != '-') return false;
			return true;
		}
	}
}
