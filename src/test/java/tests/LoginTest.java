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
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/inventories.html"),"LOGIN FAILED");
    }

    @Test
    public void validateLoginWithInvalidCreds(){
        loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("invalidUsername"),ConfigReader.get("invalidPassword"));

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getErrorLocator()));

        Assert.assertEquals(loginPage.getErrorText(),"Epic sadface: Username and password do not match any user in this service");
    }

}
