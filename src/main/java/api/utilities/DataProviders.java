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
        return excel.getTestData(method.getName(), "BAT_DATA");
    }
    
    
    public static String getRoutes(String route) {
    	ExcelUtil excel = new ExcelUtil();
    	Object[][] data = excel.getTestData("Routes", "BAT_DATA");
    	Map<String, String> routes= new HashMap<>();
    	for(Object[] obj:data) {
            for(Object o:obj) {
            	ObjectMapper oMapper = new ObjectMapper();
                Map<String,String > map = oMapper.convertValue(o, Map.class);
                routes.put(map.get("RouteName"),map.get("URL"));
            }
    	}
    	
    	String baseUrl = routes.get("baseUrl");
    	routes.remove("baseUrl");
    	for(String s:routes.keySet()) {
    		routes.replace(s, baseUrl+routes.get(s));
    	}
    	return routes.get(route);
    	 
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
    public static String getRoutesFromSwaggerJson(String reqName) throws FileNotFoundException, IOException, ParseException {
        // Create a JSON parser object
        JSONParser parser = new JSONParser();

        // Parse the JSON file using the parser object
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Users\\DELL\\Downloads\\jobify.json"));
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
            String endpoint = baseUrl + path; // Endpoints for the API
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
                urlMap.put(requestSummary, endpoint);
            }
        }
       
        // Return the endpoint corresponding to the given request name
        return urlMap.get(reqName);
    }
    
    public static Map<String,JSONObject> getRequestBody() throws FileNotFoundException, IOException, ParseException{
     JSONParser parser = new JSONParser();
   	 JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Users\\DELL\\Downloads\\jobify.json"));

   	// System.out.println(jsonObject.toJSONString());
        JSONObject pathsObject = (JSONObject) jsonObject.get("paths");
        Map<String, JSONObject> requestBodyMap = new HashMap<String,JSONObject>();

        // Iterate through the paths in the Swagger JSON
        for (Iterator pathIterator = pathsObject.keySet().iterator(); pathIterator.hasNext();) {
            String path = (String) pathIterator.next(); // Swagger items like info, server, etc.

            // Extract information for each request type (GET, POST, etc.) for a path
            JSONObject pathObject = (JSONObject) pathsObject.get(path);
            for (Iterator requestTypeIterator = pathObject.keySet().iterator(); requestTypeIterator.hasNext();) {
                String requestType = (String) requestTypeIterator.next(); // Request type like post, get, delete, etc.
                // System.out.println(requestType);
                if (requestType.equals("parameters")) continue;

                // Extract request request body
                JSONObject requestInfoObject = (JSONObject) pathObject.get(requestType);
                String requestSummary = requestInfoObject.get("summary").toString();
                Optional<Object> requestBody= Optional.ofNullable(requestInfoObject.get("requestBody"));
                if(requestBody.isPresent()) {
               	 //System.out.println(requestInfoObject.get("requestBody")); 
               	 String examplePath ="$.paths[\""+path+ "\"]."+ requestType +".requestBody.content[\"application/json\"].example";
               	 JSONObject requestBodyExample = JsonPath.read(jsonObject,examplePath);
               	 //System.out.println(requestBodyExample);
               	 requestBodyMap.put(requestSummary,requestBodyExample);            	 
                }
                
            }
            
        }
        return requestBodyMap;
    }

}


