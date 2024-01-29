package restUtils;

import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

public class LogInReportUtil {
	/**
	 * Logs the details of a JSON request in the Extent report, including endpoint, method, headers,
	 * and request body.
	 * @param requestSpecification The RequestSpecification for the JSON request.
	 */
    public static void printJsonRequest(RequestSpecification requestSpecification) {
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
    public static void printXmlRequest(RequestSpecification requestSpecification) {
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
    public static void printJsonResponse(Response response) {
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
    public static void printXmlResponse(Response response) {
        ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response Headers are ");
        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body is ");
        ExtentReportManager.logXml(response.getBody().prettyPrint());
    }
}
