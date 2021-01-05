package org.hibernate.demos.hsearchfeatureexamples;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@QuarkusTest
public class FashionCollectionServiceTest {

	static {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	public void create_retrieve_update() {
		ExtractableResponse<Response> createResponse = given()
				.contentType( ContentType.JSON )
				.body( "{"
						+ "  \"season\": \"SPRING_SUMMER\","
						+ "  \"year\": 2021,"
						+ "  \"keywords\": \"my keywords\""
						+ "}" )
				.when().put( "/collection/" )
				.then()
				.statusCode( 200 )
				.extract();

		long id = createResponse.body().as( JsonObject.class ).get( "id" ).getAsLong();

		given()
				.when().get( "/collection/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "{"
						+ "  \"id\": " + id + ","
						+ "  \"season\": \"SPRING_SUMMER\","
						+ "  \"year\": 2021,"
						+ "  \"keywords\": \"my keywords\""
						+ "}" ) );

		given()
				.contentType( ContentType.JSON )
				.body( "{"
						+ "  \"season\": \"SPRING_SUMMER\","
						+ "  \"year\": 2021,"
						+ "  \"keywords\": \"my keywords and some more\""
						+ "}" )
				.when().post( "/collection/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "{"
						+ "  \"id\": " + id + ","
						+ "  \"season\": \"SPRING_SUMMER\","
						+ "  \"year\": 2021,"
						+ "  \"keywords\": \"my keywords and some more\""
						+ "}" ) );

		given()
				.when().get( "/collection/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "{"
						+ "  \"id\": " + id + ","
						+ "  \"season\": \"SPRING_SUMMER\","
						+ "  \"year\": 2021,"
						+ "  \"keywords\": \"my keywords and some more\""
						+ "}" ) );
	}

}