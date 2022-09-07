package restassured;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMundo() {
		Response response = request(Method.GET,"http://restapi.wcaquino.me/ola" );
		assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		assertEquals(200,response.statusCode());
	
		
	}
	
	@Test
	public void testOlaMundoRefactor() {
//		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void testsHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria") );
		Assert.assertThat(128d, Matchers.isA(Double.class) );
		Assert.assertThat(128d, Matchers.greaterThan(120d) );
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, containsInAnyOrder(1,9,3,7,5));
	} 
	
	@Test
	public void testValidaBody() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}

}
