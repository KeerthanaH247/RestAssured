package Basic;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.lang.classfile.instruction.NewMultiArrayInstruction;
import java.nio.file.Files;

public class ECommerceAPITest {

	public static void main(String[] args) {
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.setUserEmail("demoUserPractice@example.com");
		loginRequest.setUserPassword("Welcome@1");
		
		RequestSpecification reqLogin=given().log()
				.all().spec(req).body(loginRequest);
		LoginResponse loginResponse=reqLogin.when()
				.post("/api/ecom/auth/login").then()
				.log().all().extract().response().as(LoginResponse.class);
		System.out.println("Token :"+loginResponse.getToken());
		System.out.println("User ID: "+loginResponse.getUserId());
		String token=loginResponse.getToken();
		String userID=loginResponse.getUserId();
		
		//AddProduct
		RequestSpecification addProductBaseReq=new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
	
		RequestSpecification reqAddProduct=given().log().all()
				.spec(addProductBaseReq).param("productName", "Laptop")
		.param("productAddedBy", userID).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription", "Lenova").param("productFor", "women")
		.multiPart("productImage",new File("C:\\Users\\Spoorthi\\Downloads\\photo-5157246_1280"));
		
		String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
	
		JsonPath js=new JsonPath(addProductResponse);
		String productID=js.get("productId");
	}

}
