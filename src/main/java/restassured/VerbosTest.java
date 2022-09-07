package restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;

public class VerbosTest {

	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Jose\",	\"age\": 50}")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50))
		;
	}
	
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Usuario via map");
		params.put("age", 25);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via map"))
			.body("age", is(25))
		;
	}
	
	
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
		User user = new User("Usuario via Objeto", 35);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via Objeto"))
			.body("age", is(35))
		;
	}
	
	@Test
	public void deveDeserializarUmObjetoAoSalvar() {
		User user = new User("Usuario Deserializado", 15);
		
		User usuarioDeserializado = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
		;
		
		Assert.assertEquals("Usuario Deserializado", usuarioDeserializado.getName());
		assertThat(usuarioDeserializado.getAge(),is(15));
	}
	
	@Test
	public void naoDeveSalvaUsuarioSemNome() {
		given()
		.log().all()
		.contentType("application/json")
		.body("{\"age\": 50}")
	.when()
		.post("http://restapi.wcaquino.me/users")
	.then()
		.log().all()
		.statusCode(400)
		.body("id", is(nullValue()))
		.body("error", is("Name é um atributo obrigatório"))
		;
	}
	
	@Test
	public void deveAlterarUsuario() {
		given()
		.log().all()
		.contentType("application/json")
		.body("{\"name\": \"Usuario Alterado\",	\"age\": 80}")
	.when()
		.put("http://restapi.wcaquino.me/users/1")
	.then()
		.log().all()
		.statusCode(200)
		.body("id", is(1))
		.body("name", is("Usuario Alterado"))
		.body("age", is(80))
		;		
	}
	
	@Test
	public void devoCustomizarURL() {
		given()
		.log().all()
		.contentType("application/json")
		.body("{\"name\": \"Usuario Alterado\",	\"age\": 80}")
	.when()
		.put("http://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
	.then()
		.log().all()
		.statusCode(200)
		.body("id", is(1))
		.body("name", is("Usuario Alterado"))
		.body("age", is(80))
		;		
	}
	
	@Test
	public void devoCustomizarURL2() {
		given()
		.log().all()
		.contentType("application/json")
		.body("{\"name\": \"Usuario Alterado\",	\"age\": 80}")
		.pathParam("entidade", "users")
		.pathParam("userId", 1)
	.when()
		.put("http://restapi.wcaquino.me/{entidade}/{userId}")
	.then()
		.log().all()
		.statusCode(200)
		.body("id", is(1))
		.body("name", is("Usuario Alterado"))
		.body("age", is(80))
		;		
	}
	
	@Test
	public void devoRemoverUsuario() {
		given()
		.log().all()
		.pathParam("entidade", "users")
		.pathParam("userId", 1)
	.when()
		.delete("http://restapi.wcaquino.me/{entidade}/{userId}")
	.then()
		.log().all()
		.statusCode(204)
		;		
	}
	
	@Test
	public void naoDevoRemoverUsuarioInexistente() {
		given()
		.log().all()
		.pathParam("entidade", "users")
		.pathParam("userId", 1000)
	.when()
		.delete("http://restapi.wcaquino.me/{entidade}/{userId}")
	.then()
		.log().all()
		.statusCode(400)
		.body("error", is("Registro inexistente"))
		;		
	}
}


	

