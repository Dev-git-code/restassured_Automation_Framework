package api.test;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
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
import api.utilities.*;




public class DDTests {

	
	//@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testPostuserOld(String userID, String userName,String fname,String lname,String useremail,String pwd,String ph) throws JsonProcessingException 
	{
		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(userID)); 
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		Response response = RestUtils.performJsonPost(Routes.post_url, userPayload, new HashMap<>());
		  //Response response = RestUtils.performXmlPost(Routes.post_url, userPayload, new HashMap<>());
		
		Map<String,String> expectedHeader = new HashMap<>(); 
        expectedHeader.put("Content-Type", "application/json");
        expectedHeader.put("Transfer-Encoding", "chunked");
        expectedHeader.put("Server", "Jetty(9.2.9.v20150224)");
//		
//        Map<String,String> expectedHeader = new HashMap<>(); 
//        expectedHeader.put("Content-Type", "application/xml");
//        expectedHeader.put("Content-Length", response.header("Content-Length"));
//        expectedHeader.put("Server", "Jetty(9.2.9.v20150224)");
//        
	            
		
		AssertionUtils.AssertExpectedHeaders(response, expectedHeader);
	
			
	}
	
	
	
	//@Test(priority=2, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testGetUserByNameOld(String userID, String userName,String fname,String lname,String useremail,String pwd,String ph)
	{
			 //Response response=UserEndPoints.readXmlUser(userName);
			 Response response=UserEndPoints.readJsonUser(userName);
			 System.out.println(userName);
			 //Response response= RestUtils.performJsonGet(Routes.get_url,"username",userName);
			 Hashtable<String,Object> expectedValueMap = new Hashtable<>();
			 
//            expectedValueMap.put("User.username",userName);
//            expectedValueMap.put("User.firstName",fname);
//            expectedValueMap.put("User.lastName",lname);
//            expectedValueMap.put("User.email",useremail);
//            expectedValueMap.put("User.password",pwd);
//            expectedValueMap.put("User.phone",ph);   
	            
	            expectedValueMap.put("username",userName);
	            expectedValueMap.put("firstName",fname);
	            expectedValueMap.put("lastName",lname);
	            expectedValueMap.put("email",useremail);
	            expectedValueMap.put("password",pwd);
	            expectedValueMap.put("phone",ph);   
//	            
	        
	       
	        Map<String,String> expected = new HashMap<>(); 
	        expected.put("Content-Type", "application/json");
	        expected.put("Transfer-Encoding", "chunked");
	        expected.put("Server", "Jetty(9.2.9.v20150224)");
	        
//	        Map<String,String> expected = new HashMap<>(); 
//	        expected.put("Content-Type", "application/xml");
//	        expected.put("Content-Length", response.header("Content-Length"));
//	        expected.put("Server", "Jetty(9.2.9.v20150224)");
//	        
	        
	        AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
	        //AssertionUtils.assertExpectedValuesWithXmlPath(response, expectedValueMap);
	        AssertionUtils.AssertExpectedHeaders(response, expected);
	        RestUtils.printJsonResponseLogInReport(response);
	        //RestUtils.printXmlResponseLogInReport(response);
	        
	        
	}
	
	ExcelUtil excel = new ExcelUtil();
	@DataProvider
	public Object[][] getApplicationData(Method method){
		
		return excel.getTestData(method.getName(),"BAT_DATA");
	}
	
	@Test(priority=1, dataProvider= "getApplicationData")
	public void testPostuser(Method method,Map<String,Object> userPayload) throws JsonProcessingException 
	{
//		User userPayload=new User();
//		
//		userPayload.setId(Integer.parseInt(userID)); 
//		userPayload.setUsername(userName);
//		userPayload.setFirstName(fname);
//		userPayload.setLastName(lname);
//		userPayload.setEmail(useremail);
//		userPayload.setPassword(pwd);
//		userPayload.setPhone(ph);
		
		Response response = RestUtils.performJsonPost(Routes.post_url, userPayload, new HashMap<>());
		  //Response response = RestUtils.performXmlPost(Routes.post_url, userPayload, new HashMap<>());
		
		Map<String,String> expectedHeader = new HashMap<>(); 
        expectedHeader.put("Content-Type", "application/json");
        expectedHeader.put("Transfer-Encoding", "chunked");
        expectedHeader.put("Server", "Jetty(9.2.9.v20150224)");
//		
//        Map<String,String> expectedHeader = new HashMap<>(); 
//        expectedHeader.put("Content-Type", "application/xml");
//        expectedHeader.put("Content-Length", response.header("Content-Length"));
//        expectedHeader.put("Server", "Jetty(9.2.9.v20150224)");
//        
	            
		
		AssertionUtils.AssertExpectedHeaders(response, expectedHeader);
	
			
	}
	
	@Test(priority=2, dataProvider="getApplicationData" )
	public void testGetUserByName(Method method,Map<String,Object> testData)
	{
			 //Response response=UserEndPoints.readXmlUser(userName);
			 Response response=UserEndPoints.readJsonUser(testData.get("username").toString());
			 System.out.println(testData.get("username"));
			 //Response response= RestUtils.performJsonGet(Routes.get_url,"username",userName);
			 //Hashtable<String,Object> expectedValueMap = new Hashtable<>();
			 
//            expectedValueMap.put("User.username",userName);
//            expectedValueMap.put("User.firstName",fname);
//            expectedValueMap.put("User.lastName",lname);
//            expectedValueMap.put("User.email",useremail);
//            expectedValueMap.put("User.password",pwd);
//            expectedValueMap.put("User.phone",ph);   
	            
//	            expectedValueMap.put("username",userName);
//	            expectedValueMap.put("firstName",fname);
//	            expectedValueMap.put("lastName",lname);
//	            expectedValueMap.put("email",useremail);
//	            expectedValueMap.put("password",pwd);
//	            expectedValueMap.put("phone",ph);   
////	            
	        
	       
	        Map<String,String> expected = new HashMap<>(); 
	        expected.put("Content-Type", "application/json");
	        expected.put("Transfer-Encoding", "chunked");
	        expected.put("Server", "Jetty(9.2.9.v20150224)");
	        
//	        Map<String,String> expected = new HashMap<>(); 
//	        expected.put("Content-Type", "application/xml");
//	        expected.put("Content-Length", response.header("Content-Length"));
//	        expected.put("Server", "Jetty(9.2.9.v20150224)");
//	        
	        
	        AssertionUtils.assertExpectedValuesWithJsonPath(response, testData);
	        //AssertionUtils.assertExpectedValuesWithXmlPath(response, expectedValueMap);
	        AssertionUtils.AssertExpectedHeaders(response, expected);
	        RestUtils.printJsonResponseLogInReport(response);
	        //RestUtils.printXmlResponseLogInReport(response);
	        
	        
	}
	
	//@Test(priority=3, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{ 
			//Response response=UserEndPoints.deleteXmlUser(userName);
			Response response=UserEndPoints.deleteJsonUser(userName);
			//RestUtils.printXmlResponseLogInReport(response);
			RestUtils.printJsonResponseLogInReport(response);	
			AssertionUtils.AssertThat(200, response.getStatusCode(), "correct status code");
				
	
	}
	
	
	
	//@Test(priority=1, dataProvider="getApplicationData")
	public void testGetUser(Method method, Hashtable<String,String> testData) {
		
		System.out.println(testData);
		
	}
	
	
}
