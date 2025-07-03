package Basic;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

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
		System.out.println("#############################################################");
		
		//AddProduct
		RequestSpecification addProductBaseReq=new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
	
		RequestSpecification reqAddProduct=given().log().all()
				.spec(addProductBaseReq).param("productName", "Nature")
		.param("productAddedBy", userID).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription", "Lenova").param("productFor", "women")
		.multiPart("productImage",new File("C:\\Users\\Spoorthi\\Downloads\\download.jpeg"));
//		System.out.println("Added form parameters");
		String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
	
		JsonPath js=new JsonPath(addProductResponse);
		String productID=js.get("productId");
		System.out.println("Product added successfully!!!!!");
		System.out.println("#############################################################");
		
//		Create Order
		RequestSpecification CreateOrderBaseReq= new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON).build();
		OrderDetail orderDetail=new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderId(productID);
		
		List<OrderDetail> oderDetailList= new ArrayList<OrderDetail>();
		oderDetailList.add(orderDetail);
		
		Orders orders=new Orders();
		orders.setOrders(oderDetailList);
		
		RequestSpecification createOrderReq=given().log()
				.all().spec(CreateOrderBaseReq).body(orders);
		String responseAddOrder=createOrderReq.when()
				.post("/api/ecom/order/create-order")
				.then().log().all().extract().response().asString();
		System.out.println(responseAddOrder);
		System.out.println("order created successfully!!!!!");
		System.out.println("#############################################################");
		
		//Delete Product
		RequestSpecification deleteProdBaseReq=	new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON)
				.build();

				RequestSpecification deleteProdReq =given().log().all()
						.spec(deleteProdBaseReq).pathParam("productId",productID);

				String deleteProductResponse = deleteProdReq.when()
						.delete("/api/ecom/product/delete-product/{productId}").then().log().all().
				extract().response().asString();

				JsonPath js1 = new JsonPath(deleteProductResponse);

				Assert.assertEquals("Product Deleted Successfully",js1.get("message"));

				System.out.println("Product Deleted successfully!!!!!");
	}

}
