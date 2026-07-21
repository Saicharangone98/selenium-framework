package stepdefinitions;

import io.cucumber.java.After;
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
import utils.TestContext;

import java.time.Duration;

public class LoginSteps {
    private final LoginPage loginPage;
    private final WebDriver driver;

    // PicoContainer automatically initializes and passes TestContext here
    public LoginSteps(TestContext context) {
        this.loginPage = context.getLoginPage();
        this.driver = DriverFactory.getDriver();
    }

    @Given("User is on the login page")
    public void userIsOnTheLoginPage() {
        driver.get(ConfigReader.get("baseUrl"));
    }

    @When("User enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String userName, String password) {
        loginPage.login(userName, password);
    }

    @Then("User should be redirected to the inventory page")
    public void userShouldBeRedirectedToTheInventoryPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/inventory.html"));

        Assert.assertTrue(driver.getCurrentUrl().endsWith("/inventory.html"), "LOGIN FAILED");
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Then("{string}")
    public void validateLogin(String expectedOutput) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        if (expectedOutput.equals("redirect")) {
            wait.until(ExpectedConditions.urlContains("/inventory.html"));
            Assert.assertTrue(driver.getCurrentUrl().endsWith("/inventory.html"), "LOGIN FAILED");
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getErrorLocator()));
            Assert.assertEquals(loginPage.getErrorText(), expectedOutput);
        }
    }


}
