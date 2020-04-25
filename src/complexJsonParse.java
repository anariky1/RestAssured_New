import files.payLoad;
import io.restassured.path.json.JsonPath;


public class complexJsonParse {
	
	public static void main(String[] args){
	
	 JsonPath js= new JsonPath(payLoad.CoursePrice());
	int noOfCourses= js.getInt("courses.size()");
	System.out.println(noOfCourses);
	
	int purchaseAmount=js.getInt("dashboard.purchaseAmount");
	System.out.println(purchaseAmount);
	
	String courseTitle=js.getString("courses[0].title");
	//js.get("courses[0].title");
	System.out.println(courseTitle);
	
	
	//print all course title and respective price
	
	for(int i=0;i<=noOfCourses-1;i++){
		System.out.println(js.get("courses["+i+"].title")+"=="+js.getInt("courses["+i+"].price"));
	}
	
	
	// no of copies sold by rpa
	for(int i=0;i<=noOfCourses-1;i++){
		System.out.println(js.get("courses["+i+"].title")+"=="+js.getInt("courses["+i+"].price"));
	   if(js.get("courses["+i+"].title").equals("RPA")){
		   System.out.println(js.get("courses["+i+"].copies"));
		   break;
	   }	
	}
	
	
	
	
	
	 
	}
	 

}
