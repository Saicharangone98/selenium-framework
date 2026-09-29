package api;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DatabaseManager;

import java.util.List;
import java.util.Map;

public class DatabaseValidationTest {

    @BeforeClass
    public void setupTable() {
        // Initialize an accounts table for verification demo
        DatabaseManager.executeUpdate(
                "CREATE TABLE IF NOT EXISTS ACCOUNTS (ID INT PRIMARY KEY, CUSTOMER_ID INT, TYPE VARCHAR(50), BALANCE DECIMAL(10,2))"
        );
        DatabaseManager.executeUpdate(
                "MERGE INTO ACCOUNTS KEY(ID) VALUES (12212, 1001, 'SAVINGS', 2500.50)"
        );
    }

    @Test
    public void testAccountRecordIntegrity() {
        int targetCustomerId = 1001;

        List<Map<String, Object>> results = DatabaseManager.executeQuery(
                "SELECT * FROM ACCOUNTS WHERE CUSTOMER_ID = ?",
                targetCustomerId
        );

        Assert.assertFalse(results.isEmpty(), "Account record should exist in the database for customer " + targetCustomerId);

        System.out.println(results);

        Map<String, Object> record = results.get(0);
        Assert.assertEquals(record.get("TYPE"), "SAVINGS", "Account type must match expected record");
        Assert.assertEquals(Double.parseDouble(record.get("BALANCE").toString()), 2500.50, "Account balance must match expected record");
    }

    @AfterClass
    public void tearDown() {
        DatabaseManager.closeConnection();
    }
}