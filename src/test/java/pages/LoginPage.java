package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    By userName = By.id("user-name");
    By password = By.id("password");
    By loginButton = By.id("login-button");
    By loginErrorBox = By.cssSelector("[data-test='error']");


    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void enterUserName(String username){
        driver.findElement(userName).sendKeys(username);
    }

    public void enterPassword(String password){
        driver.findElement(this.password).sendKeys(password);
    }

    public void clickLoginButton(){
        driver.findElement(loginButton).click();
    }

    public void login(String userName,String password){
        enterUserName(userName);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorText(){
        return driver.findElement(loginErrorBox).getText();
    }

    public By getErrorLocator(){
        return loginErrorBox;
    }

}
