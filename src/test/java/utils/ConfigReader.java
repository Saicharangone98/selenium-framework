package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try {
            // Defaults to 'qa' if not provided via CLI
            String env = System.getProperty("env", "qa").toLowerCase();
            String fileName = "config-" + env + ".properties";
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("config.properties not found in test/resources", e);
        }
    }

    public static String getProperty(String key){
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp; // Allows overriding individual keys via CLI: -Dui.base.url=...
        }
        return properties.getProperty(key);
    }


}
