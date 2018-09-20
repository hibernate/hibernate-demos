package it.redhat.demo.task;

import org.infinispan.Cache;
import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.encoding.DataConversion;

/**
 * Helps to use Infinispan transcoding feature.
 *
 * This feature, unlike the compatibility mode, allows to keep the caches in the original format
 * when we need to operate on them using a different {@link MediaType}.
 *
 * In our case the data are stored in Protocol Buffer format, but we operate on them using deserialized Java objects.
 *
 * @author Fabio Massimo Ercoli
 */
public class TranscodingHelper {

	public static <K, V> V getWithTranscoding(K key, Cache<K, V> cache) {
		Object protoValue = cache.get( keyToProto( key, cache ) );
		return valueToJava( cache, protoValue );
	}

	public static <K, V> void putWithTranscoding(K key, V value, Cache cache) {
		cache.put( keyToProto( key, cache ), valueToProto( value, cache ) );
	}

	private static <K, V> Object keyToProto(K key, Cache<K, V> cache) {
		DataConversion keyDataConversion = cache.getAdvancedCache().getKeyDataConversion();
		return keyDataConversion.convert( key, MediaType.APPLICATION_OBJECT, MediaType.APPLICATION_PROTOSTREAM );
	}

	private static <K, V> Object valueToProto(V value, Cache<K, V> cache) {
		DataConversion keyDataConversion = cache.getAdvancedCache().getValueDataConversion();
		return keyDataConversion.convert( value, MediaType.APPLICATION_OBJECT, MediaType.APPLICATION_PROTOSTREAM );
	}

	private static <K, V> V valueToJava(Cache<K, V> cache, Object protoValue) {
		DataConversion valueDataConversion = cache.getAdvancedCache().getValueDataConversion();
		return (V) valueDataConversion.convert( protoValue, MediaType.APPLICATION_PROTOSTREAM, MediaType.APPLICATION_OBJECT );
	}
}
