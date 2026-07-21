package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    WebDriver driver;

    private final By pageTitle = By.className("title");
    private final By addToCartBackPackBtn = By.id("add-to-cart-sauce-labs-backpack");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");


    public InventoryPage(WebDriver driver){
        this.driver = driver;
    }

    public String getPageTitle(){
        return driver.findElement(pageTitle).getText();
    }

    public void addBackpackToCart(){
        driver.findElement(addToCartBackPackBtn).click();
    }

    public String getCartBadgeCount(){
        return driver.findElement(shoppingCartBadge).getText();
    }
}
