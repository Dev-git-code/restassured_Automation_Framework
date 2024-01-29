package restUtils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JsonRequestUtils {
	
    
    /**
     * Performs a POST request with JSON payload and logs request and response details in the Extent report.
     * @param endPoint The API endpoint for the request.
     * @param requestPayload The JSON request payload as a string.
     * @param headers The headers to be included in the request.
     * @return The Response object for the POST request.
     */
    public static Response performJsonPost(String endPoint, String requestPayload, Map<String,String>headers) {
        RequestSpecification requestSpecification = GetRequestSpecification.jsonRequest(endPoint, requestPayload, headers);
        Response response =  requestSpecification.post();
        LogInReportUtil.printJsonRequest(requestSpecification);
        LogInReportUtil.printJsonResponse(response);
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
        RequestSpecification requestSpecification = GetRequestSpecification.jsonRequest(endPoint, requestPayload, headers);
        Response response =  requestSpecification.post();
        LogInReportUtil.printJsonRequest(requestSpecification);
        LogInReportUtil.printJsonResponse(response);
        return response;
    }
    

    public static Response performJsonPost(String endPoint,  Object requestPayload, Map<String,String>headers) {
        RequestSpecification requestSpecification = GetRequestSpecification.jsonRequest(endPoint, requestPayload, headers);
        Response response =  requestSpecification.post();
        LogInReportUtil.printJsonRequest(requestSpecification);
        LogInReportUtil.printJsonResponse(response);
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
    	RequestSpecification requestSpecification = GetRequestSpecification.jsonRequestWithOauth2(requestPayload,accessToken);
    	Response response =  requestSpecification.when().post(endPoint);
    	LogInReportUtil.printJsonRequest(requestSpecification);
        LogInReportUtil.printJsonResponse(response);
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
        RequestSpecification requestSpecification = GetRequestSpecification.jsonRequest(pathParam,pathParamValue);
        Response response =  requestSpecification.when().get(endPoint);
        LogInReportUtil.printJsonResponse(response);
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
        
       RequestSpecification requestSpecification = GetRequestSpecification.jsonRequestWithOauth2(pathParam,pathParamValue,accessToken);
       Response response =  requestSpecification.when().get(endPoint);
       LogInReportUtil.printJsonResponse(response);
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
      
     RequestSpecification requestSpecification = GetRequestSpecification.jsonRequestWithOauth2(accessToken);
     Response response =  requestSpecification.when().get(endPoint);
     LogInReportUtil.printJsonResponse(response);
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
		 RequestSpecification requestSpecification = GetRequestSpecification.jsonRequest(pathParam,pathParamValue);
	       Response response =  requestSpecification.when().delete(endPoint);
	        LogInReportUtil.printJsonResponse(response);
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
		 RequestSpecification requestSpecification = GetRequestSpecification.jsonRequestWithOauth2(pathParam,pathParamValue,accessToken);
         Response response =  requestSpecification.when().delete(endPoint);
         LogInReportUtil.printJsonResponse(response);
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
		RequestSpecification requestSpecification =GetRequestSpecification.jsonRequestWithOauth2(requestPayload,pathParam,pathParamValue,accessToken);
    	Response response =  requestSpecification.when().patch(endPoint);
    	LogInReportUtil.printJsonRequest(requestSpecification);
    	LogInReportUtil.printJsonResponse(response);
    	return response;
	}    
	
	public static Response performJsonPatch(String endPoint, Object requestPayload, String pathParam, Object pathParamValue, String accessToken) {
		RequestSpecification requestSpecification =GetRequestSpecification.jsonRequestWithOauth2(requestPayload,pathParam,pathParamValue,accessToken);
    	Response response =  requestSpecification.when().patch(endPoint);
    	LogInReportUtil.printJsonRequest(requestSpecification);
    	LogInReportUtil.printJsonResponse(response);
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
		RequestSpecification requestSpecification = GetRequestSpecification.jsonRequestWithOauth2(requestPayload,accessToken);
    	Response response =  requestSpecification.pathParam(pathParam,pathParamValue).when().put(endPoint);
    	LogInReportUtil.printJsonRequest(requestSpecification);
    	LogInReportUtil.printJsonResponse(response);
    	return response;
	}

    
}
