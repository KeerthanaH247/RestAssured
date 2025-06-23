package Basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.Matchers.*;
import java.nio.file.spi.FileSystemProvider;

public class StaticJsonPayload {

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Spoorthi\\Documents\\Java\\Interview Docs\\RestAPI\\DemoProject\\DemoProject\\bin\\files\\payload.class"))))
		.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		JsonPath js=new JsonPath(response);
		String placeId=js.get("place_id");
		System.out.println(placeId);
	}
}
