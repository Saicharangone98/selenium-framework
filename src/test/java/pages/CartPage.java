package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    WebDriver driver;

    private final By shoppingCartLink = By.className("shopping_cart_link");
    private final By cartItemName = By.className("inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver){
        this.driver = driver;
    }

    public void navigateToCart(){
        driver.findElement(shoppingCartLink).click();
    }
    public String getFirstItemName(){
        return driver.findElement(cartItemName).getText();
    }

    public void clickCheckout(){
        driver.findElement(checkoutButton).click();
    }

}
