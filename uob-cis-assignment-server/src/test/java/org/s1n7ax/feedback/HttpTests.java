package org.s1n7ax.feedback;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.s1n7ax.feedback.controller.AuthController;
import org.s1n7ax.feedback.model.LoginCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest
public class HttpTests {

	@Autowired
	private AuthController authController;

	@Test
	public void contextLoader() {
		assertThat(authController).isNotNull();
		given().body(Map.of("email", "srinesh@gmail.com", "password", "123")).contentType(ContentType.JSON).when().post("http://localhost:8080/login").then().statusCode(200);
	}
}
