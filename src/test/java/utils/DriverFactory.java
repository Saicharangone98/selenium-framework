package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriverInstance());
        }
        return driver.get();
    }

    private static WebDriver createDriverInstance() {
        String executionMode = ConfigReader.getProperty("execution.mode").toLowerCase();
        String browser = ConfigReader.getProperty("browser").toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if ("grid".equals(executionMode)) {
            return createRemoteDriver(browser, isHeadless);
        } else {
            return createLocalDriver(browser, isHeadless);
        }
    }

    private static WebDriver createLocalDriver(String browser, boolean isHeadless) {
        if ("firefox".equals(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) options.addArguments("-headless");
            return new FirefoxDriver(options);
        } else {
            return new ChromeDriver(getChromeOptions(isHeadless));
        }
    }

    private static WebDriver createRemoteDriver(String browser, boolean isHeadless) {
        String gridUrlStr = ConfigReader.getProperty("grid.url");
        try {
            URL gridUrl = URI.create(gridUrlStr).toURL();
            if ("firefox".equals(browser)) {
                FirefoxOptions options = new FirefoxOptions();
                if (isHeadless) options.addArguments("-headless");
                return new RemoteWebDriver(gridUrl, options);
            } else {
                return new RemoteWebDriver(gridUrl, getChromeOptions(isHeadless));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL: " + gridUrlStr, e);
        }
    }

    private static ChromeOptions getChromeOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}