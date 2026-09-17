package utils;

import api.BaseApi;
import io.cucumber.java.After;
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
