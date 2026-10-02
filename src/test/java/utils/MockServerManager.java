package utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class MockServerManager {

    private static WireMockServer wireMockServer;

    public static synchronized void startServer() {
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            // dynamicPort() lets the OS pick an available port to prevent conflicts
            wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
            wireMockServer.start();
            System.out.println("🟢 WireMock Server started on port: " + wireMockServer.port());
        }
    }

    public static synchronized void stopServer() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            wireMockServer = null;
            System.out.println("🔴 WireMock Server stopped.");
        }
    }

    public static synchronized void resetStubs() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.resetAll();
        }
    }

    public static String getBaseUrl() {
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            throw new IllegalStateException("WireMock server is not running! Call startServer() first.");
        }
        return wireMockServer.baseUrl();
    }

    public static int getPort() {
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            throw new IllegalStateException("WireMock server is not running! Call startServer() first.");
        }
        return wireMockServer.port();
    }

    public static WireMockServer getServer() {
        return wireMockServer;
    }
}