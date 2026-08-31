package com.tutoring.exception;

/**
 * Thrown when scheduling a session would overlap with another session
 * for the same student or the same tutor.
 */
public class ScheduleConflictException extends RuntimeException {

    public ScheduleConflictException(String message) {
        super(message);
    }
}
