import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import pojo.AddPlace;
import pojo.Location;
import files.ReusableMethods;
import files.payLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class serializeTest {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com/";
		
		// we are passing POJO class to create request
		AddPlace addPlace = new AddPlace();
		addPlace.setAccuracy(50);
		addPlace.setAddress("29, side layout, cohen 09");
		addPlace.setLanguage("French-IN");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setWebsite("http://google.com");
		addPlace.setName("Frontline house");
		
		//add types
		List<String>typesList = new ArrayList<String>();
		typesList.add("shoe park");
		typesList.add("shop");	
		addPlace.setTypes(typesList);
		
		
		//add location
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		addPlace.setLocation(location);
		
		
		Response res= given()
		   .log().all()
		   .queryParam("Key", "qaclick123")
		   .body(addPlace)
	   .when()
	     .post("maps/api/place/add/json")
	   .then()
	      .assertThat().statusCode(200).extract().response();
		
		String responseString=res.asString();
		System.out.println(responseString);
		
		
		 
		 
		 
		 
		
	}

}
