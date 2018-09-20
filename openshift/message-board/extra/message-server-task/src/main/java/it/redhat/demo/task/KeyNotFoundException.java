package it.redhat.demo.task;

import org.infinispan.Cache;

public class KeyNotFoundException extends RuntimeException {

	public KeyNotFoundException(Object entryKey, Cache cache) {
		super( getMessage( entryKey, cache ) );
	}

	private static String getMessage(Object entryKey, Cache cache) {
		StringBuilder builder = new StringBuilder( "Entry with key[" );
		builder.append( entryKey );
		builder.append( "] NOT FOUND on cache[" );
		builder.append( cache.getName() );
		builder.append( "]" );
		builder.append( ". Cache size: " );
		builder.append( cache.size() );
		builder.append( ". Cache content: " );

		cache.forEach( (key, value) -> {
			builder.append( key );
			builder.append( ":" );
			builder.append( key.getClass() );
			builder.append( " - " );
			builder.append( value );
			builder.append( ":" );
			builder.append( value.getClass() );
			builder.append( " . " );
		} );
		return builder.toString();
	}
}
