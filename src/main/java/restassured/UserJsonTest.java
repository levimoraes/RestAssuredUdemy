package restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.apache.http.client.methods.RequestBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class UserJsonTest {
	
	public static RequestSpecification reqSpec;
	public static ResponseSpecification resSpec;
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		reqSpec = reqBuilder.build();
		
		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
		respBuilder.expectStatusCode(200);
		resSpec = respBuilder.build();
	}

	@Test
	public void testPrimeiroNivel() {
		given()
		.when()
			.get("/users/1")
		.then()
			.statusCode(200)
			.body("id",is(1))
			.body("age",greaterThan(18));
	}

	@Test
	public void testPrimeiroNivelOutraForma() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/users/1");
		
		//path
		Assert.assertEquals(new Integer(1), response.path("id"));
		
		//jsonPath
		JsonPath jpath= new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
	}
	
	@Test
	public void testSegundoNivel() {
		given()
		.when()
			.get("/users/2")
		.then()
			.statusCode(200)
			.body("id",is(2))
			.body("endereco.rua",is("Rua dos bobos"));
	}
	
	@Test
	public void testLista() {
		
		given()
			.spec(reqSpec)
		.when()
			.get("/users/3")
		.then()
//			.statusCode(200)
			.spec(resSpec)
			.body("id",is(3))
			.body("name",containsString("Ana"))
			.body("filhos", hasSize(2))
			.body("filhos[0].name", is("Zezinho"))
			
			
			
			
			;
	}
	
}
