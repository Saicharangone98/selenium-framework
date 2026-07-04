package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.ExtentReportManager.*;

public class Listeners implements ITestListener, ISuiteListener{

    public String takeScreenShot(String methodName){
        WebDriver driver = DriverFactory.getDriver();

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        String directoryPath = System.getProperty("user.dir")+ File.separator+"target"+File.separator+"Screenshot"+File.separator;
        String filePath = directoryPath + methodName + timeStamp+".png";

        new File(directoryPath).mkdirs();

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        }catch(Exception e){
            e.printStackTrace();
        }
        return filePath;
    }
    @Override
    public void onTestFailure(ITestResult result){
        String methodName = result.getMethod().getMethodName();
        String screenshot = takeScreenShot(methodName);

        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().fail(result.getThrowable());
            ExtentReportManager.getTest().addScreenCaptureFromPath(screenshot,"TEST FAILED - "+methodName);
        } else {
            System.out.println("ExtentTest null - BeforeMethod likely failed: "
                    + result.getThrowable().getMessage());
        }

    }

    @Override
    public void onStart(ISuite suite) {
        initReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        Object[] parameters = result.getParameters();
        String testName = result.getMethod().getMethodName();
        if (parameters != null && parameters.length > 0) {
            // Cucumber passes PickleWrapper as first parameter
            testName = parameters[0] instanceof io.cucumber.testng.PickleWrapper
                    ? ((io.cucumber.testng.PickleWrapper) parameters[0]).getPickle().getName()
                    : result.getMethod().getMethodName();
        }
        createTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getTest().pass("TEST PASSED");
    }

    @Override
    public void onFinish(ISuite suite) {
        flushReport();
    }
}
