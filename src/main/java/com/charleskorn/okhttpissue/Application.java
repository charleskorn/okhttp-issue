package com.charleskorn.okhttpissue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

public class Application {
    public static void main(String... args) throws Exception {
        configureLogging();

        ClientAndServer clientAndServer = new ClientAndServer();

        clientAndServer.run(Scenario.ChunkedWithoutEndMarker);
        clientAndServer.run(Scenario.ChunkedWithEndMarker);
        clientAndServer.run(Scenario.Normal);

        List<Thread> foregroundThreads = Thread.getAllStackTraces().keySet().stream()
                .filter(t -> !t.isDaemon())
                .sorted(Comparator.comparing(Thread::getId))
                .collect(Collectors.toList());

        if (foregroundThreads.size() > 1) {
            System.out.println("There is more than one foreground thread still running:");

            for (Thread thread : foregroundThreads) {
                System.out.println(thread.getId() + ": " + thread.getName());
            }

            System.out.println();
            System.out.println("These will be forcibly terminated.");
            Runtime.getRuntime().exit(0);
        }
    }

    private static void configureLogging() throws IOException {
        try (InputStream configurationStream = ClientAndServer.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(configurationStream);
        }
    }
}
