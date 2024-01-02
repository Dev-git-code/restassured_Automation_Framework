package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import reporting.Setup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ExtentReportManager {

    public static ExtentReports extentReports;

    /**
     * Creates and returns an instance of ExtentReports for generating HTML reports.
     *
     * <p>The method configures an ExtentSparkReporter with the provided file name, report name, and document title.
     * Additionally, it sets the theme to DARK, encoding to "utf-8", and attaches the reporter to the ExtentReports instance.
     *
     * @param fileName The name of the HTML report file to be generated.
     * @param reportName The name of the report to be displayed in the header.
     * @param documentTitle The title of the HTML document.
     * @return An instance of ExtentReports configured with the specified settings.
     */
    public static ExtentReports createInstance(String fileName, String reportName, String documentTitle) {
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
        extentSparkReporter.config().setReportName(reportName);
        extentSparkReporter.config().setDocumentTitle(documentTitle);
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setEncoding("utf-8"); 
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        return extentReports;
    }

    /**
     * Generates a report name with a timestamp formatted as "TestReportyyyyMMddHHmmss.html".
     *
     * <p>This method utilizes the current date and time to create a unique timestamp in the format
     * "yyyyMMddHHmmss" and appends it to the base report name "TestReport". The resulting string is
     * suitable for use as a report name with timestamp in HTML reports.
     *
     * @return A string representing the report name with a timestamp.
     */
    public static String getReportNameWithTimeStamp() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedTime = dateTimeFormatter.format(localDateTime);
        String reportName = "TestReport" + formattedTime + ".html";
        return reportName;
    }
    
    /**
     * Logs a pass message with the provided log details in green color.
     * @param log The log message to be included in the Extent report.
     */
    public static void logPassDetails(String log) {
        Setup.extentTest.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
    }
    
    /**
     * Logs a failure message with the provided log details in red color.
     * @param log The log message to be included in the Extent report.
     */
    public static void logFailureDetails(String log) {
        Setup.extentTest.get().fail(MarkupHelper.createLabel(log, ExtentColor.RED));
    }
    
    /**
     * Logs an exception message with the provided log details.
     * @param log The log message to be included in the Extent report.
     */
    public static void logExceptionDetails(String log) {
        Setup.extentTest.get().fail(log);
    }
    
    /**
     * Logs an information message with the provided log details in grey color.
     * @param log The log message to be included in the Extent report.
     */
    public static void logInfoDetails(String log) {
        Setup.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.GREY));
    }
    
    /**
     * Logs a warning message with the provided log details in yellow color.
     * @param log The log message to be included in the Extent report.
     */
    public static void logWarningDetails(String log) {
        Setup.extentTest.get().warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW));
    }
    
    /**
     * Logs a JSON string in a code block format.
     * @param json The JSON string to be included in the Extent report.
     */
    public static void logJson(String json) {
        Setup.extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
    }
    

	/**
	 * Logs an XML string in a code block format.
	 * @param xml The XML string to be included in the Extent report.
	 */
    public static void logXml(String xml) {
        Setup.extentTest.get().info(MarkupHelper.createCodeBlock(xml, CodeLanguage.XML));
    }
    
    /**
     * Logs HTTP headers in a tabular format in the Extent report.
     * @param headersList List of HTTP headers to be included in the Extent report.
     */
    public static void logHeaders(List<Header> headersList) {

        String[][] arrayHeaders = headersList.stream().map(header -> new String[] {header.getName(), header.getValue()})
                        .toArray(String[][] :: new);
        Setup.extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
    }
}
