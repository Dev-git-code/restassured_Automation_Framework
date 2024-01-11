package restUtils;

import com.aventstack.extentreports.markuputils.MarkupHelper;


import io.restassured.response.Response;
import reporting.ExtentReportManager;
import reporting.Setup;
import restUtils.AssertionKeys;

import java.util.*;

import org.testng.Assert;

public class AssertionUtils {
	
	/**
	 * <ul>
	 *   <li>Performs assertions on values extracted from a REST API response using JSONPath expressions.</li>
	 *   <li>Compares actual values against expected values provided in a map and logs results in an Extent report.</li>
	 *   <li>Iterates through JSONPath expressions, extracts actual values using JSONPath, and compares them with expected values. Logs results in a tabular format in an Extent report.</li>
	 *   <li>If all assertions pass, logs a success message; otherwise, logs a failure message.</li>
	 *   <li>Table columns: JSONPath, Expected Value, Actual Value, Result ("MATCHED ✅" or "NOT_MATCHED ❌").</li>
	 *   <li>If JSONPath is not found in the response, "VALUE_NOT_FOUND" is marked in the Actual Value column.</li>
	 * </ul>
	 * @param response REST API response object to be validated.
	 * @param expectedValuesMap Map with JSONPath expressions as keys and corresponding expected values.
	 
	 */
    public static void assertExpectedValuesWithJsonPath(Response response, Map<String, Object> expectedValuesMap) {
        List<AssertionKeys> actualValuesMap = new ArrayList<>();
        // Table headers
        actualValuesMap.add(new AssertionKeys("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        boolean allMatched = true;
        // Iterate to extract value from response using jsonpath
        Set<String> jsonPaths =  expectedValuesMap.keySet();
        for(String jsonPath : jsonPaths) {
            Optional<Object> actualValue = Optional.ofNullable(response.jsonPath().get(jsonPath));
            if(actualValue.isPresent()) {
                Object value = actualValue.get();
                // Assert actual and expected values
                if(value.equals(expectedValuesMap.get(jsonPath)))
                    // if value is matched then add details
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "MATCHED ✅"));
                else {
                    // if single assertion is failed then to update final result as failure
                    allMatched = false;
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "NOT_MATCHED ❌"));
                }
            }
            // if jsonpath does not exist in the response
            else {
                allMatched = false;
                actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), "VALUE_NOT_FOUND", "NOT_MATCHED ❌"));
            }
        }
        // To decide final result
        if(allMatched)
            ExtentReportManager.logPassDetails("All assertions are passed. 😊😊😊😊😊");
        else
            ExtentReportManager.logFailureDetails("All assertions are not passed. 😒😒😒😒😒");

        // To log the details in a tabular format in extent report
        String[][] finalAssertionsMap = actualValuesMap.stream().map(assertions -> new String[] {assertions.getJsonPath(),
                String.valueOf(assertions.getExpectedValue()), String.valueOf(assertions.getActualValue()), assertions.getResult()})
                .toArray(String[][] :: new);
        Setup.extentTest.get().info(MarkupHelper.createTable(finalAssertionsMap));
    }
    
    public static void assertExpectedValuesWithJsonPathString(Response response, Map<String, String> expectedValuesMap) {
        List<AssertionKeys> actualValuesMap = new ArrayList<>();
        // Table headers
        actualValuesMap.add(new AssertionKeys("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        boolean allMatched = true;
        // Iterate to extract value from response using jsonpath
        Set<String> jsonPaths =  expectedValuesMap.keySet();
        for(String jsonPath : jsonPaths) {
            Optional<String> actualValue = Optional.ofNullable(response.jsonPath().get(jsonPath));
            if(actualValue.isPresent()) {
                Object value = actualValue.get();
                // Assert actual and expected values
                if(value.equals(expectedValuesMap.get(jsonPath)))
                    // if value is matched then add details
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "MATCHED ✅"));
                else {
                    // if single assertion is failed then to update final result as failure
                    allMatched = false;
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "NOT_MATCHED ❌"));
                }
            }
            // if jsonpath does not exist in the response
            else {
                allMatched = false;
                actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), "VALUE_NOT_FOUND", "NOT_MATCHED ❌"));
            }
        }
        // To decide final result
        if(allMatched)
            ExtentReportManager.logPassDetails("All assertions are passed. 😊😊😊😊😊");
        else
            ExtentReportManager.logFailureDetails("All assertions are not passed. 😒😒😒😒😒");

        // To log the details in a tabular format in extent report
        String[][] finalAssertionsMap = actualValuesMap.stream().map(assertions -> new String[] {assertions.getJsonPath(),
                String.valueOf(assertions.getExpectedValue()), String.valueOf(assertions.getActualValue()), assertions.getResult()})
                .toArray(String[][] :: new);
        Setup.extentTest.get().info(MarkupHelper.createTable(finalAssertionsMap));
    }
    
    /**
     * <ul>
     *   <li>Performs assertions on values extracted from a REST API response using XML XPath expressions.</li>
     *   <li>Compares actual values against expected values provided in a map and logs results in an Extent report.</li>
     *   <li>Iterates through XML XPath expressions, extracts actual values using XPath, and compares them with expected values. Logs results in a tabular format in an Extent report.</li>
     *   <li>If all assertions pass, logs a success message; otherwise, logs a failure message.</li>
     *   <li>Table columns: XPath, Expected Value, Actual Value, Result ("MATCHED ✅" or "NOT_MATCHED ❌").</li>
     *   <li>If XPath is not found in the response, "VALUE_NOT_FOUND" is marked in the Actual Value column.</li>
     * </ul>
     * @param response REST API response object to be validated.
     * @param expectedValuesMap Map with XML XPath expressions as keys and corresponding expected values.
     */
    public static void assertExpectedValuesWithXmlPath(Response response, Map<String, Object> expectedValuesMap) {
        List<AssertionKeys> actualValuesMap = new ArrayList<>();
        // Table headers
        actualValuesMap.add(new AssertionKeys("XML_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        boolean allMatched = true;
        // Iterate to extract value from response using xmlpath
        Set<String> xmlPaths =  expectedValuesMap.keySet();
        for(String xmlPath : xmlPaths) {
            Optional<Object> actualValue = Optional.ofNullable(response.xmlPath().get(xmlPath));
            if(actualValue.isPresent()) {
                Object value = actualValue.get();
                // Assert actual and expected values
                if(value.equals(expectedValuesMap.get(xmlPath)))
                    // if value is matched then add details
                    actualValuesMap.add(new AssertionKeys(xmlPath, expectedValuesMap.get(xmlPath), value, "MATCHED ✅"));
                else {
                    // if single assertion is failed then to update final result as failure
                    allMatched = false;
                    actualValuesMap.add(new AssertionKeys(xmlPath, expectedValuesMap.get(xmlPath), value, "NOT_MATCHED ❌"));
                }
            }
            // if xml does not exist in the response
            else {
                allMatched = false;
                actualValuesMap.add(new AssertionKeys(xmlPath, expectedValuesMap.get(xmlPath), "VALUE_NOT_FOUND", "NOT_MATCHED ❌"));
            }
        }
        // To decide final result
        if(allMatched)
            ExtentReportManager.logPassDetails("All assertions are passed. 😊😊😊😊😊");
        else
            ExtentReportManager.logFailureDetails("All assertions are not passed. 😒😒😒😒😒");

        // To log the details in a tabular format in extent report
        String[][] finalAssertionsMap = actualValuesMap.stream().map(assertions -> new String[] {assertions.getJsonPath(),
                String.valueOf(assertions.getExpectedValue()), String.valueOf(assertions.getActualValue()), assertions.getResult()})
                .toArray(String[][] :: new);
        Setup.extentTest.get().info(MarkupHelper.createTable(finalAssertionsMap));
    }
    
    
    /**
     * Asserts that the actual string is equal to the expected string and logs the result in an Extent report.
     *
     * <p>Compares the provided actual and expected strings using {@link Assert#assertEquals(Object, Object)}.
     * If the strings are equal, logs a pass message using {@link ExtentReportManager#logPassDetails(String)}; otherwise,
     * logs a failure message with details of the assertion.
     *
     * @param actual The actual string to be compared.
     * @param expected The expected string for comparison.
     * @param log The log message to be included in the Extent report.
     */
    
    public static void AssertThat(String actual, String expected, String log) {
    	Assert.assertEquals(expected, actual);
    	if(expected.equals(actual)) {
    		ExtentReportManager.logPassDetails(log);
	    }else {
	    	ExtentReportManager.logFailureDetails("assertion failed: Expected: "+ expected + " Found: "+actual);
	    }
    }
    
    /**
    * Asserts that the actual integer value is equal to the expected integer value and logs the result in an Extent report.
    *
    * <p>Compares the provided actual and expected integer values using {@link Assert#assertEquals(int, int)}.
    * If the values are equal, logs a pass message using {@link ExtentReportManager#logPassDetails(String)}; otherwise,
    * logs a failure message with details of the assertion.
    *
    * @param actual The actual integer value to be compared.
    * @param expected The expected integer value for comparison.
    * @param log The log message to be included in the Extent report.
    */
    
    public static void AssertThat(int actual, int expected, String log) {
    	Assert.assertEquals(expected, actual);	
    	if(expected==actual) {
    		ExtentReportManager.logPassDetails(log);
	    }else {
	    	ExtentReportManager.logFailureDetails("assertion failed: Expected: "+ expected + " Found: "+actual);
	    }
    }
    
    /**
     * <ul>
     *   <li>Asserts that the actual headers in the given response match the expected headers.</li>
     *   <li>Compares each header in the response against the expected values provided in a map.</li>
     *   <li>Logs the results in a tabular format in an Extent report.</li>
     *   <li>Asserts the values using {@link Assert#assertEquals(Object, Object)}.</li>
     *   <li>Table columns in the Extent report: Header, Expected Value, Actual Value, Result ("MATCHED ✅" or "NOT_MATCHED ❌").</li>
     *   <li>If a header is not found in the response, "VALUE_NOT_FOUND" is marked in the Actual Value column.</li>
     *   <li>If all assertions pass, logs a success message; otherwise, logs a failure message.</li>
     * </ul>
     *
     * @param response The REST API response object to be validated.
     * @param expected A map with header names as keys and corresponding expected values.
     */
    
    public static void AssertExpectedHeaders(Response response,Map<String,String> expected) {
    	Set<String> headers = expected.keySet();
    	List<AssertionKeys> actualValuesMap = new ArrayList<>();
    	boolean allMatched = true;
        // Table headers
        actualValuesMap.add(new AssertionKeys("Header", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        
    	for(String header:headers){
    		String value = response.header(header);
    		Optional<String> actualValue = Optional.ofNullable(response.header(header));
    		//Assert.assertEquals(value, expected.get(header));
    		if(actualValue.isPresent()) {
    			if(value.equals(expected.get(header))){
        			actualValuesMap.add(new AssertionKeys(header, expected.get(header), value, "MATCHED ✅"));
        		}else {
                        // if single assertion is failed then to update final result as failure
                        allMatched = false;
                        actualValuesMap.add(new AssertionKeys(header, expected.get(header), value, "NOT_MATCHED ❌"));
                    }
    		}else {
    			allMatched = false;
                actualValuesMap.add(new AssertionKeys(header, expected.get(header), "VALUE_NOT_FOUND", "NOT_MATCHED ❌"));
    		}
    		
        }
        // To decide final result
        if(allMatched)
            ExtentReportManager.logPassDetails("All assertions are passed. 😊😊😊😊😊");
        else
            ExtentReportManager.logFailureDetails("All assertions are not passed. 😒😒😒😒😒");

        // To log the details in a tabular format in extent report
        String[][] finalAssertionsMap = actualValuesMap.stream().map(assertions -> new String[] {assertions.getJsonPath(),
                String.valueOf(assertions.getExpectedValue()), String.valueOf(assertions.getActualValue()), assertions.getResult()})
                .toArray(String[][] :: new);
        Setup.extentTest.get().info(MarkupHelper.createTable(finalAssertionsMap));
        for(String header:headers){
    		String value = response.header(header);
    		Assert.assertEquals(value, expected.get(header));
        }
    }
    
    
}

