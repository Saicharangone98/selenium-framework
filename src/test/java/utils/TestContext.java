package utils;

import lombok.Getter;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private LoginPage loginPage;

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public InventoryPage getInventoryPage() {
        return inventoryPage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public CheckoutPage getCheckoutPage() {
        return checkoutPage;
    }

    private InventoryPage inventoryPage;

    private CartPage cartPage;

    private CheckoutPage checkoutPage;

    private Map<String, Object> scenarioContext;

    public TestContext() {
        this.loginPage = new LoginPage(DriverFactory.getDriver());
        this.inventoryPage = new InventoryPage(DriverFactory.getDriver());
        this.cartPage = new CartPage(DriverFactory.getDriver());
        this.checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        this.scenarioContext = new HashMap<>();
    }

    public void setContext(String key, Object value) {
        scenarioContext.get(key);
    }

    public Object getContext(String key) {
        return scenarioContext.get(key);
    }
}
