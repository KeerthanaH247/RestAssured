package Basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;

public class FirstDemo {

	public static void main(String[] args)
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-type","application/json")
		.body(payload.addPlace()).when().post("/maps/api/place/add/json").then().log().all()
		.assertThat().statusCode(200).body("status", equalTo("OK"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		JsonPath js=new JsonPath(response);
		String placeId=js.getString("place_id");
		System.out.println(placeId);
	
	given().log().all().queryParam("key", "qaclick123").header("Content-type","application/json")
	.body("").when().put("/maps/api/place/update/json")
	.then().assertThat().statusCode(200).body("msg",equalsTo("Address successfully updated"))
	}
}
