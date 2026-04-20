package com.lms.controller;

import com.lms.domain.course.Course;
import com.lms.domain.lesson.Lesson;
import com.lms.service.course.CourseService;

import java.util.List;
import java.util.Objects;

public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = Objects.requireNonNull(courseService, "courseService cannot be null");
    }

    public Course createCourse(Course course) {
        return courseService.createCourse(course);
    }

    public Course assignInstructor(String courseId, String instructorId) {
        return courseService.assignInstructor(courseId, instructorId);
    }

    public Course enrollStudent(String courseId, String studentId) {
        return courseService.enrollStudent(courseId, studentId);
    }

    public Course addLesson(String courseId, Lesson lesson) {
        return courseService.addLesson(courseId, lesson);
    }

    public Course createCompleteCourse(String courseId, String title, String description, String instructorId, List<Lesson> lessons) {
        return courseService.createCompleteCourse(courseId, title, description, instructorId, lessons);
    }

    public Course getCourse(String courseId) {
        return courseService.getCourseById(courseId);
    }

    public List<Course> listCourses() {
        return courseService.getAllCourses();
    }
}
