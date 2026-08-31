package com.tutoring.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A student who can enroll in courses and attend tutoring sessions.
 */
public class Student extends Person {

    private String gradeLevel;
    private final Set<Course> enrolledCourses = new LinkedHashSet<>();

    public Student(String firstName, String lastName, String email, String phone, String gradeLevel) {
        super(firstName, lastName, email, phone);
        this.gradeLevel = gradeLevel;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Set<Course> getEnrolledCourses() {
        return Collections.unmodifiableSet(enrolledCourses);
    }

    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("course must not be null");
        }
        enrolledCourses.add(course);
    }

    public void unenrollFromCourse(Course course) {
        enrolledCourses.remove(course);
    }

    public boolean isEnrolledIn(Course course) {
        return enrolledCourses.contains(course);
    }

    @Override
    public String toString() {
        return "Student{id='%s', name='%s', gradeLevel='%s', courses=%d}"
                .formatted(getId(), getFullName(), gradeLevel, enrolledCourses.size());
    }
}
