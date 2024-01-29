package restUtils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.specification.QueryableRequestSpecification;

import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


public class XmlRequestUtils {
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
        RequestSpecification requestSpecification = GetRequestSpecification.xmlRequest(endPoint, newUserXml, headers);
        Response response =  requestSpecification.post();
        LogInReportUtil.printJsonRequest(requestSpecification);
        LogInReportUtil.printJsonResponse(response);
        return response;
    }
    
}
