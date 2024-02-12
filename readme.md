# Package: `api.utilities`

## Class: `DataProviders`

The `DataProviders` class serves as a utility for retrieving and processing data for testing purposes.

The `getAllData()` method retrieves all data from an Excel sheet located at a specified path. It returns a two-dimensional array of strings containing the retrieved data. This method is typically used when all data from the Excel sheet is required for testing multiple scenarios.

The `getUserNames()` method specifically retrieves user names from the  Excel sheet located at a specified path. It returns an array of strings containing the user names. This method is useful when only user names are needed from the Excel sheet, often in scenarios where user-specific tests are conducted.

The `getApplicationData(Method method)` method retrieves application data for a specific test method from the Excel sheet. It takes a `Method` object representing the test method as a parameter and returns a two-dimensional array of objects containing the application data. This method is used to fetch data specific to a particular test method, aiding in parameterized testing.

The `getRoutes(String route)` method retrieves the URL for a given route by parsing test data from the Excel sheet. It takes a route name as a parameter and returns the corresponding URL as a string. This method is helpful for obtaining route URLs required for API testing, facilitating endpoint accessibility.

The `getAssertionData(Method method)` method retrieves assertion data for a given test method from the Excel sheet. It takes a `Method` object representing the test method as a parameter and returns a map containing assertion data where keys are JSON paths and values are expected values. This method is used to fetch expected assertion values for validating test results, enhancing test case reliability.

The `getParameterValue(Method method)` method retrieves parameter values for a given request method from the Excel sheet. It takes a `Method` object representing the request method as a parameter and returns a map containing parameter names and their corresponding values as strings. This method is useful for obtaining parameter values required to construct requests, aiding in API testing.

The `getRoutesFromSwaggerJson(String jsonPath)` method extracts route information from a Swagger JSON file. It takes the path to the Swagger JSON file as a parameter and returns a map containing route names and their corresponding URLs. This method facilitates API testing by providing route information parsed from Swagger documentation, aiding in endpoint accessibility.

The `getRequestBody(String jsonPath)` method parses a Swagger JSON file to extract request body information associated with different API paths and request types. It takes the path to the Swagger JSON file as a parameter and returns a map where the key is the summary of the request and the value is the corresponding request body in JSON format. This method is useful for retrieving request body examples defined in Swagger documentation, aiding in constructing API requests for testing.

The `getParamMap(String jsonPath)` method extracts parameter information from a Swagger JSON file. It takes the path to the Swagger JSON file as a parameter and returns a map containing parameter information with their corresponding summaries. This method helps in fetching parameter details specified in Swagger documentation, assisting in constructing API requests for testing.

## Class: `WriteDataToExcel`

The `WriteDataToExcel` class is a utility designed for writing data to an Excel sheet.

The `writeJsonDataToExcel(Map<String, JSONObject> jsonMap)` method writes JSON data to the Excel sheet. It takes a map containing JSON data as input and populates the `excelData` map with the JSON data formatted for Excel.

The `writeMapToExcel(Map<String, String> map)` method writes a map to the Excel sheet. It takes a map containing data to be written to the Excel sheet and formats it accordingly in the `excelData` map.

The `writeDataToSheet()` method writes data to the Excel sheet. It iterates through the `excelData` map, creating rows and cells in the spreadsheet and populating them with the data.

The `writeWorkbookToFile(String excelPath)` method writes the workbook to a file. It takes the path where the Excel file will be saved as input and writes the workbook to that file location.

The `writeToExcel(String jsonFilePath, String excelPath)` method is the entry point for writing data to an Excel file. It retrieves data from data providers, including request body map, routes map, and parameter map, and then calls the respective methods to write this data to the Excel sheet. Finally, it inserts the data into the Excel sheet and writes the workbook to the specified Excel file path.

This class provides a comprehensive solution for efficiently writing data to Excel sheets, particularly useful in scenarios where test data needs to be managed and utilized in automated testing environments.

# Package:`reporting `
## Class: `ExtentReportManager`

The `ExtentReportManager` class is responsible for managing and generating Extent reports for test automation.

The `createInstance(String fileName, String reportName, String documentTitle)` method creates and returns an instance of `ExtentReports` for generating HTML reports. It configures an `ExtentSparkReporter` with the provided file name, report name, and document title. Additionally, it sets the theme to DARK, encoding to "utf-8", and attaches the reporter to the `ExtentReports` instance.

The `getReportNameWithTimeStamp()` method generates a report name with a timestamp formatted as `"TestReportyyyyMMddHHmmss.html"`. It utilizes the current date and time to create a unique timestamp and appends it to the base report name `TestReport`, resulting in a string suitable for use as a report name with a timestamp in HTML reports.

