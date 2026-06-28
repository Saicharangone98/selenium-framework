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

    @Override
    public void onTestFailure(ITestResult result){
        WebDriver driver = DriverFactory.getDriver();
        String methodName = result.getMethod().getMethodName();

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
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().fail(result.getThrowable());
            ExtentReportManager.getTest().addScreenCaptureFromPath(filePath,"TEST FAILED - "+methodName);
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
        createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getTest().pass(result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ISuite suite) {
        flushReport();
    }
}
