package restUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class RestUtils {

	/**
	 * Returns a RequestSpecification for an XML request with the specified endpoint, request payload,
	 * and headers. Sets up the request with base URI, headers, content type, and body for XML.
	 * @param endPoint The API endpoint for the request.
	 * @param requestPayload The request payload object.
	 * @param headers The headers to be included in the request.
	 * @return A RequestSpecification for an XML request.
	 */
    private static RequestSpecification getXmlRequestSpecification(String endPoint, Object requestPayload, Map<String,String>headers) {
        return RestAssured.given()
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .body(requestPayload);
    }
    
    /**
     * Returns a RequestSpecification configured for XML content type.
     *
     * @return A RequestSpecification instance configured for XML content type.
     */
    private static RequestSpecification getXmlRequestSpecification() {
        return RestAssured.given()
                .contentType(ContentType.XML)
                .accept(ContentType.XML);
    }
    
    /**
     * Returns a RequestSpecification configured for XML content type with OAuth 2.0 authentication.
     *
     * @param accessToken The OAuth 2.0 access token to be used for authentication.
     * @return A RequestSpecification instance configured for XML content type with OAuth 2.0 authentication.
     */
    private static RequestSpecification getXmlRequestSpecificationWithOauth2(String accessToken) {
        return RestAssured.given()
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .auth().oauth2(accessToken);
    }
    
    /**
     * Returns a RequestSpecification for a JSON request with the specified endpoint, request payload,
     * and headers. Sets up the request with base URI, headers, content type, and body for JSON.
     * @param endPoint The API endpoint for the request.
     * @param requestPayload The request payload object.
     * @param headers The headers to be included in the request.
     * @return A RequestSpecification for a JSON request.
     */
    private static RequestSpecification getJsonRequestSpecification(String endPoint, Object requestPayload, Map<String,String>headers) {
        return RestAssured.given()
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestPayload);
    }
    
    /**
     * Returns a RequestSpecification configured for JSON content type.
     *
     * @return A RequestSpecification instance configured for JSON content type.
     */
    private static RequestSpecification getJsonRequestSpecification() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
    
    
    private static RequestSpecification getJsonRequestSpecification(String pathParam, Object pathParamValue) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam(pathParam,pathParamValue);
    }
    
    private static RequestSpecification getJsonRequestSpecificationWithOauth2(String pathParam, Object pathParamValue, String accessToken) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam(pathParam,pathParamValue)
                .auth().oauth2(accessToken);
    }
    
    
    /**
     * Returns a RequestSpecification configured for JSON content type with OAuth 2.0 authentication.
     *
     * @param accessToken The OAuth 2.0 access token to be used for authentication.
     * @return A RequestSpecification instance configured for JSON content type with OAuth 2.0 authentication.
     */
    private static RequestSpecification getJsonRequestSpecificationWithOauth2(String accessToken) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().oauth2(accessToken);
    }
    
    /**
     * Returns a RequestSpecification configured for JSON requests with OAuth 2.0 authentication.
     *
     * @param requestPayload    The payload to be included in the request body.
     * @param accessToken       The OAuth 2.0 access token to be used for authentication.
     * 
     * @return A RequestSpecification object configured for JSON requests with OAuth 2.0 authentication.
     */
    private static RequestSpecification getJsonRequestSpecificationWithOauth2(Object requestPayload,String accessToken) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestPayload)
                .auth().oauth2(accessToken);
    }
    
    /**
     * Constructs and returns a RequestSpecification with JSON content type and OAuth 2.0 authentication.
     * This method is specifically designed for making JSON requests with OAuth 2.0 authentication and optional path parameters.
     *
     * @param requestPayload  The payload of the request in JSON format.
     * @param pathParam       The name of the path parameter (can be null if no path parameter is needed).
     * @param pathParamValue  The value of the path parameter.
     * @param accessToken     The OAuth 2.0 access token for authentication.
     * @return A RequestSpecification configured with JSON content type, OAuth 2.0 authentication, and optional path parameters.
     */
    private static RequestSpecification getJsonRequestSpecificationWithOauth2(Object requestPayload,String pathParam,Object pathParamValue,String accessToken) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam(pathParam, pathParamValue)
                .body(requestPayload)
                .auth().oauth2(accessToken);
    }

	/**
	 * Logs the details of a JSON request in the Extent report, including endpoint, method, headers,
	 * and request body.
	 * @param requestSpecification The RequestSpecification for the JSON request.
	 */
    public static void printJsonRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetails("Endpoint is " + queryableRequestSpecification.getBaseUri());
        ExtentReportManager.logInfoDetails("Method is " + queryableRequestSpecification.getMethod());
        ExtentReportManager.logInfoDetails("Headers are ");
        ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Request body is ");
        ExtentReportManager.logJson(queryableRequestSpecification.getBody());
    }
    
    
    /**
     * Logs the details of an XML request in the Extent report, including endpoint, method, headers,
     * and request body.
     * @param requestSpecification The RequestSpecification for the XML request.
     */
    public static void printXmlRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetails("Endpoint is " + queryableRequestSpecification.getBaseUri());
        ExtentReportManager.logInfoDetails("Method is " + queryableRequestSpecification.getMethod());
        ExtentReportManager.logInfoDetails("Headers are ");
        ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Request body is ");
        ExtentReportManager.logXml(queryableRequestSpecification.getBody());
    } 
    

	/**
	 * Logs the details of a JSON response in the Extent report, including status code, headers,
	 * and response body.
	 * @param response The Response object for the JSON response.
	 */
    public static void printJsonResponseLogInReport(Response response) {
        ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response Headers are ");
        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body is ");
        ExtentReportManager.logJson(response.getBody().prettyPrint());
    }
    

	/**
	 * Logs the details of an XML response in the Extent report, including status code, headers,
	 * and response body.
	 * @param response The Response object for the XML response.
	 */
    public static void printXmlResponseLogInReport(Response response) {
        ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response Headers are ");
        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body is ");
        ExtentReportManager.logXml(response.getBody().prettyPrint());
    }
 
    /**
     * Performs a POST request with JSON payload (using a POJO) and logs request and response details in the Extent report.
     * @param endPoint The API endpoint for the request.
     * @param requestPayloadAsPojo The JSON request payload as a POJO (Plain Old Java Object).
     * @param headers The headers to be included in the request.
     * @return The Response object for the POST request.
     */
    public static Response performJsonPost(String endPoint, Object requestPayloadAsPojo, Map<String,String>headers) {
        RequestSpecification requestSpecification = getJsonRequestSpecification(endPoint, requestPayloadAsPojo, headers);
        Response response =  requestSpecification.post();
        printJsonRequestLogInReport(requestSpecification);
        printJsonResponseLogInReport(response);
        return response;
    }
    
    /**
     * Performs a POST request with JSON payload and logs request and response details in the Extent report.
     * @param endPoint The API endpoint for the request.
     * @param requestPayload The JSON request payload as a string.
     * @param headers The headers to be included in the request.
     * @return The Response object for the POST request.
     */
    public static Response performJsonPost(String endPoint, String requestPayload, Map<String,String>headers) {
        RequestSpecification requestSpecification = getJsonRequestSpecification(endPoint, requestPayload, headers);
        Response response =  requestSpecification.post();
        printJsonRequestLogInReport(requestSpecification);
        printJsonResponseLogInReport(response);
        return response;
    }

    /**
     * Performs a POST request with JSON payload and logs request and response details in the Extent report.
     * @param endPoint The API endpoint for the request.
     * @param requestPayload The JSON request payload as a map.
     * @param headers The headers to be included in the request.
     * @return The Response object for the POST request.
     */
    public static Response performJsonPost(String endPoint, Map<String, Object> requestPayload, Map<String,String>headers) {
        RequestSpecification requestSpecification = getJsonRequestSpecification(endPoint, requestPayload, headers);
        Response response =  requestSpecification.post();
        printJsonRequestLogInReport(requestSpecification);
        printJsonResponseLogInReport(response);
        return response;
    }
    
    /**
     * Performs a JSON POST request with OAuth 2.0 authentication.
     *
     * @param endPoint         The API endpoint to send the POST request to.
     * @param requestPayload   The payload to be included in the request body as a Map.
     * @param accessToken      The OAuth 2.0 access token to be used for authentication.
     * @return A Response object representing the server's response to the POST request.
     */
    public static Response performJsonPost(String endPoint, Map<String, Object> requestPayload, String accessToken) {
    	RequestSpecification requestSpecification = getJsonRequestSpecificationWithOauth2(requestPayload,accessToken);
    	Response response =  requestSpecification.when().post(endPoint);
    	printJsonRequestLogInReport(requestSpecification);
    	printJsonResponseLogInReport(response);
    	return response;
    }

	/**
	 * Performs a GET request with JSON content and logs request and response details in the Extent report.
	 * @param endPoint The API endpoint for the request.
	 * @param pathParam The path parameter name.
	 * @param pathParamValue The path parameter value.
	 * @return The Response object for the GET request.
	 */
    public static Response performJsonGet(String endPoint, String pathParam, Object pathParamValue) {  
        RequestSpecification requestSpecification = getJsonRequestSpecification(pathParam,pathParamValue);
        Response response =  requestSpecification.when().get(endPoint);
        printJsonResponseLogInReport(response);
 		return response;
     }
    
    /**
     * Performs a JSON GET request with OAuth 2.0 authentication.
     *
     * @param endPoint The endpoint URL for the GET request.
     * @param pathParam The path parameter name.
     * @param pathParamValue The value of the path parameter.
     * @param accessToken The OAuth 2.0 access token for authentication.
     * @return A Response object containing the result of the GET request.
     */
    public static Response performJsonGet(String endPoint, String pathParam, Object pathParamValue, String accessToken) {
        
       RequestSpecification requestSpecification = getJsonRequestSpecificationWithOauth2(pathParam,pathParamValue,accessToken);
       Response response =  requestSpecification.when().get(endPoint);
       printJsonResponseLogInReport(response);
	   return response;
    }
    
    /**
     * Performs a JSON GET request with OAuth 2.0 authentication.
     *
     * @param endPoint The endpoint URL for the GET request.
     * @param accessToken The OAuth 2.0 access token for authentication.
     * @return A Response object containing the result of the GET request.
     */
    public static Response performJsonGet(String endPoint,String accessToken) {
      
     RequestSpecification requestSpecification = getJsonRequestSpecificationWithOauth2(accessToken);
     Response response =  requestSpecification.when().get(endPoint);
     printJsonResponseLogInReport(response);
	 return response;
    }   
    
    /**
     * Performs a POST request with XML payload (using a POJO) and logs request and response details in the Extent report.
     * @param endPoint The API endpoint for the request.
     * @param requestPayloadAsPojo The XML request payload as a POJO (Plain Old Java Object).
     * @param headers The headers to be included in the request.
     * @return The Response object for the POST request.
     * @throws JsonProcessingException If there is an issue processing the JSON content.
     */
    public static Response performXmlPost(String endPoint, Object requestPayloadAsPojo, Map<String,String>headers) throws JsonProcessingException {
    	String newUserXml = new XmlMapper().writeValueAsString(requestPayloadAsPojo);
        RequestSpecification requestSpecification = getXmlRequestSpecification(endPoint, newUserXml, headers);
        Response response =  requestSpecification.post();
        printXmlRequestLogInReport(requestSpecification);
        printXmlResponseLogInReport(response);
        return response;
    }
    
    /**
     * Performs a JSON DELETE request to the specified endpoint with optional path parameters.
     *
     * @param endPoint       The API endpoint to send the DELETE request to.
     * @param pathParam      The path parameter name (can be null if no path parameter is needed).
     * @param pathParamValue The value of the path parameter.
     * @return A Response object containing the server's response to the DELETE request.
     */
	public static Response performJsonDelete(String endPoint, String pathParam, Object pathParamValue) {
		 RequestSpecification requestSpecification = getJsonRequestSpecification(pathParam,pathParamValue);
	       Response response =  requestSpecification.when().delete(endPoint);
	       printJsonResponseLogInReport(response);
		   return response;
	}
	
	/**
	 * Performs a JSON DELETE request to the specified endpoint with optional path parameters
	 * and includes OAuth 2.0 authentication using the provided access token.
	 *
	 * @param endPoint       The API endpoint to send the DELETE request to.
	 * @param pathParam      The path parameter name (can be null if no path parameter is needed).
	 * @param pathParamValue The value of the path parameter.
	 * @param accessToken    The OAuth 2.0 access token for authentication.
	 * @return A Response object containing the server's response to the DELETE request.
	 */
	public static Response performJsonDelete(String endPoint, String pathParam, Object pathParamValue, String accessToken) {
		 RequestSpecification requestSpecification = getJsonRequestSpecificationWithOauth2(pathParam,pathParamValue,accessToken);
         Response response =  requestSpecification.when().delete(endPoint);
         printJsonResponseLogInReport(response);
	     return response;
	}
	
	/**
	 * Performs a JSON Patch operation using OAuth2 authentication.
	 *
	 * @param endPoint        The API endpoint URL for the PATCH request.
	 * @param requestPayload  The JSON payload to be included in the request body.
	 * @param pathParam       The name of the path parameter in the endpoint URL.
	 * @param pathParamValue  The value of the path parameter.
	 * @param accessToken     The OAuth2 access token for authentication.
	 * @return                The response object containing the result of the PATCH request.
	 */
	public static Response performJsonPatch(String endPoint, Map<String, Object> requestPayload, String pathParam, Object pathParamValue, String accessToken) {
		RequestSpecification requestSpecification = getJsonRequestSpecificationWithOauth2(requestPayload,pathParam,pathParamValue,accessToken);
    	Response response =  requestSpecification.when().patch(endPoint);
    	printJsonRequestLogInReport(requestSpecification);
    	printJsonResponseLogInReport(response);
    	return response;
	}    
	
	/**
	 * Performs a JSON PUT operation using OAuth2 authentication.
	 *
	 * @param endPoint        The API endpoint URL for the PUT request.
	 * @param requestPayload  The JSON payload to be included in the request body.
	 * @param pathParam       The name of the path parameter in the endpoint URL.
	 * @param pathParamValue  The value of the path parameter.
	 * @param accessToken     The OAuth2 access token for authentication.
	 * @return                The response object containing the result of the PUT request.
	 */
	public static Response performJsonPut(String endPoint, Map<String, Object> requestPayload, String pathParam, Object pathParamValue, String accessToken) {
		RequestSpecification requestSpecification = getJsonRequestSpecification(accessToken,requestPayload);
    	Response response =  requestSpecification.pathParam(pathParam,pathParamValue).when().put(endPoint);
    	printJsonRequestLogInReport(requestSpecification);
    	printJsonResponseLogInReport(response);
    	return response;
	}

	    
    
}
