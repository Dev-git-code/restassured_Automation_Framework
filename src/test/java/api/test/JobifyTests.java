package api.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

import static io.restassured.RestAssured.given;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.JobifyRoutes;
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


public class JobifyTests {
	

	String token;
	String id;
	@Test(priority=1, dataProvider= "getData",dataProviderClass = DataProviders.class)
	public void testRegisterUser(Method method,Map<String,Object> userPayload) throws JsonProcessingException {
		
		Response response = RestUtils.performJsonPost(JobifyRoutes.registerUrl, userPayload, new HashMap<>());		
	}
	
	@Test(priority=2, dataProvider= "getData", dataProviderClass = DataProviders.class)
	public void testLoginUser(Method method,Map<String,Object> userPayload) throws JsonProcessingException {	
		Response response = RestUtils.performJsonPost(JobifyRoutes.loginUrl, userPayload, new HashMap<>());		
	    token = response.jsonPath().get("token");
		System.out.println(token);
	}
	
		
	@Test(priority =3, dataProvider="getData",dataProviderClass = DataProviders.class)
	public void testCreateJob(Method method,Map<String,Object> userPayload) {
		// get the access token from login user
		Response response = RestUtils.performJsonPostWithOauth2(JobifyRoutes.createJobUrl, userPayload,token);
		id = response.jsonPath().get("job._id");
		System.out.println(id);	
	}
	
	@Test(priority = 4)
	
	public void testGetSingleJob() {
		Response response = RestUtils.performJsonGetWithOauth2(JobifyRoutes.getSingleJobUrl,"id", id, token);
		RestUtils.printJsonResponseLogInReport(response);
	}
	
	@Test(priority =5, dataProvider="getData",dataProviderClass = DataProviders.class)
	
	public void testgetAllJobs(Method method,Map<String,Object> testData) {
		Response response = RestUtils.performJsonGetWithOauth2(JobifyRoutes.getALlJobsUrl, token);
		RestUtils.printJsonResponseLogInReport(response);
		AssertionUtils.assertExpectedValuesWithJsonPath(response, testData );
	}
	
	@Test(priority =6, dataProvider="getData",dataProviderClass = DataProviders.class)
	
	public void testUpdateJob(Method method,Map<String,Object> testData) {
		Response response = RestUtils.performJsonPatchWithOauth2(JobifyRoutes.updateJobUrl,testData,"id", id,token);
		
	}
	
	@Test(priority = 7)
	
	public void testDeleteJob() {
		Response response = RestUtils.performJsonGetWithOauth2(JobifyRoutes.getSingleJobUrl,"id", id, token);
		RestUtils.printJsonResponseLogInReport(response);
	}
	
	@Test(priority=8, dataProvider= "getData",dataProviderClass = DataProviders.class)
	
	public void testDeleteUser(Method method,Map<String,Object> testData) throws JsonProcessingException {
		Response response = RestUtils.performJsonDelete(JobifyRoutes.deleteUserUrl,"email",testData.get("email"));		
		RestUtils.printJsonResponseLogInReport(response);	
	}
	
	
	
	
}
