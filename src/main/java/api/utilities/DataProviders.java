package api.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jayway.jsonpath.JsonPath;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataProviders {


	@DataProvider(name="Data")
	public String[][] getAllData() throws IOException
	{
		String path=System.getProperty("user.dir")+"//testData//Userdata.xlsx";
		XLUtility xl=new XLUtility(path); 
	
		int rownum=xl.getRowCount("Sheet1");	
		int colcount=xl.getCellCount("Sheet1",1);
		
		String apidata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{		
			for(int j=0;j<colcount;j++)
			{
				apidata[i-1][j]= xl.getCellData("Sheet1",i, j);  
			}
		}
	
		return apidata;
	}
	
	@DataProvider(name="UserNames")
	public String[] getUserNames() throws IOException
	{
		String path=System.getProperty("user.dir")+"//testData//Userdata.xlsx";
		XLUtility xl=new XLUtility(path);
	
		int rownum=xl.getRowCount("Sheet1");	
			
		String apidata[]=new String[rownum];
		
		for(int i=1;i<=rownum;i++)
		{		
			apidata[i-1]= xl.getCellData("Sheet1",i, 1);  
			
		}
	
		return apidata;
	}
	
    @DataProvider(name = "getData")
    public Object[][] getApplicationData(Method method) {
        // Your existing implementation to get application data
    	ExcelUtil excel = new ExcelUtil();
        return excel.getTestData(method.getName(), "User Data");
    }
    
    
    /**
     * Retrieves the URL for a given route.
     *
     * @param route The route name for which the URL is requested.
     * @return The URL corresponding to the given route.
     */
    public static String getRoutes(String route) {
        // Create an instance of ExcelUtil to access Excel data
        ExcelUtil excel = new ExcelUtil();

        // Get the test data for routes from the Excel sheet
        Object[][] data = excel.getTestData("Routes", "User Data");

        // Initialize a Map to store route names and URLs
        Map<String, String> routes = new HashMap<>();

        // Iterate through the test data and populate the routes Map
        for (Object[] obj : data) {
            for (Object o : obj) {
                // Convert the Object to a Map using ObjectMapper
                ObjectMapper oMapper = new ObjectMapper();
                Map<String, String> map = oMapper.convertValue(o, Map.class);
                routes.put(map.get("RouteName"), map.get("URL"));
            }
        }

        // Get the base URL from the routes Map
        String baseUrl = routes.get("baseUrl");

        // Remove the base URL entry from the routes Map
        routes.remove("baseUrl");

        // Concatenate the base URL with each route URL
        for (String s : routes.keySet()) {
            routes.replace(s, baseUrl + routes.get(s));
        }

        // Return the URL corresponding to the given route
        return routes.get(route);
    }

    
  
    /**
     * Retrieves assertion data for a given test method from an Excel sheet.
     *
     * @param method The test method for which assertion data is requested.
     * @return A Map containing assertion data where keys are JSON paths and values are expected values.
     */
    public static Map<String, Object> getAssertionData(Method method) {
        // Create an instance of ExcelUtil to access Excel data
        ExcelUtil excel = new ExcelUtil();

        // Get the test data for the given method from the Excel sheet
        Object[][] data = excel.getTestData(method.getName(), "User Data");

        // Initialize a Map to store assertion data
        Map<String, Object> body = new HashMap<>();

        // Iterate through the test data and populate the body Map
        for (Object[] obj : data) {
            for (Object o : obj) {
                // Convert the Object to a Map using ObjectMapper
                ObjectMapper oMapper = new ObjectMapper();
                Map<String, String> map = oMapper.convertValue(o, Map.class);
                
                // Extract JSON path and expected value from the map and put into the body map
                body.put(map.get("jsonPath"), map.get(" Expected Value"));
            }
        }

        // Return the assertion data
        return body;
    }

    
    /**
     * Retrieves parameter values for a given request from an Excel sheet.
     *
     * @param req The request name for which parameter values are requested.
     * @return A Map containing parameter names and their corresponding values.
     */
    public static Map<String, String> getParameterValue(String req) {
        // Create an instance of ExcelUtil to access Excel data
        ExcelUtil excel = new ExcelUtil();

        // Get the test data for the given request from the Excel sheet
        Object[][] data = excel.getTestData(req + " Parameter", "User Data");

        // Initialize a Map to store parameter values
        Map<String, String> paramMap = new HashMap<>();

        // Iterate through the test data and populate the paramMap
        for (Object[] obj : data) {
            for (Object o : obj) {
                // Convert the Object to a Map using ObjectMapper
                ObjectMapper oMapper = new ObjectMapper();
                paramMap = oMapper.convertValue(o, Map.class);
                System.out.println(paramMap); // Print for debugging
            }
        }

        // Return the paramMap containing parameter values
        return paramMap;
    }
    
    /**
     * Retrieves the endpoint associated with a specific request name from a Swagger JSON file.
     *
     * @param reqName The summary or name of the Swagger request for which the endpoint is sought.
     * @return The endpoint URL corresponding to the given request name.
     * @throws FileNotFoundException If the Swagger JSON file is not found.
     * @throws IOException           If an I/O error occurs while reading the Swagger JSON file.
     * @throws ParseException        If an error occurs during JSON parsing.
     */
    public static Map<String,String> getRoutesFromSwaggerJson() throws FileNotFoundException, IOException, ParseException {
        // Create a JSON parser object
        JSONParser parser = new JSONParser();

        // Parse the JSON file using the parser object
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Users\\DELL\\3D Objects\\automation framework\\RestAssuredAutomation\\testData\\jobify.json"));
        // System.out.println(jsonObject.toJSONString());
        JSONObject pathsObject = (JSONObject) jsonObject.get("paths");

        // Extract the base URL from the "servers" array in the Swagger JSON
        JSONArray serverArray = (JSONArray) jsonObject.get("servers");
        JSONObject server1 = (JSONObject) serverArray.get(0);
        String baseUrl = server1.get("url").toString();
        Map<String, String> urlMap = new HashMap<String, String>();

        // Iterate through the paths in the Swagger JSON
        for (Iterator pathIterator = pathsObject.keySet().iterator(); pathIterator.hasNext();) {
            String path = (String) pathIterator.next(); // Swagger items like info, server, etc.
            //String endpoint = baseUrl + path; // Endpoints for the API
            // System.out.println(endpoint);

            // Extract information for each request type (GET, POST, etc.) for a path
            JSONObject pathObject = (JSONObject) pathsObject.get(path);
            for (Iterator requestTypeIterator = pathObject.keySet().iterator(); requestTypeIterator.hasNext();) {
                String requestType = (String) requestTypeIterator.next(); // Request type like post, get, delete, etc.
                // System.out.println(requestType);
                if (requestType.equals("parameters")) continue;

                // Extract request information like tags, summary, parameters, etc.
                JSONObject requestInfoObject = (JSONObject) pathObject.get(requestType);
                String requestSummary = requestInfoObject.get("summary").toString();
                urlMap.put("baseUrl", baseUrl);
                urlMap.put(requestSummary, path);
            }
        }
       
        // Return the endpoint corresponding to the given request name
        return urlMap;
    }
    
    /**
     * Parses a Swagger JSON file to extract and retrieve request bodies associated with different API paths and request types.
     *
     * @return A map where the key is the summary of the request and the value is the corresponding request body in JSON format.
     * @throws FileNotFoundException If the specified file path for the Swagger JSON is not found.
     * @throws IOException           If an I/O exception occurs while reading the Swagger JSON file.
     * @throws ParseException        If an error occurs during the parsing of the Swagger JSON.
     */
    public static Map<String, JSONObject> getRequestBody() throws FileNotFoundException, IOException, ParseException {
        // Create a JSONParser to parse the Swagger JSON file
        JSONParser parser = new JSONParser();

        // Read the Swagger JSON file and parse it into a JSONObject
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Users\\DELL\\3D Objects\\automation framework\\RestAssuredAutomation\\testData\\jobify.json"));

        // Extract the "paths" object from the Swagger JSON
        JSONObject pathsObject = (JSONObject) jsonObject.get("paths");

        // Initialize a map to store request bodies with their corresponding summaries
        Map<String, JSONObject> requestBodyMap = new HashMap<>();

        // Iterate through the paths in the Swagger JSON
        for (Iterator pathIterator = pathsObject.keySet().iterator(); pathIterator.hasNext();) {
            String path = (String) pathIterator.next(); // Swagger items like info, server, etc.

            // Extract information for each request type (GET, POST, etc.) for a path
            JSONObject pathObject = (JSONObject) pathsObject.get(path);
            for (Iterator requestTypeIterator = pathObject.keySet().iterator(); requestTypeIterator.hasNext();) {
                String requestType = (String) requestTypeIterator.next(); // Request type like post, get, delete, etc.

                // Skip iteration if the request type is "parameters"
                if (requestType.equals("parameters")) continue;

                // Extract request body information
                JSONObject requestInfoObject = (JSONObject) pathObject.get(requestType);
                String requestSummary = requestInfoObject.get("summary").toString();

                // Check if the request has a request body
                Optional<Object> requestBody = Optional.ofNullable(requestInfoObject.get("requestBody"));
                if (requestBody.isPresent()) {
                    // Build the JSONPath expression to locate the request body example in the Swagger JSON
                    String examplePath = "$.paths[\"" + path + "\"]." + requestType +
                                        ".requestBody.content[\"application/json\"].example";

                    // Use JSONPath to extract the request body example
                    JSONObject requestBodyExample = JsonPath.read(jsonObject, examplePath);

                    // Put the request summary and corresponding request body example into the map
                    requestBodyMap.put(requestSummary, requestBodyExample);
                }else {
                	
              	  JSONObject jsonPathObject = new JSONObject();
              	  jsonPathObject.put(" Expected Value", "Enter Value");
               	  jsonPathObject.put("jsonPath","Enter Value");
               	  
              	  requestBodyMap.put(requestSummary,jsonPathObject);
                }
            }
        }

        // Return the map containing request summaries and their corresponding request bodies
        return requestBodyMap;
    }
    
    
    /**
     * Extracts parameters information from Swagger JSON.
     *
     * @return A map containing parameter information with their corresponding summaries.
     * @throws FileNotFoundException If the JSON file is not found.
     * @throws IOException           If there is an issue reading the JSON file.
     * @throws ParseException        If there is an issue parsing the JSON file.
     */
    public static Map<String, JSONObject> getParamMap() throws FileNotFoundException, IOException, ParseException {
        // Initialize JSON parser
        JSONParser parser = new JSONParser();

        // Read and parse Swagger JSON file
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Users\\DELL\\3D Objects\\automation framework\\RestAssuredAutomation\\testData\\jobify.json"));

        // Extract the 'paths' object from Swagger JSON
        JSONObject pathsObject = (JSONObject) jsonObject.get("paths");

        // Initialize map to store parameter information
        Map<String, JSONObject> paramMap = new HashMap<>();

        // Iterate through the paths in the Swagger JSON
        for (Iterator pathIterator = pathsObject.keySet().iterator(); pathIterator.hasNext();) {
            String path = (String) pathIterator.next(); // Swagger items like info, server, etc.

            // Extract information for each request type (GET, POST, etc.) for a path
            JSONObject pathObject = (JSONObject) pathsObject.get(path);

            for (Iterator requestTypeIterator = pathObject.keySet().iterator(); requestTypeIterator.hasNext();) {
                String requestType = (String) requestTypeIterator.next(); // Request type like post, get, delete, etc.

                // Check if the request type is 'parameters'
                if (requestType.equals("parameters")) {
                    String paramPath = path;
                    String paramNamePath = "$.paths[\"" + path + "\"].parameters[0].description";
                    String paramName = JsonPath.read(jsonObject, paramNamePath);
                    JSONObject paramNameObject = new JSONObject();
                    paramNameObject.put(paramName, "Enter value");
                    String paramObjectPath = "$.paths[\"" + path + "\"]";
                    JSONObject paramObject = JsonPath.read(jsonObject, paramObjectPath);

                    // Iterate through other properties of the path excluding 'parameters'
                    for (Iterator paramObjectIterator = paramObject.keySet().iterator(); paramObjectIterator.hasNext();) {
                        String requestName = (String) paramObjectIterator.next();

                        // Skip 'parameters' property
                        if (requestName.equals("parameters")) continue;

                        // Extract summary information for each request type
                        String requestNamePath = "$.paths[\""+path+ "\"]." + requestName + ".summary";
                        String paramRequestName = JsonPath.read(jsonObject, requestNamePath).toString() + " Parameter";

                        paramMap.put(paramRequestName, paramNameObject);
                    }
                }
            }
        }

        return paramMap;
    }

}


