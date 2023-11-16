package org.hibernate.demos.hsearchfeatureexamples;

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
public class TShirtServiceTest {

	static {
		RestAssured.config = RestAssuredConfig.config()
				.objectMapperConfig( ObjectMapperConfig.objectMapperConfig()
						.defaultObjectMapperType( ObjectMapperType.GSON ) )
				.logConfig( LogConfig.logConfig()
						.enableLoggingOfRequestAndResponseIfValidationFails() );
	}

	@Test
	public void create_retrieve_update() {
		Response createResponse = given()
				.contentType( ContentType.JSON )
				.body( """
						{
						  "name":"My T-Shirt",
						  "collection": 1,
						  "variants": [
						    {      "color": "Red",
						      "size": "S",
						      "price": 29.99
						    },
						    {
						      "color": "Blue",
						      "size": "L",
						      "price": 19.99
						    }
						  ]
						}
						""" )
				.when().post( "/tshirt/" )
				.then()
				.statusCode( 200 )
				.extract().response();

		long id = createResponse.body().as( JsonObject.class ).get( "id" ).getAsLong();

		given()
				.when().get( "/tshirt/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( """
						{
						  "id": %d,
						  "name":"My T-Shirt",
						  "collection": { "id": 1 },
						  "variants": [
							{
							  "color": "Red",
							  "size": "S",
							  "price": 29.99
							},
							{
							  "color": "Blue",
							  "size": "L",
							  "price": 19.99
							}
						  ]
						}
						"""
						.formatted(
								id ) )
						.when( Option.IGNORING_EXTRA_FIELDS ) );

		given()
				.contentType( ContentType.JSON )
				.body( """
						{
						  "name":"My T-Shirt",
						  "collection": 1,
						  "variants": [
						    {
						      "color": "Red",
						      "size": "S",
						      "price": 24.99
						    },
						    {
						      "color": "Blue",
						      "size": "L",
						      "price": 19.99
						    }
						  ]
						}
						""" )
				.when().put( "/tshirt/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( """
						{
						  "id": %d,
						  "name":"My T-Shirt",
						  "collection": { "id": 1 },
						  "variants": [
							{
							  "color": "Red",
							  "size": "S",
							  "price": 24.99
							},
							{
							  "color": "Blue",
							  "size": "L",
							  "price": 19.99
							}
						  ]
						}
						""".formatted( id ) )
						.when( Option.IGNORING_EXTRA_FIELDS ) );

		given()
				.when().get( "/tshirt/" + id )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( """
						{
						  "id": %d,
						  "name":"My T-Shirt",
						  "collection": { "id": 1 },
						  "variants": [
						    {
						  	"color": "Red",
						  	"size": "S",
						  	"price": 24.99
						    },
						    {
						  	"color": "Blue",
						  	"size": "L",
						  	"price": 19.99
						    }
						  ]
						}""".formatted( id ) )
						.when( Option.IGNORING_EXTRA_FIELDS ) );
	}

	@Test
	public void search() {
		// The application won't index automatically on start in native (prod) mode,
		// so we need to do it explicitly.
		given()
				.when().post( "/admin/reindex/" )
				.then()
				.statusCode( 204 );

		given()
				.when()
				.queryParam( "q", "jump" )
				.get( "/tshirt/search" )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( """
						{
						    "totalHitCount": 3,
						    "hits": [
						        {
						            "name": "Ski jump"
						        },
						        {
						            "name": "Morty jumping into the abyss"
						        },
						        {
						            "name": "Jumping over a log"
						        }
						    ]
						}""" )
						.when( Option.IGNORING_EXTRA_FIELDS, Option.IGNORING_ARRAY_ORDER ) );
	}

	@Test
	public void searchWithFacets() {
		// The application won't index automatically on start in native (prod) mode,
		// so we need to do it explicitly.
		given()
				.when().post( "/admin/reindex/" )
				.then()
				.statusCode( 204 );

		given()
				.when()
				.queryParam( "q", "jump" )
				.get( "/tshirt/search_facets" )
				.then()
				.statusCode( 200 )
				.body( jsonEquals( """
						{
						  "totalHitCount": 3,
						  "hits": [
							{
							  "name": "Ski jump"
							},
							{
							  "name": "Jumping over a log"
							},
							{
							  "name": "Morty jumping into the abyss"
							}
						  ],
						  "facets": {
							"count-by-price-range": {
							  "[0,5)": 0,
							  "[5,10)": 2,
							  "[10,15)": 1,
							  "[15,20)": 0,
							  "[20,+Infinity]": 0
							},
							"count-by-color": {
							  "Black": 0,
							  "Black and white": 0,
							  "Blue": 0,
							  "Brown": 0,
							  "Dark blue": 0,
							  "Green": 1,
							  "Grey": 0,
							  "Grey and green": 0,
							  "Mauve": 0,
							  "Orange": 1,
							  "Pink": 0,
							  "Purple": 1,
							  "Red": 0,
							  "White": 1,
							  "Yellow": 0
							},
							"count-by-size": {
							  "L": 2,
							  "M": 2,
							  "S": 2,
							  "XL": 2,
							  "XS": 0,
							  "XXL": 1,
							  "XXXL": 1
							}
						  }
						}
						""" )
						.when( Option.IGNORING_EXTRA_FIELDS, Option.IGNORING_ARRAY_ORDER ) );
	}

}