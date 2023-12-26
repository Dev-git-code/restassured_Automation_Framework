package api.test;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.Routes;
import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import reporting.Setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import reporting.Setup;
import restUtils.AssertionUtils;
import restUtils.RestUtils;
import restUtils.XmlUtils;
import reporting.ExtentReportManager;




public class DDTests {

	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testPostuser(String userID, String userName,String fname,String lname,String useremail,String pwd,String ph) throws JAXBException
	{
//		ExtentTest test = Setup.extentReports.createTest("Test Name - "+"POST USER");
//        Setup.extentTest.set(test);
		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
//		Response response = UserEndPoints.createUser(userPayload);
//		RestUtils.printResponseLogInReport(response);
//		 String userXml = XmlUtils.toXml(userPayload);
		RestUtils.performPost(Routes.post_url, userPayload, new HashMap<>());
		//Assert.assertEquals(response.getStatusCode(),200);
			
	}
	
	@Test(priority=2, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testGetUserByName(String userID,String userName,String fname,String lname,String useremail,String pwd,String ph)
	{
//		    ExtentTest test = Setup.extentReports.createTest("Test Name - "+"Get User");
//	        Setup.extentTest.set(test);

			Response response=UserEndPoints.readUser(userName);
			 Map<String,Object> expectedValueMap = new HashMap<>();
			 
	            expectedValueMap.put("username",userName);
	            expectedValueMap.put("firstName",fname);
	            expectedValueMap.put("lastName",lname);
	            expectedValueMap.put("email",useremail);
	            expectedValueMap.put("password",pwd);
	            expectedValueMap.put("phone",ph);   
	        AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
	      // ExtentReportManager.logPassDetails("user validated successfully");
	       // test.log(Status.PASS, "user is valid");
	           
	
	}
	
	@Test(priority=3, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{ 
//		ExtentTest test = Setup.extentReports.createTest("Test Name - "+"Delete User");
//		Setup.extentTest.set(test);

			Response response=UserEndPoints.deleteUser(userName);
			RestUtils.printResponseLogInReport(response);
			Assert.assertEquals(response.getStatusCode(),200);	
	
	}
	
	
	
}
