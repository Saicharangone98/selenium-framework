package utils;

import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;

//    @BeforeMethod
    public void setupDriver() {
        try {
            driver = DriverFactory.getDriver();
            driver.get(ConfigReader.getProperty("baseUrl"));
        } catch (Exception e) {
            System.out.println("BEFOREMETHOD FAILED: " + e.getMessage());
            throw e;
        }
    }

//    @AfterMethod
    public void tearDownDriver() {
        System.out.println("Base Test - TearDown");
        DriverFactory.quitDriver();
    }

}
