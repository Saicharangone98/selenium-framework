package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "target/screenshots/";

    private ScreenshotUtils() {
        // Prevent instantiation
    }

    /**
     * Captures a screenshot as a Base64 string for direct embedding in HTML reports.
     */
    public static String captureBase64(WebDriver driver) {
        if (driver == null) {
            return null;
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Captures and writes a physical .png file to target/screenshots/.
     * Returns the relative file path.
     */
    public static String captureToFile(WebDriver driver, String testMethodName) {
        if (driver == null) {
            return null;
        }

        try {
            Path directory = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
            String fileName = testMethodName + "_" + timestamp + ".png";
            Path destination = directory.resolve(fileName);

            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(sourceFile.toPath(), destination);

            return destination.toString();
        } catch (IOException e) {
            System.err.println("Failed to save screenshot to file: " + e.getMessage());
            return null;
        }
    }
}