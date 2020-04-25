import io.restassured.RestAssured;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import files.ReusableMethods;
import files.payLoad;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class dynamicJson {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn , String aisle){
		
		RestAssured.baseURI="http://216.10.245.166";
		String response=given()
		 .log().all().header("Content-Type", "application/json")
		 .body(payLoad.addBook(isbn,aisle)).
		when()
		  .post("/Library/Addbook.php").
	    then()
	       .log().all().assertThat().statusCode(200)
	       .extract().response().asString();
	      JsonPath js= ReusableMethods.rawToJson(response);
	      String book_id=js.get("ID");
	      System.out.println(book_id);
		  
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData(){
		return new Object[][]{
				{"book1","1234"},
				{"book2","1235"},
				{"book3","1236"}
		};
	}
	
}

