package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class Setup implements ITestListener {

    public static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /**
     * Initializes the ExtentReports instance at the beginning of the test execution.
     * Creates a unique report name with a timestamp, sets up the report instance with specified
     * details, and stores it for later use throughout the test execution.
     * @param context The ITestContext object representing the test context.
     */
    public void onStart(ITestContext context) {
        String fileName = ExtentReportManager.getReportNameWithTimeStamp();
        String fullReportPath = System.getProperty("user.dir") + "\\reports\\" + fileName;
        extentReports = ExtentReportManager.createInstance(fullReportPath, "Test API Automation Report", "Test ExecutionReport");
    } 

    /**
     * Flushes the ExtentReports instance at the end of the test execution.
     * If the ExtentReports instance is not null, flushes the report, ensuring that all
     * information is written to the HTML report file.
     * @param context The ITestContext object representing the test context.
     */
    public void onFinish(ITestContext context) {
        if (extentReports != null)
            extentReports.flush();
    }

    /**
     * Creates a new ExtentTest instance at the start of each test.
     * Sets up the test with a name and description based on the test class and method names.
     * @param result The ITestResult object representing the test result.
     */
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest("Test Name " + result.getTestClass().getName() + " - " + result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        extentTest.set(test);
    }

    /**
     * Logs failure details and exception information when a test fails.
     * Uses ExtentReportManager methods to log failure details and exception stack trace
     * in a formatted manner in the Extent report.
     * @param result The ITestResult object representing the test result.
     */
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.logFailureDetails(result.getThrowable().getMessage());
        String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());
        stackTrace = stackTrace.replaceAll(",", "<br>");
        String formmatedTrace = "<details>\n" +
                "    <summary>Click Here To See Exception Logs</summary>\n" +
                "    " + stackTrace + "\n" +
                "</details>\n";
        ExtentReportManager.logExceptionDetails(formmatedTrace);
    }

}
