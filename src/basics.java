import org.testng.Assert;

import files.ReusableMethods;
import files.payLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;




public class basics {

	public static void main(String[] args) {
	
		 //validate add place api is working as expected
		
		//given -- all input details
		//when   - submit the api
		//then  - validate the response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";		
		String response=given().log().all().queryParam("key" , "qaclick123")
		       .header("Content-Type","application/json")
		       .body(payLoad.addPlace())
		
		.when()
		//resource and http method
		   .post("/maps/api/place/add/json")
		   .then()
		      // .log().all().assertThat().statusCode(200)
		         .assertThat().statusCode(200)
		      .body("scope", equalTo("APP"))
		      .header("Server", "Apache/2.4.18 (Ubuntu)")
		      .extract().response().asString();
		
		System.out.println("printing response.....");
		System.out.println(response);
		
		//parsing the response
		JsonPath js= ReusableMethods.rawToJson(response);
		String placeId=js.getString("place_id");
		System.out.println("Place id is :"+placeId);
		
		
		//update place with new address
		String newAddress="70 Summer walk, USA";
		given()
	     .log().all()
	     .queryParam("key" , "qaclick123")
	     .header("Content-Type","application/json")
	     .body("{\r\n" + 
	     		"\"place_id\":\""+placeId+"\",\r\n" + 
	     		"\"address\":\""+newAddress+"\",\r\n" + 
	     		"\"key\":\"qaclick123\"\r\n" + 
	     		"}\r\n" + 
	     		"")
	     .when()
	         .put("maps/api/place/update/json")
	     .then()
	        .assertThat().log().all().body("msg", equalTo("Address successfully updated"));
	     
		
		//get place
		 String getPlaceRes=given()
	      .log().all()
	      .queryParam("key" , "qaclick123")
	      .queryParam("place_id" , placeId)
	     .when()
	         .get("/maps/api/place/get/json")
	     .then()
		        .assertThat().log().all().statusCode(200).extract().response().asString();
		 JsonPath js1= ReusableMethods.rawToJson(getPlaceRes);
			String actualAddress=js1.getString("address");
			
			System.out.println("Actual address is "+actualAddress);
			Assert.assertEquals(actualAddress, newAddress);
			
		 
		 
		 
	      
		
		
		
		
		
		
		

	}

}
