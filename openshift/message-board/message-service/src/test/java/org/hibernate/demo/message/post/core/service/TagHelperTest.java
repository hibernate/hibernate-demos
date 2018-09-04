package org.hibernate.demo.message.post.core.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TagHelperTest {

	@Test
	public void testTagExtractionFrom() {
		List<String> tags = TagHelper.readTagFromBody( "I'm #Fabio. How are you? #nice" );

		assertEquals( 2, tags.size() );
		assertEquals( "#Fabio", tags.get( 0 ) );
		assertEquals( "#nice", tags.get( 1 ) );
	}
}
