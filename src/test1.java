import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import pojo.GetCourse;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;




public class test1 {

	public static void main(String[] args) {
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2FzAEcd5X3V0vLFDQH4tq5OmfE9wsaRonMKy9uUo-gvieltWbA_z2VUFcIz3gAD32r4dz4l8csz3CNsoCj6pDyFxA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none#";
		String[]expectedCourseTitles={"Selenium Webdriver Java","Cypress1","Protractor"};
		
		
		String partialcode=url.split("code=")[1];
		String code=partialcode.split("&scope")[0];
		System.out.println(code);
		String response =
				given() 
		              .urlEncodingEnabled(false)
		              .queryParams("code",code)		               
		               .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		               .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		               .queryParams("grant_type", "authorization_code")
		               .queryParams("state", "verifyfjdss")
		               .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
		                  // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
	                   .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		        .when().log().all()
		                .post("https://www.googleapis.com/oauth2/v4/token").asString();
		               // System.out.println(response);
		               
		              JsonPath jsonPath = new JsonPath(response); 
		              String accessToken = jsonPath.getString("access_token");		              
		               System.out.println(accessToken);
		               
		               
	                	GetCourse gc=given().contentType("application/json").
		              queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)

	           	.when()
		           .get("https://rahulshettyacademy.com/getCourse.php")
		            .as(GetCourse.class);
		//System.out.println(gc);
		
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		//course title of api
		String api2CourseTitle=gc.getCourses().getApi().get(1).getCourseTitle();
         System.out.println(api2CourseTitle);

         //get me the price of SoapUI Webservices testing course
         String courseTitle="Rest Assured Automation using Java";
         int items= gc.getCourses().getApi().size();
        System.out.println(items);
        for(int i=0;i<=items-1;i++){
        	if(gc.getCourses().getApi().get(i).getCourseTitle().equals(courseTitle)){
        		String price= gc.getCourses().getApi().get(i).getPrice();
        		System.out.println(price);
        	}
        }
        
        // get me the course title of web automation
        int webAutomationSize=gc.getCourses().getWebAutomation().size();
        ArrayList<String> actualCourseTitle = new ArrayList<String>();
        for(int i=0;i<=webAutomationSize-1;i++){
        	String webAutomationCourseTitle=gc.getCourses().getWebAutomation().get(i).getCourseTitle();
            System.out.println(webAutomationCourseTitle);
            actualCourseTitle.add(webAutomationCourseTitle);
        }
        
        List<String> expectedList=  Arrays.asList(expectedCourseTitles);
        
        Assert.assertTrue(actualCourseTitle.equals(expectedList));
        
        
        
        
        
        
        
        
        
        
	}

}
