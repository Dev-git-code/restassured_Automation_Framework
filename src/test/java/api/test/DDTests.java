package api.test;

import java.util.HashMap;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.Routes;
import api.endpoints.UserEndPoints;
import api.payload.User;
import api.payload.UserObject;
import api.utilities.DataProviders;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import reporting.Setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import reporting.Setup;
import restUtils.AssertionUtils;
import restUtils.RestUtils;
import reporting.ExtentReportManager;




public class DDTests {

	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testPostuser(String userID, String userName,String fname,String lname,String useremail,String pwd,String ph) throws JsonProcessingException 
	{
		UserObject userPayload=new UserObject();
		
		userPayload.setId(Integer.parseInt(userID)); 
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		
		RestUtils.performJsonPost(Routes.post_url, userPayload, new HashMap<>());
		//RestUtils.performXmlPost(Routes.post_url, userPayload, new HashMap<>());
		
			
	}
	
	@Test(priority=2, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testGetUserByName(String userID,String userName,String fname,String lname,String useremail,String pwd,String ph)
	{
			 //Response response=UserEndPoints.readXmlUser(userName);
			 Response response=UserEndPoints.readJsonUser(userName);
			 Map<String,Object> expectedValueMap = new HashMap<>();
			 
//	            expectedValueMap.put("User.username",userName);
//	            expectedValueMap.put("User.firstName",fname);
//	            expectedValueMap.put("User.lastName",lname);
//	            expectedValueMap.put("User.email",useremail);
//	            expectedValueMap.put("User.password",pwd);
//	            expectedValueMap.put("User.phone",ph);   
	            
	            expectedValueMap.put("username",userName);
	            expectedValueMap.put("firstName",fname);
	            expectedValueMap.put("lastName",lname);
	            expectedValueMap.put("email",useremail);
	            expectedValueMap.put("password",pwd);
	            expectedValueMap.put("phone",ph);   
	            
	        //AssertionUtils.assertExpectedValuesWithXmlPath(response, expectedValueMap);
	        //RestUtils.printXmlResponseLogInReport(response);
	        AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
	        RestUtils.printJsonResponseLogInReport(response);
	        
	}
	
	@Test(priority=3, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{ 
			//Response response=UserEndPoints.deleteXmlUser(userName);
			Response response=UserEndPoints.deleteJsonUser(userName);
			RestUtils.printJsonResponseLogInReport(response);	
			AssertionUtils.AssertThat(200, response.getStatusCode(), "correct status code");
				
	
	}
	
	
	
}
