import io.restassured.path.json.JsonPath;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payLoad;


public class sumValidation {

	 @Test
	 public void sumOfCourses(){
		 JsonPath js= new JsonPath(payLoad.CoursePrice());
		 int noOfCourses= js.getInt("courses.size()");
		System.out.println(noOfCourses);
		int sum=0;
		
		for(int i=0;i<=noOfCourses-1;i++){
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			
			int amount=price*copies;
			System.out.println(amount);
			
			sum=sum+amount;
		}
		System.out.println(sum);
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);
	 }
}
