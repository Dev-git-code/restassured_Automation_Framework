package restUtils;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

public class GetRequestSpecification {
	/**
	 * Returns a RequestSpecification for an XML request with the specified endpoint, request payload,
	 * and headers. Sets up the request with base URI, headers, content type, and body for XML.
	 * @param endPoint The API endpoint for the request.
	 * @param requestPayload The request payload object.
	 * @param headers The headers to be included in the request.
	 * @return A RequestSpecification for an XML request.
	 */
    public static RequestSpecification xmlRequest(String endPoint, Object requestPayload, Map<String,String>headers) {
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
    public static RequestSpecification xmlRequest() {
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
    public static RequestSpecification xmlRequestWithOauth2(String accessToken) {
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
    public static RequestSpecification jsonRequest(String endPoint, Object requestPayload, Map<String,String>headers) {
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
    public static RequestSpecification jsonRequest() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
    
    
    public static RequestSpecification jsonRequest(String pathParam, Object pathParamValue) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam(pathParam,pathParamValue);
    }
    
    /**
     * Returns a RequestSpecification configured for JSON content type with OAuth 2.0 authentication.
     *
     * @param accessToken The OAuth 2.0 access token to be used for authentication.
     * @return A RequestSpecification instance configured for JSON content type with OAuth 2.0 authentication.
     */
    public static RequestSpecification jsonRequestWithOauth2(String accessToken) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().oauth2(accessToken);
    }
    
    
    public static RequestSpecification jsonRequestWithOauth2(String pathParam, Object pathParamValue, String accessToken) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam(pathParam,pathParamValue)
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
    public static RequestSpecification jsonRequestWithOauth2(Object requestPayload,String accessToken) {
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
    public static RequestSpecification jsonRequestWithOauth2(Object requestPayload,String pathParam,Object pathParamValue,String accessToken) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam(pathParam, pathParamValue)
                .body(requestPayload)
                .auth().oauth2(accessToken);
    }

}
