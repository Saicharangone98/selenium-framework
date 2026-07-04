package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

import java.time.Duration;

public class LoginSteps {
    LoginPage loginPage;
    WebDriver driver;

    @Before
    public void setup(){
        driver = DriverFactory.getDriver();
    }

    @Given("User is on the login page")
    public void userIsOnTheLoginPage() {
        driver.get(ConfigReader.get("baseUrl"));
    }

    @When("User enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String userName, String password) {
        loginPage = new LoginPage(driver);
        loginPage.login(userName,password);
    }

    @Then("User should be redirected to the inventory page")
    public void userShouldBeRedirectedToTheInventoryPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/inventory.html"));

        Assert.assertTrue(driver.getCurrentUrl().endsWith("/inventory.html"),"LOGIN FAILED");
    }
    @After
    public void tearDown(){
        DriverFactory.quitDriver();
    }


}
