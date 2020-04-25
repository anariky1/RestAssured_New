import java.io.File;

import org.testng.Assert;

import files.ReusableMethods;
import files.payLoad;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class JiraTest {

	public static void main(String[] args) {
		
		
		//login to jira
		RestAssured.baseURI="http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		//handling certificate - relaxedHTTPSValidation()
		String response=given().relaxedHTTPSValidation()
		  .header("Content-Type","application/json")
		  .body("{ \"username\": \"ananthgithub\", \"password\": \"Handgreat99\" }")
		  .log().all().filter(session).
		when()
		  .post("rest/auth/1/session").
	    then().
	      log().all().extract().response().asString();
		
		
		//add comment
		String expectedMessage="This is a comment that I added through rest assured automation - Retrieve comment.";
		String addCommentResponse=given()
		 .pathParam("issueId", "10102")
		 .header("Content-Type","application/json")
		 .body("{\r\n" + 
		 		"    \"body\": \""+expectedMessage+"\",\r\n" + 
		 		"    \"visibility\": {\r\n" + 
		 		"        \"type\": \"role\",\r\n" + 
		 		"        \"value\": \"Administrators\"\r\n" + 
		 		"    }\r\n" + 
		 		"}").filter(session).
		 when()
		  .post("/rest/api/2/issue/{issueId}/comment")
		  .then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId=js.getString("id");
		System.out.println("Comment id is :"+commentId);
		
		
		
		//add attachment
		given()
		.header("Content-Type","multipart/form-data")
		 .header("X-Atlassian-Token","no-check").filter(session)
		 .pathParam("issueId", "10102")
		 .multiPart("file",new File("Jira.txt"))
		 .when()
		  .post("/rest/api/2/issue/{issueId}/attachments")
		 .then().log().all().assertThat().statusCode(200);
		
	System.out.println("Starting get issue*********************************");	
		//get issue
		String issueDetails=given()
		 .filter(session)
		 .pathParam("issueId", "10102").log().all()
		 .queryParam("fields", "comment") //limit the response -fetch only comment field in response
		 .when()
		   .get("/rest/api/2/issue/{issueId}").then().log().all().extract().response().asString();
		System.out.println("***********************************");
		System.out.println(issueDetails);
		//how to retrieve the comment and attachment
		
		JsonPath js1 = new JsonPath(issueDetails);
		int commentsCount1=js1.getInt("fields.comment.comments.size()");
		
		for(int i=0;i<=commentsCount1-1;i++){
				String commentIdIssue=js1.get("fields.comment.comments["+i+"].id").toString();
			     if(commentIdIssue.equals(commentId)){
			    	 String actualMessage=js1.get("fields.comment.comments["+i+"].body").toString();
			    	 System.out.println("actial message is:"+actualMessage);
			    	 Assert.assertEquals(actualMessage, expectedMessage);
			    	 
			     }
		}
		
		 
		

	}

}
