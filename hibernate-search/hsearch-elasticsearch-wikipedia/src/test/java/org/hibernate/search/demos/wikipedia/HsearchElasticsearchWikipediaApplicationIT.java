package org.hibernate.search.demos.wikipedia;

import static io.restassured.RestAssured.given;
import static org.skyscreamer.jsonassert.JSONCompare.compareJSON;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class HsearchElasticsearchWikipediaApplicationIT {

	static {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	public void indexAndSearch() throws JSONException {
		String body = given().param( "q", "brave" )
				.when().get( "/page/search" )
				.then()
				.statusCode( 200 )
				.extract().body().asString();
		compareJSON( "{\"totalCount\": 0}", body, JSONCompareMode.LENIENT );

		given().body( "{\"title\": \"Brave new World\", \"content\": \"A classic.\"}" )
				.contentType( ContentType.JSON )
				.when().post( "/page" ).then()
				.statusCode( 201 );

		body = given().param( "q", "brave" )
				.when().get( "/page/search" )
				.then()
				.statusCode( 200 )
				.extract().body().asString();
		compareJSON( "{\"totalCount\": 1}", body, JSONCompareMode.LENIENT );
	}

}
