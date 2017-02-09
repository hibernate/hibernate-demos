package com.acme.validation.validators;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

import org.junit.Assert;
import org.junit.Test;

import org.threeten.extra.Quarter;
import org.threeten.extra.YearQuarter;

/**
 * @author Marko Bekhta
 */
public class YearQuarterTest extends AbstractValidationTest {

	@Test
	public void testPast() {
		Assert.assertTrue( validator.validate( new PastEvent( YearQuarter.of( YearQuarter.now().getYear() - 1, Quarter.Q1 ) ) ).isEmpty() );
		Assert.assertEquals( validator.validate( new PastEvent( YearQuarter.of( YearQuarter.now().getYear() + 1, Quarter.Q1 ) ) ).size(), 1 );
	}

	@Test
	public void testFuture() {
		Assert.assertTrue( validator.validate( new FutureEvent( YearQuarter.of( YearQuarter.now().getYear() + 1, Quarter.Q1 ) ) ).isEmpty() );
		Assert.assertEquals( validator.validate( new FutureEvent( YearQuarter.of( YearQuarter.now().getYear() - 1, Quarter.Q1 ) ) ).size(), 1 );
	}

	public static class PastEvent {

		@Past
		private YearQuarter quarter;

		public PastEvent(YearQuarter quarter) {
			this.quarter = quarter;
		}
	}

	public static class FutureEvent {

		@Future
		private YearQuarter quarter;

		public FutureEvent(YearQuarter quarter) {
			this.quarter = quarter;
		}
	}

}
