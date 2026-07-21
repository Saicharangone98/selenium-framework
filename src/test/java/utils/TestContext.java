package utils;

import pages.LoginPage;

public class TestContext {
    private LoginPage loginPage;

    public TestContext(){
        this.loginPage = new LoginPage(DriverFactory.getDriver());
    }

    public LoginPage getLoginPage(){
        return loginPage;
    }
}
