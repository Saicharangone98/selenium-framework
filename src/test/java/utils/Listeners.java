package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.*;


public class Listeners implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        ExtentReportManager.initReport();
    }

    @Override
    public void onFinish(ISuite suite) {
        ExtentReportManager.flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed Successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        // 1. Log failure status and exception trace
        ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + methodName);
        if (throwable != null) {
            ExtentReportManager.getTest().fail(throwable);
        }

        // 2. Fetch driver safely from the current thread
        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            // Option A: Base64 embedded directly inside the single HTML report
            String base64Screenshot = ScreenshotUtils.captureBase64(driver);
            if (base64Screenshot != null) {
                ExtentReportManager.getTest().fail("Failure Snapshot (Embedded):",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            }

            // Option B: Save as a disk asset for pipeline artifact bundle
            String filePath = ScreenshotUtils.captureToFile(driver, methodName);
            if (filePath != null) {
                System.out.println("📸 Screenshot saved to file: " + filePath);
            }
        } else {
            ExtentReportManager.getTest().log(Status.INFO,
                    "No active WebDriver instance on current thread to capture screenshot (likely an API test).");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            ExtentReportManager.getTest().skip(result.getThrowable());
        }
    }
}