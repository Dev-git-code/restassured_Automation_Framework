package restUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;



public class RestUtils {

    private static RequestSpecification getXmlRequestSpecification(String endPoint, Object requestPayload, Map<String,String>headers) {
        return RestAssured.given()
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .body(requestPayload);
    }
    
    private static RequestSpecification getJsonRequestSpecification(String endPoint, Object requestPayload, Map<String,String>headers) {
        return RestAssured.given()
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestPayload);
    }

    private static void printJsonRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetails("Endpoint is " + queryableRequestSpecification.getBaseUri());
        ExtentReportManager.logInfoDetails("Method is " + queryableRequestSpecification.getMethod());
        ExtentReportManager.logInfoDetails("Headers are ");
        ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Request body is ");
        ExtentReportManager.logJson(queryableRequestSpecification.getBody());
    }
    private static void printXmlRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetails("Endpoint is " + queryableRequestSpecification.getBaseUri());
        ExtentReportManager.logInfoDetails("Method is " + queryableRequestSpecification.getMethod());
        ExtentReportManager.logInfoDetails("Headers are ");
        ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Request body is ");
        ExtentReportManager.logXml(queryableRequestSpecification.getBody());
    } 

    public static void printJsonResponseLogInReport(Response response) {
        ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response Headers are ");
        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body is ");
        ExtentReportManager.logJson(response.getBody().prettyPrint());
    }
    
    public static void printXmlResponseLogInReport(Response response) {
        ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response Headers are ");
        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body is ");
        ExtentReportManager.logXml(response.getBody().prettyPrint());
    }

    public static Response performPost(String endPoint, String requestPayload, Map<String,String>headers) {
        RequestSpecification requestSpecification = getJsonRequestSpecification(endPoint, requestPayload, headers);
        Response response =  requestSpecification.post();
        printJsonRequestLogInReport(requestSpecification);
        printJsonResponseLogInReport(response);
        return response;
    }

    public static Response performPost(String endPoint, Map<String, Object> requestPayload, Map<String,String>headers) {
        RequestSpecification requestSpecification = getJsonRequestSpecification(endPoint, requestPayload, headers);
        Response response =  requestSpecification.post();
        printJsonRequestLogInReport(requestSpecification);
        printJsonResponseLogInReport(response);
        return response;
    }

    public static Response performJsonPost(String endPoint, Object requestPayloadAsPojo, Map<String,String>headers) {
        RequestSpecification requestSpecification = getJsonRequestSpecification(endPoint, requestPayloadAsPojo, headers);
        Response response =  requestSpecification.post();
        printJsonRequestLogInReport(requestSpecification);
        printJsonResponseLogInReport(response);
        return response;
    }
    
    public static Response performXmlPost(String endPoint, Object requestPayloadAsPojo, Map<String,String>headers) throws JsonProcessingException {
    	String newUserXml = new XmlMapper().writeValueAsString(requestPayloadAsPojo);
        RequestSpecification requestSpecification = getXmlRequestSpecification(endPoint, newUserXml, headers);
        Response response =  requestSpecification.post();
        printXmlRequestLogInReport(requestSpecification);
        printXmlResponseLogInReport(response);
        return response;
    }
}
