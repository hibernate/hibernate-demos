package org.hibernate.demo.account;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.http.ContentType;
import io.thorntail.test.ThorntailTestRunner;

/**
 * @author Fabio Massimo Ercoli
 */
@RunWith(ThorntailTestRunner.class)
public class UserAppTest {

	@Inject
	private UserRepo repo;

	@Inject
	private UserTransaction utx;

	@Test
	public void testCreateAndDelete_OnRepo() throws Exception {
		User hibernate = new User( "hibernate" );

		utx.begin();
		repo.add( hibernate );
		utx.commit();

		assertThat( hibernate.getId() ).isNotNull();

		utx.begin();
		List<User> allUsers = repo.findAll();
		utx.commit();

		assertThat( allUsers ).containsExactly( hibernate );

		utx.begin();
		User reloaded = repo.findByUserName( "hibernate" );
		utx.commit();

		assertThat( reloaded ).isEqualTo( hibernate );

		utx.begin();
		repo.delete( "hibernate" );
		utx.commit();

		utx.begin();
		allUsers = repo.findAll();
		utx.commit();

		assertThat( allUsers ).isEmpty();
	}

	@Test
	public void testCreateAndDelete_onService() throws Exception {
		User hibernate = new User( "hibernate" );

		given().body( hibernate ).contentType( ContentType.JSON )
				.when().post( "/user" )
				.then().statusCode( 200 )
				.body( "id", notNullValue() );

		User[] users = given().accept( ContentType.JSON )
				.when().get( "/user/all" )
				.as( User[].class );

		assertThat( users ).hasSize( 1 );
		hibernate = users[0];

		User reloaded = given().accept( ContentType.JSON )
				.queryParam( "username", "hibernate" )
				.when().get( "/user" )
				.as( User.class );

		assertThat( reloaded ).isEqualTo( hibernate );

		when().delete( "/user/username/hibernate" )
				.then().statusCode( 204 );

		users = given().accept( ContentType.JSON )
				.when().get( "/user/all" )
				.as( User[].class );

		assertThat( users ).isEmpty();
	}

}
