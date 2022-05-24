package com.hidarisoft.dynamostudy.config;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DynamoDBConfigTest implements BeforeAllCallback, AfterAllCallback {

    protected DynamoDBProxyServer server;

    public static String port;

    public DynamoDBConfigTest() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        port = "8765";
        this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
        this.server.start();

    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        this.stopUnchecked(this.server);
    }

    protected void stopUnchecked(DynamoDBProxyServer dynamoDBProxyServer) {
        try {
            dynamoDBProxyServer.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
