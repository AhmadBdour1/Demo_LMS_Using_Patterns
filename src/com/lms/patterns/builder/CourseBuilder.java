package com.lms.patterns.builder;

import com.lms.domain.course.Course;
import com.lms.domain.lesson.Lesson;
import com.lms.domain.user.Instructor;

public interface CourseBuilder {
    CourseBuilder withId(String id);

    CourseBuilder withTitle(String title);

    CourseBuilder withDescription(String description);

    CourseBuilder withInstructor(Instructor instructor);

    CourseBuilder addLesson(Lesson lesson);

    Course build();
}
