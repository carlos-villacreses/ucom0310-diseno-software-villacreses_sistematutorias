package com.tutoring.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a subject/course offered by the tutoring system (e.g., "Algebra II").
 */
public class Course {

    private final String id;
    private String name;
    private String subject;
    private String description;
    private int durationMinutes;

    public Course(String name, String subject, String description, int durationMinutes) {
        this.id = UUID.randomUUID().toString();
        this.name = Person.requireNonBlank(name, "name");
        this.subject = Person.requireNonBlank(subject, "subject");
        this.description = description == null ? "" : description;
        setDurationMinutes(durationMinutes);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Person.requireNonBlank(name, "name");
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = Person.requireNonBlank(subject, "subject");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("durationMinutes must be positive");
        }
        this.durationMinutes = durationMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course{id='%s', name='%s', subject='%s', durationMinutes=%d}"
                .formatted(id, name, subject, durationMinutes);
    }
}
