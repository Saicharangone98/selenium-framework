package api;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServiceStubs {

    private final WireMockServer server;

    public MockServiceStubs(WireMockServer server) {
        this.server = server;
    }

    /**
     * Stubs a 200 OK payment verification endpoint.
     */
    public void stubSuccessfulPayment(String transactionId) {
        server.stubFor(post(urlEqualTo(ApiEndpoints.PAYMENT_VERIFY))
                .withHeader("Content-Type", containing("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "status": "APPROVED",
                                  "transactionId": "%s",
                                  "message": "Payment verified successfully"
                                }
                                """.formatted(transactionId))));
    }

    /**
     * Stubs a 503 Service Unavailable with artificial network delay.
     */
    public void stubPaymentGatewayDown() {
        server.stubFor(post(urlEqualTo(ApiEndpoints.PAYMENT_VERIFY))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withFixedDelay(1500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "status": "FAILED",
                                  "error": "Gateway Timeout",
                                  "message": "Downstream payment provider is currently unavailable"
                                }
                                """)));
    }

    /**
     * Stubs a 404 User Not Found endpoint using path pattern matching.
     */
    public void stubUserNotFound(int userId) {
        String path = ApiEndpoints.USERS_BASE + "/" + userId;
        server.stubFor(get(urlEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "timestamp": "%s",
                                  "status": 404,
                                  "error": "Not Found",
                                  "message": "User with ID %d does not exist"
                                }
                                """.formatted(java.time.Instant.now().toString(), userId))));
    }
}