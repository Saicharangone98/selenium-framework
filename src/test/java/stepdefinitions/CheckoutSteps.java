package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import utils.TestContext;

public class CheckoutSteps {

    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private TestContext context;

    public CheckoutSteps(TestContext testContext){
        context = testContext;
        cartPage = testContext.getCartPage();
        checkoutPage = testContext.getCheckoutPage();
    }

    @When("User navigates to the shopping cart")
    public void userNavigatesToTheShoppingCart() {
        cartPage.navigateToCart();

    }

    @Then("The cart should contain item {string}")
    public void theCartShouldContainItem(String expectedItemName) {
        String actualItemName = cartPage.getFirstItemName();
        context.setContext("SELECTED_ITEM", actualItemName);
        Assert.assertEquals(actualItemName,expectedItemName,"Wrong item is added to cart");
    }

    @And("User completes checkout with details {string}, {string}, {string}")
    public void userCompletesCheckoutWithDetails(String firstName, String lastName, String zipcode) {
        cartPage.clickCheckout();
        checkoutPage.enterShippingInfo(firstName,lastName,zipcode);
        checkoutPage.clickFinish();
    }

    @Then("The order confirmation header should be {string}")
    public void theOrderConfirmationHeaderShouldBe(String expectedMessage) {
        Assert.assertEquals(checkoutPage.getConfirmationMessage(),expectedMessage);
        String itemInContext = (String) context.getContext("SELECTED_ITEM");
        System.out.println(itemInContext + " Item is successfully checked out");
    }
}
