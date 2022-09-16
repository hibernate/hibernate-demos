package org.hibernate.demos.quarkus;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.javacrumbs.jsonunit.core.Option;

@QuarkusTest
public class ClientTest {

	static {
		RestAssured.config = RestAssuredConfig.config()
				.objectMapperConfig( ObjectMapperConfig.objectMapperConfig()
						.defaultObjectMapperType( ObjectMapperType.GSON ) )
				.logConfig( LogConfig.logConfig()
						.enableLoggingOfRequestAndResponseIfValidationFails() );
	}

	@Test
	public void indexAndSearch() {
		given()
				.when()
				.queryParam( "terms", "acme" )
				.get( "/client/search" )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "[]" )
						.when( Option.IGNORING_EXTRA_FIELDS, Option.IGNORING_ARRAY_ORDER ) );
		given()
				.contentType( ContentType.JSON )
				.body( "{"
						+ "  \"name\":\"ACME Corporation\""
						+ "}" )
				.when().post( "/client/" )
				.then()
				.statusCode( 200 )
				.extract().response();
		given()
				.when()
				.queryParam( "terms", "acme" )
				.get( "/client/search" )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "[{\n" +
						"    \"name\": \"ACME Corporation\"\n" +
						"}]" )
						.when( Option.IGNORING_EXTRA_FIELDS, Option.IGNORING_ARRAY_ORDER ) );
	}

}