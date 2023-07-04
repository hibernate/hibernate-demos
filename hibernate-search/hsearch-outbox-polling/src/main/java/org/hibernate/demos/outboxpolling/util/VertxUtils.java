package org.hibernate.demos.outboxpolling.util;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jboss.resteasy.reactive.RestResponse;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public final class VertxUtils {
	private VertxUtils() {
	}

	public static Handler<RoutingContext> chunkedHandler(String operationName, Supplier<CompletionStage<?>> operation) {
		return rc -> {
			var response = rc.response();
			response.setChunked( true );
			response.write( message( RestResponse.StatusCode.ACCEPTED, operationName + " started" ),
					ignored -> operation.get().whenComplete( (ignored2, throwable) -> {
						if ( throwable == null ) {
							response.end( message(
									RestResponse.StatusCode.OK,
									operationName + " succeeded"
							) );
						}
						else {
							response.end( message(
									RestResponse.StatusCode.INTERNAL_SERVER_ERROR,
									operationName + " failed:\n" + Arrays.stream( throwable.getStackTrace() )
											.map( Object::toString )
											.collect( Collectors.joining( "\n" ) )
							) );
						}
					} ) );
		};
	}

	private static String message(int code, String message) {
		return JsonObject.of( "code", code, "message", message ).toString() + "\n";
	}
}
