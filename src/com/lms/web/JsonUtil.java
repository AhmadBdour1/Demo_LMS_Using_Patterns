package com.lms.web;

import com.lms.domain.course.Course;
import com.lms.domain.lesson.Lesson;
import com.lms.domain.user.Student;
import com.lms.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Minimal JSON serialization helper.
 * <p>
 * This project intentionally avoids external JSON libraries to keep the foundation plain Java.
 */
public final class JsonUtil {
    private JsonUtil() {
    }

    /**
     * Converts a list of users to a JSON array string.
     */
    public static String usersToJson(List<User> users) {
        return users.stream().map(JsonUtil::userToJson).collect(Collectors.joining(",", "[", "]"));
    }

    /**
     * Converts a list of courses to a JSON array string.
     */
    public static String coursesToJson(List<Course> courses) {
        return courses.stream().map(JsonUtil::courseToJson).collect(Collectors.joining(",", "[", "]"));
    }

    /**
     * Converts a user to a compact JSON object.
     */
    public static String userToJson(User user) {
        return "{"
                + quote("id") + ":" + quote(user.getId()) + ","
                + quote("name") + ":" + quote(user.getName()) + ","
                + quote("email") + ":" + quote(user.getEmail()) + ","
                + quote("role") + ":" + quote(user.getRole().name())
                + "}";
    }

    /**
     * Converts a course with lessons and students to JSON.
     */
    public static String courseToJson(Course course) {
        String lessons = course.getLessons().stream().map(JsonUtil::lessonToJson)
                .collect(Collectors.joining(",", "[", "]"));
        String students = course.getEnrolledStudents().stream().map(JsonUtil::studentToJson)
                .collect(Collectors.joining(",", "[", "]"));

        return "{"
                + quote("id") + ":" + quote(course.getId()) + ","
                + quote("title") + ":" + quote(course.getTitle()) + ","
                + quote("description") + ":" + quote(course.getDescription()) + ","
                + quote("instructorName") + ":" + quote(course.getInstructor() == null ? "" : course.getInstructor().getName()) + ","
                + quote("lessons") + ":" + lessons + ","
                + quote("students") + ":" + students
                + "}";
    }

    private static String lessonToJson(Lesson lesson) {
        return "{"
                + quote("id") + ":" + quote(lesson.getId()) + ","
                + quote("title") + ":" + quote(lesson.getTitle())
                + "}";
    }

    private static String studentToJson(Student student) {
        return "{"
                + quote("id") + ":" + quote(student.getId()) + ","
                + quote("name") + ":" + quote(student.getName())
                + "}";
    }

    private static String quote(String value) {
        return "\"" + escape(value) + "\"";
    }

    /**
     * Escapes JSON-sensitive characters.
     */
    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
