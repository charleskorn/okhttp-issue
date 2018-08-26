package com.charleskorn.okhttpissue;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class Application {
    public static void main(String... args) throws Exception {
        configureLogging();

        ClientAndServer clientAndServer = new ClientAndServer();

        clientAndServer.run(Scenario.ChunkedWithoutEndMarker);
        clientAndServer.run(Scenario.ChunkedWithEndMarker);
        clientAndServer.run(Scenario.Normal);
    }

    private static void configureLogging() throws IOException {
        try (InputStream configurationStream = ClientAndServer.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(configurationStream);
        }
    }
}
