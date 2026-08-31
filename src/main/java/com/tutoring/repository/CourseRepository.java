package com.tutoring.repository;

import com.tutoring.model.Course;

import java.util.List;

public class CourseRepository extends AbstractInMemoryRepository<Course, String> {

    public CourseRepository() {
        super(Course::getId);
    }

    public List<Course> findBySubject(String subject) {
        return findAll().stream()
                .filter(c -> c.getSubject().equalsIgnoreCase(subject))
                .toList();
    }
}
