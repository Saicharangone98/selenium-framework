package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver=new ThreadLocal<>();

    public static WebDriver getDriver(){
        if(driver.get()==null){
            ChromeOptions options = new ChromeOptions();
            // 1. Force Guest Mode (Disables Password Manager & User Profiles entirely)
            options.addArguments("--guest");

            // 2. Hide automation notification bar
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false);
            prefs.put("autofill.credit_card_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            // 2. Disable Chrome info bars, notification popups & password leak detection
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-save-password-bubble");
            WebDriver webDriver;
            String headless = System.getProperty("headless", ConfigReader.get("runHeadless"));
            boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");
            if("true".equals(ConfigReader.get(headless)) || isLinux){

                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            webDriver = new ChromeDriver(options);
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            webDriver.manage().window().maximize();
            driver.set(webDriver);
        }
        return driver.get();
    }

    public static void quitDriver(){
        if (driver.get()!=null){
            driver.get().quit();
            driver.remove();
        }
    }

}
