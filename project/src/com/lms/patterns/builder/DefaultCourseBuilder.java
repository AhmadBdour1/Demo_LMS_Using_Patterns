package com.lms.patterns.builder;

import com.lms.common.exceptions.ValidationException;
import com.lms.domain.course.Course;
import com.lms.domain.lesson.Lesson;
import com.lms.domain.user.Instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Strict builder that guarantees a valid initial Course aggregate.
 */
public class DefaultCourseBuilder implements CourseBuilder {
    private String id;
    private String title;
    private String description;
    private Instructor instructor;
    private final List<Lesson> lessons = new ArrayList<>();

    @Override
    public CourseBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public CourseBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public CourseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public CourseBuilder withInstructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }

    @Override
    public CourseBuilder addLesson(Lesson lesson) {
        this.lessons.add(Objects.requireNonNull(lesson, "lesson cannot be null"));
        return this;
    }

    @Override
    public Course build() {
        validate();

        Course course = new Course(id.trim(), title.trim(), description.trim());
        course.assignInstructor(instructor);
        lessons.forEach(course::addLesson);
        return course;
    }

    private void validate() {
        if (isBlank(id)) {
            throw new ValidationException("Course id cannot be blank");
        }
        if (isBlank(title)) {
            throw new ValidationException("Course title cannot be blank");
        }
        if (isBlank(description)) {
            throw new ValidationException("Course description cannot be blank");
        }
        if (instructor == null) {
            throw new ValidationException("Course instructor is required");
        }
        if (lessons.isEmpty()) {
            throw new ValidationException("At least one lesson is required to build course");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
