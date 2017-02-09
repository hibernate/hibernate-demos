package com.acme.validation.validators;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.Test;

import com.acme.validation.constraints.DurationMin;

/**
 * @author Marko Bekhta
 */
public class DurationMinTest extends AbstractValidationTest {

	@Test
	public void testPast() {
		Assert.assertTrue( validator.validate( new Task( "write blog post", Duration.ofHours( 4 ) ) ).isEmpty() );
		Assert.assertEquals( validator.validate( new Task( "write blog post", Duration.ofHours( 1 ) ) ).size(), 1 );
	}

	public static class Task {

		private String taskName;
		@DurationMin(value = 2, units = ChronoUnit.HOURS)
		private Duration timeSpent;

		public Task(String taskName, Duration timeSpent) {
			this.taskName = taskName;
			this.timeSpent = timeSpent;
		}
	}

}
