package com.lms.service.course;

import com.lms.common.enums.Role;
import com.lms.common.exceptions.EntityNotFoundException;
import com.lms.common.exceptions.ValidationException;
import com.lms.domain.course.Course;
import com.lms.domain.lesson.Lesson;
import com.lms.domain.user.Instructor;
import com.lms.domain.user.Student;
import com.lms.domain.user.User;
import com.lms.patterns.builder.CourseBuilder;
import com.lms.patterns.builder.DefaultCourseBuilder;
import com.lms.repository.interfaces.CourseRepository;
import com.lms.repository.interfaces.UserRepository;

import java.util.List;
import java.util.Objects;

public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = Objects.requireNonNull(courseRepository, "courseRepository cannot be null");
        this.userRepository = Objects.requireNonNull(userRepository, "userRepository cannot be null");
    }

    @Override
    public Course createCourse(Course course) {
        validateCourse(course);
        if (courseRepository.existsById(course.getId())) {
            throw new ValidationException("Course already exists with id: " + course.getId());
        }
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        validateCourse(course);
        if (!courseRepository.existsById(course.getId())) {
            throw new EntityNotFoundException("Course not found with id: " + course.getId());
        }
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void deleteCourse(String courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Course not found with id: " + courseId);
        }
        courseRepository.deleteById(courseId);
    }

    @Override
    public Course assignInstructor(String courseId, String instructorId) {
        Course course = getCourseById(courseId);
        User user = getUser(instructorId);
        if (user.getRole() != Role.INSTRUCTOR) {
            throw new ValidationException("User is not an instructor: " + instructorId);
        }

        course.assignInstructor((Instructor) user);
        return courseRepository.save(course);
    }

    @Override
    public Course enrollStudent(String courseId, String studentId) {
        Course course = getCourseById(courseId);
        User user = getUser(studentId);
        if (user.getRole() != Role.STUDENT) {
            throw new ValidationException("User is not a student: " + studentId);
        }

        course.enrollStudent((Student) user);
        return courseRepository.save(course);
    }

    @Override
    public Course addLesson(String courseId, Lesson lesson) {
        Course course = getCourseById(courseId);
        if (lesson == null) {
            throw new ValidationException("Lesson cannot be null");
        }
        course.addLesson(lesson);
        return courseRepository.save(course);
    }

    @Override
    public Course createCompleteCourse(String courseId, String title, String description, String instructorId, List<Lesson> lessons) {
        if (courseRepository.existsById(courseId)) {
            throw new ValidationException("Course already exists with id: " + courseId);
        }

        User user = getUser(instructorId);
        if (user.getRole() != Role.INSTRUCTOR) {
            throw new ValidationException("User is not an instructor: " + instructorId);
        }

        CourseBuilder builder = new DefaultCourseBuilder()
                .withId(courseId)
                .withTitle(title)
                .withDescription(description)
                .withInstructor((Instructor) user);

        if (lessons != null) {
            lessons.forEach(builder::addLesson);
        }

        Course completeCourse = builder.build();
        return courseRepository.save(completeCourse);
    }

    private User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    private void validateCourse(Course course) {
        if (course == null) {
            throw new ValidationException("Course cannot be null");
        }
        if (isBlank(course.getId())) {
            throw new ValidationException("Course id cannot be blank");
        }
        if (isBlank(course.getTitle())) {
            throw new ValidationException("Course title cannot be blank");
        }
        if (isBlank(course.getDescription())) {
            throw new ValidationException("Course description cannot be blank");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
