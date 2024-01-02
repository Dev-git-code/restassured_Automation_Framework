package restUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

import static io.restassured.RestAssured.given;

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
	 * Performs a GET request with JSON content and logs request and response details in the Extent report.
	 * @param endPoint The API endpoint for the request.
	 * @param pathParam The path parameter name.
	 * @param pathParamValue The path parameter value.
	 * @return The Response object for the GET request.
	 */
    public static Response performJsonGet(String endPoint, String pathParam, Object pathParamValue) {
        Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam(pathParam,pathParamValue)
				.when()
					.get(endPoint);	
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
    

    
    
    
}
