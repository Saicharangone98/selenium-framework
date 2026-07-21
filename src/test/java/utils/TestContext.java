package utils;

import pages.InventoryPage;
import pages.LoginPage;

public class TestContext {
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    public TestContext(){
        this.loginPage = new LoginPage(DriverFactory.getDriver());
        this.inventoryPage = new InventoryPage(DriverFactory.getDriver());
    }

    public LoginPage getLoginPage(){
        return loginPage;
    }

    public InventoryPage getInventoryPage() {
        return inventoryPage;
    }
}
