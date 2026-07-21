package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = {"src/test/resources/features"}, glue = {"stepdefinitions"}, plugin = {"pretty", "html:target/cucumber-reports",
        "json:target/cucumber-reports.json"}, monochrome = true,
        tags = "@Inventory",
        dryRun = false)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
