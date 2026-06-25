package utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(utils.Listeners.class)
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setupDriver() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.get("baseUrl"));
    }

    @AfterMethod
    public void tearDownDriver() {
        DriverFactory.quitDriver();
    }

}
