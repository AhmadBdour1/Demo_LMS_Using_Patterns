package com.lms.service.course;

import com.lms.domain.course.Course;
import com.lms.domain.lesson.Lesson;

import java.util.List;

public interface CourseService {
    Course createCourse(Course course);

    Course updateCourse(Course course);

    Course getCourseById(String courseId);

    List<Course> getAllCourses();

    void deleteCourse(String courseId);

    Course assignInstructor(String courseId, String instructorId);

    Course enrollStudent(String courseId, String studentId);

    Course addLesson(String courseId, Lesson lesson);

    Course createCompleteCourse(String courseId, String title, String description, String instructorId, List<Lesson> lessons);
}
