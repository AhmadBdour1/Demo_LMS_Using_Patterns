package com.lms.domain.course;

import com.lms.domain.lesson.Lesson;
import com.lms.domain.user.Instructor;
import com.lms.domain.user.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String id;
    private String title;
    private String description;
    private Instructor instructor;
    private final List<Lesson> lessons;
    private final List<Student> enrolledStudents;

    public Course(String id, String title, String description) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.description = Objects.requireNonNull(description, "description cannot be null");
        this.lessons = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "description cannot be null");
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void assignInstructor(Instructor instructor) {
        this.instructor = Objects.requireNonNull(instructor, "instructor cannot be null");
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(Objects.requireNonNull(lesson, "lesson cannot be null"));
    }

    public List<Student> getEnrolledStudents() {
        return Collections.unmodifiableList(enrolledStudents);
    }

    public void enrollStudent(Student student) {
        Objects.requireNonNull(student, "student cannot be null");
        boolean alreadyEnrolled = enrolledStudents.stream()
                .anyMatch(existing -> existing.getId().equals(student.getId()));
        if (!alreadyEnrolled) {
            enrolledStudents.add(student);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructor=" + (instructor != null ? instructor.getName() : "unassigned") +
                ", lessons=" + lessons.size() +
                ", enrolledStudents=" + enrolledStudents.size() +
                '}';
    }
}
