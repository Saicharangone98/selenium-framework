package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.MockServerManager;

// Explicit WireMock imports (only what is needed for verification)
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

// Hamcrest Matchers for REST Assured assertions
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class WireMockApiTest {

    private MockServiceStubs mockStubs;

    @BeforeClass
    public void setupServer() {
        MockServerManager.startServer();
        RestAssured.baseURI = MockServerManager.getBaseUrl();
        mockStubs = new MockServiceStubs(MockServerManager.getServer());
    }

    @BeforeMethod
    public void resetStubs() {
        MockServerManager.resetStubs();
    }

    @Test(description = "Verify successful payment authorization response from mock stub")
    public void testSuccessfulPaymentVerification() {
        String testTxnId = "TXN-98451-XYZ";
        mockStubs.stubSuccessfulPayment(testTxnId);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "orderId": "ORD-101",
                          "amount": 150.00,
                          "currency": "USD"
                        }
                        """)
                .when()
                .post(ApiEndpoints.PAYMENT_VERIFY)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("APPROVED"))
                .body("transactionId", equalTo(testTxnId))
                .body("message", equalTo("Payment verified successfully"));

        // Verify that the mock endpoint received exactly 1 POST request
        MockServerManager.getServer().verify(1, postRequestedFor(urlEqualTo(ApiEndpoints.PAYMENT_VERIFY)));
    }

    @Test(description = "Verify gateway failure simulation (503 Service Unavailable)")
    public void testPaymentGatewayDownSimulation() {
        mockStubs.stubPaymentGatewayDown();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "orderId": "ORD-102",
                          "amount": 99.99
                        }
                        """)
                .when()
                .post(ApiEndpoints.PAYMENT_VERIFY)
                .then()
                .statusCode(503)
                .contentType(ContentType.JSON)
                .body("status", equalTo("FAILED"))
                .body("error", equalTo("Gateway Timeout"));

        MockServerManager.getServer().verify(1, postRequestedFor(urlEqualTo(ApiEndpoints.PAYMENT_VERIFY)));
    }

    @Test(description = "Verify 404 response when querying non-existent user ID")
    public void testUserNotFoundSimulation() {
        int nonExistentUserId = 999;
        mockStubs.stubUserNotFound(nonExistentUserId);

        RestAssured.given()
                .when()
                .get(ApiEndpoints.USERS_BASE + "/" + nonExistentUserId)
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("status", equalTo(404))
                .body("message", containsString("User with ID 999 does not exist"));

        MockServerManager.getServer().verify(1, getRequestedFor(urlEqualTo(ApiEndpoints.USERS_BASE + "/" + nonExistentUserId)));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        MockServerManager.stopServer();
        RestAssured.reset();
    }
}