The `logPassDetails(String log)` method logs a pass message with the provided log details in green color.

The `logFailureDetails(String log)` method logs a failure message with the provided log details in red color.

The `logExceptionDetails(String log)` method logs an exception message with the provided log details.

The `logInfoDetails(String log)` method logs an information message with the provided log details in grey color.

The `logWarningDetails(String log)` method logs a warning message with the provided log details in yellow color.

The `logJson(String json)` method logs a JSON string in a code block format.

The `logXml(String xml)` method logs an XML string in a code block format.

The `logHeaders(List<Header> headersList)` method logs HTTP headers in a tabular format in the Extent report, utilizing the `MarkupHelper.createTable` method to create the table representation of the headers.
## Class: `Setup`

The `Setup` class implements the `ITestListener` interface and is responsible for setting up and managing Extent reports during test execution.

The `onStart(ITestContext context)` method initializes the `ExtentReports` instance at the beginning of the test execution. It generates a unique report name with a timestamp using the `ExtentReportManager.getReportNameWithTimeStamp()` method, creates a full report path, and sets up the report instance with specified details such as report name and document title using the `ExtentReportManager.createInstance()` method.

The `onFinish(ITestContext context)` method flushes the `ExtentReports` instance at the end of the test execution. If the `ExtentReports` instance is not null, it flushes the report, ensuring that all information is written to the HTML report file.

The `onTestStart(ITestResult result)` method creates a new `ExtentTest` instance at the start of each test. It sets up the test with a name and description based on the test class and method names using the `ExtentReports.createTest()` method.

The `onTestFailure(ITestResult result)` method logs failure details and exception information when a test fails. It utilizes the `ExtentReportManager.logFailureDetails()` method to log failure details and formats the exception stack trace in a detailed format using HTML tags, which is then logged using the `ExtentReportManager.logExceptionDetails()` method.

# Package: `RestUtils`

## Class: `AssertionUtils`

The `AssertionUtils` class provides methods for performing assertions on values extracted from REST API responses and logging the results in an Extent report.

The `assertExpectedValuesWithJsonPath(Response response, Map<String, Object> expectedValuesMap)` method performs assertions on values extracted from a REST API response using JSONPath expressions. It compares actual values against expected values provided in a map and logs the results in an Extent report. The method iterates through JSONPath expressions, extracts actual values using JSONPath, and compares them with expected values. Results are logged in a tabular format in the Extent report. If all assertions pass, it logs a success message; otherwise, it logs a failure message.

The `assertExpectedValuesWithJsonPathString(Response response, Map<String, String> expectedValuesMap)` method performs assertions on values extracted from a REST API response using JSONPath expressions with string values. It follows a similar process to the previous method but handles expected values as strings. Results are logged in a tabular format in the Extent report.

The `assertExpectedValuesWithXmlPath(Response response, Map<String, Object> expectedValuesMap)` method performs assertions on values extracted from a REST API response using XML XPath expressions. It iterates through XML XPath expressions, extracts actual values using XPath, and compares them with expected values. Results are logged in a tabular format in the Extent report.

The `AssertThat(String actual, String expected, String log)` method asserts that the actual string is equal to the expected string and logs the result in an Extent report. It compares the provided actual and expected strings and logs either a pass message or a failure message with details of the assertion.

The `AssertThat(int actual, int expected, String log)` method asserts that the actual integer value is equal to the expected integer value and logs the result in an Extent report. It follows a similar process to the previous method but handles integer values.

The `AssertExpectedHeaders(Response response, Map<String, String> expected)` method asserts that the actual headers in the given response match the expected headers. It compares each header in the response against the expected values provided in a map and logs the results in a tabular format in the Extent report. If all assertions pass, it logs a success message; otherwise, it logs a failure message.

## Class: `GetRequestSpecification`

The `GetRequestSpecification` class provides methods for generating different types of request specifications for REST API requests.

The `xmlRequest(String endPoint, Object requestPayload, Map<String,String> headers)` method returns a `RequestSpecification` for an XML request with the specified endpoint, request payload, and headers. It sets up the request with the base URI, headers, content type, and body for XML.

The `xmlRequest()` method returns a`RequestSpecification` configured for XML content type without any specific endpoint or payload.

The `xmlRequestWithOauth2(String accessToken)` method returns a `RequestSpecification` configured for XML content type with OAuth 2.0 authentication using the provided access token.

The `jsonRequest(String endPoint, Object requestPayload, Map<String,String> headers)` method returns a` RequestSpecification `for a JSON request with the specified endpoint, request payload, and headers. It sets up the request with the base URI, headers, content type, and body for JSON.

