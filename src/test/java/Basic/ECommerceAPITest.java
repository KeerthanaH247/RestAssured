package Basic;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;

import static io.restassured.RestAssured.*;

public class ECommerceAPITest {

	public static void main(String[] args) {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.setUserEmail("demoUserPractice@example.com");
		loginRequest.setUserPassword("Welcome@1");
		
		RequestSpecification reqLogin=given().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse=reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		System.out.println("Token :"+loginResponse.getToken());
		System.out.println("User ID: "+loginResponse.getUserId());
	}

}
