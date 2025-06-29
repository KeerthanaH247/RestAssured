package Basic;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.Mobile;
import pojo.WebAutomation;
public class OAuthTest {

	public static void main(String[] args) {
		String[] courseTitles= {"Selenium Webdriver Java","Cypress","Protractor"};
		String response=given()
		.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParams("grant_type", "client_credentials")
		.formParams("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		System.out.println(response);
		JsonPath js= new JsonPath(response);
		String accessToken= js.getString("access_token");
		
		GetCourse gc=given().queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		System.out.println("LinkedIn: "+gc.getLinkedIn());
		List<Mobile> mobile = gc.getCourses().getMobile();
		for(Mobile m: mobile)
		{
			System.out.println("title"+m.getCourseTitle());
			System.out.println("Price"+m.getPrice());
		}
		List<Api> apiCourses= gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println("Price of SoapUI Webservices testing:"+apiCourses.get(i).getPrice());
			}
		}
		ArrayList<String> arrayList=new ArrayList<>();
		List<WebAutomation> w = gc.getCourses().getWebAutomation();
		for(int i=0;i<w.size();i++)
		{
			System.out.println(w.get(i).getCourseTitle());
		}
		
		for(int j=0;j<w.size();j++)
		{
			arrayList.add(w.get(j).getCourseTitle());
		}
		List<String> expectedList= Arrays.asList(courseTitles);
		Assert.assertTrue(arrayList.equals(expectedList));
		
	}

}
