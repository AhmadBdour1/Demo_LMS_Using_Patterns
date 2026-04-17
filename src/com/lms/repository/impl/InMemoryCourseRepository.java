package com.lms.repository.impl;

import com.lms.domain.course.Course;
import com.lms.repository.interfaces.CourseRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCourseRepository implements CourseRepository {
    private final Map<String, Course> courses = new LinkedHashMap<>();

    @Override
    public Course save(Course entity) {
        courses.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Course> findById(String id) {
        return Optional.ofNullable(courses.get(id));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    @Override
    public void deleteById(String id) {
        courses.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return courses.containsKey(id);
    }
}
