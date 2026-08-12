package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks extends BaseTest {
    WebDriver driver;

    @Before
    public void setup(){
        System.out.println("Hooks setup method is invoked");
        setupDriver();
    }

    @After
    public void tearDown(Scenario scenario){
        System.out.println("Hooks tearDown method is invoked");
        if (scenario.isFailed()){
            try{
                byte[] screenshot = ((TakesScreenshot)DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot,"image/png",scenario.getName());


            } catch (Exception e) {
                System.out.println("Failed to take screenshot"+e.getMessage());
            }
        }
        tearDownDriver();
    }
}
