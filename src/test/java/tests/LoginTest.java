package tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseTest;
import utils.ConfigReader;

import java.time.Duration;


public class LoginTest extends BaseTest {

    LoginPage loginPage;

    @Test
    public void validateLoginTest(){
        loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"),ConfigReader.get("password"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/inventory.html"));

        Assert.assertTrue(driver.getCurrentUrl().endsWith("/inventory.html"),"LOGIN FAILED");
    }

}
