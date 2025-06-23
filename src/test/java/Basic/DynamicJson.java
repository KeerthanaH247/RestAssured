package Basic;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.security.PublicKey;

public class DynamicJson {
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String asile)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().header("Content-Type","application/json")
		.body(payload.Addbook(isbn,asile)).when().post("Library/Addbook.php").then().log().all()
		.assertThat().statusCode(200).extract().response().asString();
		JsonPath js=ReUsableMethods.rawToJson(response);
		String id=js.get("ID");
		System.out.println("Added book ID"+id);
	}
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"hsgai", "7275"}, {"yakau", "5762"}, {"uslq", "1280"}
	};
	}
	@Test(dataProvider = "BooksData")
	public void deleteBook(String isbn, String asile)
	{
		RestAssured.baseURI="http://216.10.245.166";	
		String response=given().header("Content-Type","application/json")
				.body(payload.Addbook(isbn,asile)).when().delete("Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath js=ReUsableMethods.rawToJson(response);
		String id=js.get("ID");
		System.out.println("Deleted book id :"+id);
	
	}
	
}
