package com.tutoring.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A single scheduled tutoring session tying together a {@link Student}, a {@link Tutor}
 * and a {@link Course} at a specific date/time.
 */
public class Session {

    private final String id;
    private final Student student;
    private final Tutor tutor;
    private final Course course;
    private LocalDateTime scheduledAt;
    private int durationMinutes;
    private SessionStatus status;
    private String notes;

    public Session(Student student, Tutor tutor, Course course, LocalDateTime scheduledAt, int durationMinutes) {
        this.id = UUID.randomUUID().toString();
        this.student = Objects.requireNonNull(student, "student must not be null");
        this.tutor = Objects.requireNonNull(tutor, "tutor must not be null");
        this.course = Objects.requireNonNull(course, "course must not be null");
        this.scheduledAt = Objects.requireNonNull(scheduledAt, "scheduledAt must not be null");
        setDurationMinutes(durationMinutes);
        this.status = SessionStatus.SCHEDULED;
        this.notes = "";
    }

    public String getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void reschedule(LocalDateTime newTime) {
        if (status != SessionStatus.SCHEDULED) {
            throw new IllegalStateException("Only a SCHEDULED session can be rescheduled (current status: " + status + ")");
        }
        this.scheduledAt = Objects.requireNonNull(newTime, "newTime must not be null");
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

    public LocalDateTime getEndsAt() {
        return scheduledAt.plusMinutes(durationMinutes);
    }

    public SessionStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? "" : notes;
    }

    /**
     * Transitions this session to a new status, enforcing valid lifecycle moves.
     */
    public void transitionTo(SessionStatus newStatus) {
        Objects.requireNonNull(newStatus, "newStatus must not be null");
        boolean allowed = switch (status) {
            case SCHEDULED -> newStatus == SessionStatus.IN_PROGRESS
                    || newStatus == SessionStatus.CANCELLED
                    || newStatus == SessionStatus.NO_SHOW;
            case IN_PROGRESS -> newStatus == SessionStatus.COMPLETED
                    || newStatus == SessionStatus.CANCELLED;
            case COMPLETED, CANCELLED, NO_SHOW -> false;
        };
        if (!allowed) {
            throw new IllegalStateException(
                    "Cannot transition session from " + status + " to " + newStatus);
        }
        this.status = newStatus;
    }

    public boolean overlapsWith(Session other) {
        if (other == null) {
            return false;
        }
        return this.scheduledAt.isBefore(other.getEndsAt()) && other.getScheduledAt().isBefore(this.getEndsAt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session session)) return false;
        return id.equals(session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Session{id='%s', student='%s', tutor='%s', course='%s', scheduledAt=%s, status=%s}"
                .formatted(id, student.getFullName(), tutor.getFullName(), course.getName(), scheduledAt, status);
    }
}
