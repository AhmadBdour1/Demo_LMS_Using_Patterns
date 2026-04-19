package com.lms;

import com.lms.web.LmsWebServer;

import java.nio.file.Path;

/**
 * Application entry point.
 * <p>
 * This class boots the dependency graph, seeds demo data, and starts the lightweight HTTP server.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // Build the default application composition (repositories -> services -> controllers).
        LmsApplication app = LmsApplication.createDefault();
        // Seed initial sample data so the UI is useful immediately after startup.
        app.seedDemoData();

        // Start the web server and point it to the static frontend folder.
        LmsWebServer webServer = new LmsWebServer(
                app,
                8080,
                Path.of("Demo_LMS_UsingPatterns", "web")
        );
        webServer.start();

        System.out.println("LMS web app is running on http://localhost:8080");
    }
}
