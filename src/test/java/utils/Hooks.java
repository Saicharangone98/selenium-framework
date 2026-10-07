package utils;

import api.BaseApi;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks extends BaseTest {
    WebDriver driver;

    @Before("@RequiresPreloadedAccount")
    public void seedAccountData(Scenario scenario) {
        // Fast backend state setup via REST Assured before browser interaction starts
        Response response = RestAssured.given()
                .spec(BaseApi.getRequestSpec())
                .queryParam("customerId", 12212)
                .queryParam("newAccountType", 1)
                .queryParam("fromAccountId", 13344)
                .when()
                .post("/createAccount");

        int generatedAccountId = response.jsonPath().getInt("id");
        System.out.println("⚡ Pre-seeded account via API: " + generatedAccountId);

        // Pass to ScenarioContext so UI steps can consume it without UI creation overhead
        scenario.log("Pre-seeded Account ID: " + generatedAccountId);
    }

    @Before
    public void setup(){
        System.out.println("Hooks setup method is invoked");
        setupDriver();
    }

    @After(order = 1)
    public void tearDownOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                // 1. Native Cucumber attachment (for cucumber-html-reports / Extent Spark Cucumber Adapter)
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Snapshot: " + scenario.getName());

                // 2. Also save physical file to target/screenshots/ using our utility
                String sanitizedName = scenario.getName().replaceAll("[^a-zA-Z0-9_-]", "_");
                ScreenshotUtils.captureToFile(driver, sanitizedName);

                System.out.println("📸 Captured failure screenshot for Cucumber scenario: " + scenario.getName());
            }
        }
    }

    @After(order = 0)
    public void quitBrowser() {
        // Quit browser AFTER screenshot is taken (order 0 runs after order 1)
        if (DriverFactory.getDriver() != null) {
            DriverFactory.quitDriver();
        }
    }

    @AfterAll
    public static void cleanUpDatabaseConnections() {
        DatabaseManager.closeConnection();
        System.out.println("🔌 Database connections cleanly closed.");
    }
}
