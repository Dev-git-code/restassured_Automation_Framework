package api.test;

import java.util.HashMap;
import java.util.Map;

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




public class DDTests {

	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testPostuser(String userID, String userName,String fname,String lname,String useremail,String pwd,String ph)
	{
		ExtentTest test = Setup.extentReports.createTest("Test Name - "+"POST USER");
        Setup.extentTest.set(test);
		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		RestUtils.performPost(Routes.post_url, userPayload, new HashMap<>());
		//Assert.assertEquals(response.getStatusCode(),200);
			
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testGetUserByName(String userName)
	{
		    ExtentTest test = Setup.extentReports.createTest("Test Name - "+"Get User");
	        Setup.extentTest.set(test);

			Response response=UserEndPoints.readUser(userName);
			 Map<String,Object> expectedValueMap = new HashMap<>();
			 test.log(Status.PASS, "user is valid");
	            expectedValueMap.put("username",userName);
	            
	        AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
	           
	
	}
	
	@Test(priority=3, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{ ExtentTest test = Setup.extentReports.createTest("Test Name - "+"Delete User");
    Setup.extentTest.set(test);

			Response response=UserEndPoints.deleteUser(userName);
			Assert.assertEquals(response.getStatusCode(),200);	
	
	}
	
	
	
}
