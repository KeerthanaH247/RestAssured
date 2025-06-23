package Basic;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js= new JsonPath(payload.coursePrice());
		int count=js.getInt("courses.size()");
	System.out.println("Course size:"+count);
	int totalAmount=js.getInt("dashboard.purchaseAmount");
	System.out.println("Total amount:"+totalAmount);
	String titlefirstCourse=js.get("courses[0].title");
	System.out.println("First course title:"+titlefirstCourse);
	//Print all course title and their respective prices
	for(int i=0;i<count;i++)
	{
		System.out.println(js.get("courses["+i+"].title").toString());
		System.out.println(js.get("courses["+i+"].price").toString());
		}
	
	System.out.println("Print number of copies sold by RPA course:");
	for(int i=0;i<count;i++)
	{
		String courseTitle= js.getString("courses["+i+"].title");
		if(courseTitle.equalsIgnoreCase("RPA"))
		{
			int copies=js.getInt("courses["+i+"].copies");
			System.out.println(copies);
			break;
		}
	}
	}

}
