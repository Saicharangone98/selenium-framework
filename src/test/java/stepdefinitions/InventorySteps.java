package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.InventoryPage;
import utils.DriverFactory;
import utils.TestContext;

public class InventorySteps {

    private final InventoryPage inventoryPage;
    private final WebDriver driver;

    public InventorySteps(TestContext testContext){
        inventoryPage = testContext.getInventoryPage();
        driver = DriverFactory.getDriver();
    }

    @Then("User should see the products header {string}")
    public void userShouldSeeTheProductsHeader(String expectedHeader) {
        Assert.assertEquals(inventoryPage.getPageTitle(),expectedHeader);
    }

    @When("User adds Sauce Labs Backpack to the cart")
    public void userAddsSauceLabsBackpackToTheCart() {
        inventoryPage.addBackpackToCart();
    }

    @Then("The cart badge count should be {string}")
    public void theCartBadgeCountShouldBe(String expectedCount) {
        Assert.assertEquals(inventoryPage.getCartBadgeCount(),expectedCount);
    }
}
