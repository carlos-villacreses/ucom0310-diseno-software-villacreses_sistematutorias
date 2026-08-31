package com.tutoring.exception;

/**
 * Thrown when a requested entity (Student, Tutor, Course, Session) cannot be found by id.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException forId(String entityType, String id) {
        return new EntityNotFoundException(entityType + " not found with id: " + id);
    }
}
