package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listeners implements ITestListener {

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

    }

}
