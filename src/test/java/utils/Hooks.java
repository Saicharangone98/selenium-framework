package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks extends BaseTest {
    WebDriver driver;

    @Before
    public void setup(){
        setupDriver();
    }

    @After
    public void tearDown(){
        tearDownDriver();
    }
}
