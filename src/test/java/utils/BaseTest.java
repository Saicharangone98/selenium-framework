package utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setupDriver() {
        try {
            driver = DriverFactory.getDriver();
            driver.get(ConfigReader.get("baseUrl"));
        } catch (Exception e) {
            System.out.println("BEFOREMETHOD FAILED: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDownDriver() {
        DriverFactory.quitDriver();
    }

}