The `jsonRequest()` method returns a `RequestSpecification `configured for JSON content type without any specific endpoint or payload.

The `jsonRequest(String pathParam, Object pathParamValue)` method returns a` RequestSpecification `configured for JSON content type with path parameters.

The `jsonRequestWithOauth2(String accessToken)` method returns a `RequestSpecification` configured for JSON content type with OAuth 2.0 authentication using the provided access token.

The `jsonRequestWithOauth2(String pathParam, Object pathParamValue, String accessToken)` method returns a` RequestSpecification `configured for JSON content type with OAuth 2.0 authentication and path parameters.

The `jsonRequestWithOauth2(Object requestPayload,String accessToken)` method returns a `RequestSpecification` configured for JSON content type with OAuth 2.0 authentication and request payload.

The `jsonRequestWithOauth2(Object requestPayload,String pathParam,Object pathParamValue,String accessToken)` method returns a `RequestSpecification` configured for JSON content type with OAuth 2.0 authentication, request payload, and path parameters.

## Class: `JsonRequestUtils`

The `JsonRequestUtils` class provides methods for performing various HTTP requests with JSON payloads and logging request and response details in the Extent report.

- `performJsonPost(String endPoint, String requestPayload, Map<String,String> headers)`: Performs a POST request with a JSON payload (provided as a string) and logs request and response details.

- `performJsonPost(String endPoint, Map<String, Object> requestPayload, Map<String,String> headers)`: Performs a POST request with a JSON payload (provided as a map) and logs request and response details.

- `performJsonPost(String endPoint,  Object requestPayload, Map<String,String> headers)`: Performs a POST request with a JSON payload (requestPayload as an Object) and logs request and response details.

- `performJsonPost(String endPoint, Map<String, Object> requestPayload, String accessToken)`: Performs a POST request with a JSON payload and OAuth 2.0 authentication, and logs request and response details.

- `performJsonGet(String endPoint, String pathParam, Object pathParamValue)`: Performs a GET request with JSON content, logs request and response details, and includes path parameters.

- `performJsonGet(String endPoint, String pathParam, Object pathParamValue, String accessToken)`: Performs a GET request with JSON content, OAuth 2.0 authentication, logs request and response details, and includes path parameters.

- `performJsonGet(String endPoint,String accessToken)`: Performs a GET request with JSON content, OAuth 2.0 authentication, logs request and response details.

- `performJsonDelete(String endPoint, String pathParam, Object pathParamValue)`: Performs a JSON DELETE request to the specified endpoint with optional path parameters, and logs request and response details.

- `performJsonDelete(String endPoint, String pathParam, Object pathParamValue, String accessToken)`: Performs a JSON DELETE request to the specified endpoint with optional path parameters and OAuth 2.0 authentication, and logs request and response details.

- `performJsonPatch(String endPoint, Map<String, Object> requestPayload, String pathParam, Object pathParamValue, String accessToken)`: Performs a JSON Patch operation using OAuth2 authentication, logs request and response details.

- `performJsonPatch(String endPoint, Object requestPayload, String pathParam, Object pathParamValue, String accessToken)`: Performs a JSON Patch operation using OAuth2 authentication, logs request and response details.

- `performJsonPut(String endPoint, Map<String, Object> requestPayload, String pathParam, Object pathParamValue, String accessToken)`: Performs a JSON PUT operation using OAuth2 authentication, logs request and response details.

Each method prepares the request specification using methods from the `GetRequestSpecification` class and then executes the request using RestAssured. After receiving the response, it logs both the request and response details using `LogInReportUtil`.

## Class: `LogInReportUtil`

The `LogInReportUtil` class provides methods for logging request and response details in the Extent report.

- `printJsonRequest(RequestSpecification requestSpecification)`: Logs the details of a JSON request in the Extent report, including endpoint, method, headers, and request body.

- `printXmlRequest(RequestSpecification requestSpecification)`: Logs the details of an XML request in the Extent report, including endpoint, method, headers, and request body.

- `printJsonResponse(Response response)`: Logs the details of a JSON response in the Extent report, including status code, headers, and response body.

- `printXmlResponse(Response response)`: Logs the details of an XML response in the Extent report, including status code, headers, and response body.

Each method receives either a `RequestSpecification` or a `Response` object and extracts relevant information from it using `SpecificationQuerier` for requests and methods provided by RestAssured for responses. Then, it logs this information using methods from `ExtentReportManager` specific to logging headers, JSON, and XML data.

## Class: `XmlRequestUtils`

- `performXmlPost(String endPoint, Object requestPayloadAsPojo, Map<String,String> headers) throws JsonProcessingException`: Performs a POST request with XML payload (using a POJO) and logs request and response details in the Extent report.




