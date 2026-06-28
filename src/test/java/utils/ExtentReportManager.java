package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportManager {
    // Single report instance for entire suite
    private static ExtentReports extent;

    // One ExtentTest per @Test method — ThreadLocal because tests run on different threads
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Called once when suite starts
    public static void initReport() {
        String reportPath = System.getProperty("user.dir") + File.separator
                + "target" + File.separator + "reports" + File.separator + "report.html";

        // SparkReporter defines WHERE and HOW the HTML is generated
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setReportName("Selenium Framework Report");
        spark.config().setDocumentTitle("Test Results");

        // ExtentReports is the engine — attach the reporter to it
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    // Called at start of each @Test — creates one entry in the report
    public static void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test); // store per thread
    }

    // Get current thread's test entry
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    // Called when suite finishes — writes the HTML file
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
