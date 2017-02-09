package com.acme.validation.validators;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

import org.junit.Assert;
import org.junit.Test;

import org.threeten.extra.YearWeek;

/**
 * @author Marko Bekhta
 */
public class YearWeekTest extends AbstractValidationTest {

	@Test
	public void testPast() {
		Assert.assertTrue( validator.validate( new PastEvent( YearWeek.of( YearWeek.now().getYear() - 1, 1 ) ) ).isEmpty() );
		Assert.assertEquals( validator.validate( new PastEvent( YearWeek.of( YearWeek.now().getYear() + 1, 1 ) ) ).size(), 1 );
	}

	@Test
	public void testFuture() {
		Assert.assertTrue( validator.validate( new FutureEvent( YearWeek.of( YearWeek.now().getYear() + 1, 1 ) ) ).isEmpty() );
		Assert.assertEquals( validator.validate( new FutureEvent( YearWeek.of( YearWeek.now().getYear() - 1, 1 ) ) ).size(), 1 );
	}

	public static class PastEvent {

		@Past
		private YearWeek yearWeek;

		public PastEvent(YearWeek yearWeek) {
			this.yearWeek = yearWeek;
		}
	}

	public static class FutureEvent {

		@Future
		private YearWeek yearWeek;

		public FutureEvent(YearWeek yearWeek) {
			this.yearWeek = yearWeek;
		}
	}

}
