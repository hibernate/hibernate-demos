package org.hibernate.demos.hsearchfeatureexamples;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.javacrumbs.jsonunit.core.Option;

@QuarkusTest
public class TShirtServiceTest {

	static {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	public void create_retrieve_update() {
		Response createResponse = given()
				.contentType( ContentType.JSON )
				.body( "{"
						+ "  \"name\":\"My T-Shirt\","
						+ "  \"collection\": 1,"
						+ "  \"variants\": ["
						+ "    {"
						+ "      \"color\": \"Red\","
						+ "      \"size\": \"S\","
						+ "      \"price\": 29.99"
						+ "    },"
						+ "    {"
						+ "      \"color\": \"Blue\","
						+ "      \"size\": \"L\","
						+ "      \"price\": 19.99"
						+ "    }"
						+ "  ]"
						+ "}" )
				.when().put( "/tshirt/" )
				.then()
				.statusCode( 200 )
				.extract().response();

		long id = createResponse.body().as( JsonObject.class ).get( "id" ).getAsLong();

		given()
				.when().get( "/tshirt/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "{"
						+ "  \"id\": " + id + ","
						+ "  \"name\":\"My T-Shirt\","
						+ "  \"collection\": { \"id\": 1 },"
						+ "  \"variants\": ["
						+ "    {"
						+ "      \"color\": \"Red\","
						+ "      \"size\": \"S\","
						+ "      \"price\": 29.99"
						+ "    },"
						+ "    {"
						+ "      \"color\": \"Blue\","
						+ "      \"size\": \"L\","
						+ "      \"price\": 19.99"
						+ "    }"
						+ "  ]"
						+ "}" )
						.when( Option.IGNORING_EXTRA_FIELDS ) );

		given()
				.contentType( ContentType.JSON )
				.body( "{"
						+ "  \"name\":\"My T-Shirt\","
						+ "  \"collection\": 1,"
						+ "  \"variants\": ["
						+ "    {"
						+ "      \"color\": \"Red\","
						+ "      \"size\": \"S\","
						+ "      \"price\": 24.99"
						+ "    },"
						+ "    {"
						+ "      \"color\": \"Blue\","
						+ "      \"size\": \"L\","
						+ "      \"price\": 19.99"
						+ "    }"
						+ "  ]"
						+ "}" )
				.when().post( "/tshirt/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "{"
						+ "  \"id\": " + id + ","
						+ "  \"name\":\"My T-Shirt\","
						+ "  \"collection\": { \"id\": 1 },"
						+ "  \"variants\": ["
						+ "    {"
						+ "      \"color\": \"Red\","
						+ "      \"size\": \"S\","
						+ "      \"price\": 24.99"
						+ "    },"
						+ "    {"
						+ "      \"color\": \"Blue\","
						+ "      \"size\": \"L\","
						+ "      \"price\": 19.99"
						+ "    }"
						+ "  ]"
						+ "}" )
						.when( Option.IGNORING_EXTRA_FIELDS ) );

		given()
				.when().get( "/tshirt/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "{"
						+ "  \"id\": " + id + ","
						+ "  \"name\":\"My T-Shirt\","
						+ "  \"collection\": { \"id\": 1 },"
						+ "  \"variants\": ["
						+ "    {"
						+ "      \"color\": \"Red\","
						+ "      \"size\": \"S\","
						+ "      \"price\": 24.99"
						+ "    },"
						+ "    {"
						+ "      \"color\": \"Blue\","
						+ "      \"size\": \"L\","
						+ "      \"price\": 19.99"
						+ "    }"
						+ "  ]"
						+ "}" )
						.when( Option.IGNORING_EXTRA_FIELDS ) );
	}

	@Test
	public void search() {
		given()
				.when()
						.queryParam( "q", "jump" )
						.get( "/tshirt/search" )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( "{\n" +
						"    \"totalHitCount\": 3,\n" +
						"    \"hits\": [\n" +
						"        {\n" +
						"            \"name\": \"Ski jump\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"Morty jumping into the abyss\"\n" +
						"        },\n" +
						"        {\n" +
						"            \"name\": \"Jumping over a log\"\n" +
						"        }\n" +
						"    ]\n" +
						"}" )
						.when( Option.IGNORING_EXTRA_FIELDS, Option.IGNORING_ARRAY_ORDER ) );
	}

}