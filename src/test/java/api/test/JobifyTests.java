package api.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.json.simple.parser.ParseException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STDocProtect;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.JobifyRoutes;
import api.endpoints.Routes;
import api.endpoints.UserEndPoints;
import api.payload.User;

import api.utilities.DataProviders;

import io.restassured.path.json.JsonPath;

import reporting.Setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.MapMaker;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import reporting.Setup;
import restUtils.AssertionUtils;
import restUtils.JsonRequestUtils;

import reporting.ExtentReportManager;
import api.utilities.*;


public class JobifyTests {
	

	String token;
	String id;
	public static String baseUrl = "https://jobify-app-api-dev.onrender.com/api/v1";
	
	//@Test(priority=1, dataProvider= "getData",dataProviderClass = DataProviders.class)
	public void testRegisterUser(Method method,Map<String,Object> userPayload) throws FileNotFoundException, IOException, ParseException {
		Response response = JsonRequestUtils.performJsonPost(DataProviders.getRoutesFromSwaggerJson("Register User"), userPayload, new HashMap<>());		
	}
	
	//@Test(priority=2, dataProvider= "getData", dataProviderClass = DataProviders.class)
	public void testLoginUser(Method method,Map<String,Object> userPayload) throws FileNotFoundException, IOException, ParseException {	
		Response response =JsonRequestUtils.performJsonPost(DataProviders.getRoutesFromSwaggerJson("Login User"), userPayload, new HashMap<>());		
	    token = response.jsonPath().get("token");
		System.out.println(token);
	}
	
		
	//@Test(priority =3, dataProvider="getData",dataProviderClass = DataProviders.class)
	public void testCreateJob(Method method,Map<String,Object> userPayload) {
		// get the access token from login user
		Response response = JsonRequestUtils.performJsonPost(JobifyRoutes.createJobUrl, userPayload,token);
		id = response.jsonPath().get("job._id");
		System.out.println(id);	
	}
	
	//@Test(priority = 4)
	
	public void testGetSingleJob() {
		Response response = JsonRequestUtils.performJsonGet(JobifyRoutes.getSingleJobUrl,"id", id, token);
	}
	
	//@Test(priority =5, dataProvider="getData",dataProviderClass = DataProviders.class)
	
	public void testgetAllJobs(Method method,Map<String,Object> testData) {
		Response response = JsonRequestUtils.performJsonGet(JobifyRoutes.getAllJobsUrl, token);
		AssertionUtils.assertExpectedValuesWithJsonPath(response, testData );
	}
	
	//@Test(priority =6, dataProvider="getData",dataProviderClass = DataProviders.class)
	
	public void testUpdateJob(Method method,Map<String,Object> testData) {
		Response response = JsonRequestUtils.performJsonPatch(JobifyRoutes.updateJobUrl,testData,"id", id,token);
		
	}
	
	//@Test(priority = 7)
	
	public void testDeleteJob() {
		Response response = JsonRequestUtils.performJsonDelete(JobifyRoutes.getSingleJobUrl,"id", id, token);
	}
	
	//@Test(priority=3, dataProvider= "getData",dataProviderClass = DataProviders.class)
	
	public void testDeleteUser(Method method,Map<String,Object> testData) throws FileNotFoundException, IOException, ParseException {
		Response response = JsonRequestUtils.performJsonDelete(DataProviders.getRoutesFromSwaggerJson("Delete User"),"email",testData.get("email"));		
	}
	
	//@Test(priority = 1)
	
	public void getRoutes() {
		System.out.println(DataProviders.getRoutes("loginUrl"));
		
	}
	

	@Test(priority = 1)
	
	public void getReqBody() throws FileNotFoundException, IOException, ParseException {
		System.out.println(DataProviders.getRequestBody());
	}
	
}
