package com.lms.web;

import com.lms.LmsApplication;
import com.lms.common.enums.Role;
import com.lms.controller.CourseController;
import com.lms.controller.UserController;
import com.lms.domain.course.Course;
import com.lms.domain.lesson.Lesson;
import com.lms.domain.user.Admin;
import com.lms.domain.user.Instructor;
import com.lms.domain.user.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Lightweight HTTP adapter for LMS use-cases.
 * <p>
 * Responsibilities:
 * - Serve static frontend files
 * - Expose REST-like endpoints
 * - Map HTTP requests to controllers
 */
public class LmsWebServer {
    private final HttpServer server;
    private final UserController userController;
    private final CourseController courseController;
    private final Path webRoot;

    /**
     * Creates and configures endpoint contexts.
     */
    public LmsWebServer(LmsApplication app, int port, Path webRoot) throws IOException {
        this.userController = app.getUserController();
        this.courseController = app.getCourseController();
        this.webRoot = webRoot;

        // Java built-in HTTP server keeps the foundation framework-free.
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.createContext("/", this::handleStatic);
        this.server.createContext("/api/users", this::handleUsers);
        this.server.createContext("/api/courses", this::handleCourses);
    }

    public void start() {
        server.start();
    }

    /**
     * Serves index/html/css/js assets from the web root directory.
     */
    private void handleStatic(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }

        String rawPath = exchange.getRequestURI().getPath();
        String fileName = rawPath.equals("/") ? "index.html" : rawPath.substring(1);
        Path target = webRoot.resolve(fileName).normalize();
        // Security check prevents path traversal outside configured web root.
        if (!target.startsWith(webRoot) || !Files.exists(target) || Files.isDirectory(target)) {
            sendText(exchange, 404, "Not Found");
            return;
        }

        byte[] content = Files.readAllBytes(target);
        exchange.getResponseHeaders().set("Content-Type", contentType(fileName));
        exchange.sendResponseHeaders(200, content.length);
        exchange.getResponseBody().write(content);
        exchange.close();
    }

    /**
     * Handles user listing and creation.
     */
    private void handleUsers(HttpExchange exchange) throws IOException {
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 200, JsonUtil.usersToJson(userController.listUsers()));
            return;
        }

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }

        try {
            Map<String, String> form = FormUtil.parseBody(exchange.getRequestBody());
            String id = form.getOrDefault("id", "");
            String name = form.getOrDefault("name", "");
            String email = form.getOrDefault("email", "");
            Role role = Role.valueOf(form.getOrDefault("role", "STUDENT").toUpperCase());

            // Role-based construction keeps domain type explicit and future-factory-ready.
            switch (role) {
                case STUDENT -> userController.createUser(new Student(id, name, email));
                case INSTRUCTOR -> userController.createUser(new Instructor(id, name, email));
                case ADMIN -> userController.createUser(new Admin(id, name, email));
            }
            sendJson(exchange, 201, JsonUtil.usersToJson(userController.listUsers()));
        } catch (Exception ex) {
            sendText(exchange, 400, ex.getMessage());
        }
    }

    /**
     * Handles course listing, creation, assigning instructor, and enrolling students.
     */
    private void handleCourses(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod()) && "/api/courses".equals(path)) {
            sendJson(exchange, 200, JsonUtil.coursesToJson(courseController.listCourses()));
            return;
        }

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }

        try {
            Map<String, String> form = FormUtil.parseBody(exchange.getRequestBody());
            if ("/api/courses".equals(path)) {
                courseController.createCourse(new Course(
                        form.getOrDefault("id", ""),
                        form.getOrDefault("title", ""),
                        form.getOrDefault("description", "")
                ));
                sendJson(exchange, 201, JsonUtil.coursesToJson(courseController.listCourses()));
                return;
            }
            if ("/api/courses/complete".equals(path)) {
                List<Lesson> lessons = parseLessons(form);
                courseController.createCompleteCourse(
                        form.getOrDefault("id", ""),
                        form.getOrDefault("title", ""),
                        form.getOrDefault("description", ""),
                        form.getOrDefault("instructorId", ""),
                        lessons
                );
                sendJson(exchange, 201, JsonUtil.coursesToJson(courseController.listCourses()));
                return;
            }

            // Basic route parsing for "/api/courses/{id}/action".
            String[] parts = path.split("/");
            if (parts.length == 5 && "assign-instructor".equals(parts[4])) {
                courseController.assignInstructor(parts[3], form.getOrDefault("instructorId", ""));
                sendJson(exchange, 200, JsonUtil.coursesToJson(courseController.listCourses()));
                return;
            }
            if (parts.length == 5 && "enroll-student".equals(parts[4])) {
                courseController.enrollStudent(parts[3], form.getOrDefault("studentId", ""));
                sendJson(exchange, 200, JsonUtil.coursesToJson(courseController.listCourses()));
                return;
            }

            sendText(exchange, 404, "Endpoint Not Found");
        } catch (Exception ex) {
            sendText(exchange, 400, ex.getMessage());
        }
    }

    /**
     * Builds lesson objects from a simple pipe-separated titles payload.
     * Example: "Intro|OOP Basics|Patterns Overview"
     */
    private List<Lesson> parseLessons(Map<String, String> form) {
        String lessonTitles = form.getOrDefault("lessonTitles", "");
        List<Lesson> lessons = new ArrayList<>();
        if (lessonTitles.isBlank()) {
            return lessons;
        }

        String[] titles = lessonTitles.split("\\|");
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i].trim();
            if (!title.isEmpty()) {
                String lessonId = "les-builder-" + (i + 1);
                lessons.add(new Lesson(lessonId, title, "Builder-created lesson content placeholder."));
            }
        }
        return lessons;
    }

    /**
     * Resolves Content-Type for static files.
     */
    private String contentType(String fileName) {
        if (fileName.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        }
        if (fileName.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        }
        return "text/html; charset=UTF-8";
    }

    /**
     * Sends JSON payload with status code.
     */
    private void sendJson(HttpExchange exchange, int code, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendBytes(exchange, code, body.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Sends plain-text payload with status code.
     */
    private void sendText(HttpExchange exchange, int code, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        sendBytes(exchange, code, body.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Writes response body and closes exchange.
     */
    private void sendBytes(HttpExchange exchange, int code, byte[] bytes) throws IOException {
        exchange.sendResponseHeaders(code, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}
