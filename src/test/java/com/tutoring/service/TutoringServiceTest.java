package com.tutoring.service;

import com.tutoring.exception.ScheduleConflictException;
import com.tutoring.model.Course;
import com.tutoring.model.Session;
import com.tutoring.model.SessionStatus;
import com.tutoring.model.Student;
import com.tutoring.model.Tutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TutoringServiceTest {

    private TutoringService service;
    private Student student;
    private Tutor tutor;
    private Course course;

    @BeforeEach
    void setUp() {
        service = new TutoringService();
        student = service.registerStudent("Alice", "Nguyen", "alice@example.com", "555-0100", "10th Grade");
        tutor = service.registerTutor("Mark", "Ibarra", "mark@example.com", "555-0200", new BigDecimal("30.00"));
        course = service.addCourse("Algebra II", "Algebra", "desc", 60);
    }

    @Test
    void schedulesSessionSuccessfully() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        Session session = service.scheduleSession(student.getId(), tutor.getId(), course.getId(), start, 60);

        assertEquals(SessionStatus.SCHEDULED, session.getStatus());
        assertEquals(1, service.getSessionsForStudent(student.getId()).size());
    }

    @Test
    void rejectsOverlappingSessionForSameTutor() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        service.scheduleSession(student.getId(), tutor.getId(), course.getId(), start, 60);

        Student otherStudent = service.registerStudent("Bob", "Lee", "bob@example.com", "555-0300", "9th Grade");

        assertThrows(ScheduleConflictException.class, () ->
                service.scheduleSession(otherStudent.getId(), tutor.getId(), course.getId(),
                        start.plusMinutes(30), 60));
    }

    @Test
    void calculatesEarningsForCompletedSessionsOnly() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        Session session = service.scheduleSession(student.getId(), tutor.getId(), course.getId(), start, 60);

        // Not completed yet -> no earnings.
        assertEquals(0, new BigDecimal("0.00").compareTo(service.calculateEarnings(tutor.getId())));

        service.startSession(session.getId());
        service.completeSession(session.getId(), "Great session");

        assertEquals(0, new BigDecimal("30.00").compareTo(service.calculateEarnings(tutor.getId())));
    }
}
