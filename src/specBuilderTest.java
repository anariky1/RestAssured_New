import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import pojo.AddPlace;
import pojo.Location;
import files.ReusableMethods;
import files.payLoad;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class specBuilderTest {
	
	public static void main(String[] args) {
		
		//setting through spec builder
		//RestAssured.baseURI="https://rahulshettyacademy.com/";
		
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
		
		RequestSpecification reqSpecRequest=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
		   .addQueryParam("Key", "qaclick123")
		   .setContentType(ContentType.JSON)
		   .build();
		
		
		RequestSpecification res= given()
		   .log().all()
		   .spec(reqSpecRequest)
		   .body(addPlace);
		
		ResponseSpecification ResponseSpecification= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON). build();
	
		Response response=res.when()
	     .post("maps/api/place/add/json")
	   .then()
	      .spec(ResponseSpecification).extract().response();
		
		String responseString=response.asString();
		System.out.println(responseString);
		
		
		 
		 
		 
		 
		
	}

}
