package com.tutoring;

import com.tutoring.model.Course;
import com.tutoring.model.Session;
import com.tutoring.model.Student;
import com.tutoring.model.Tutor;
import com.tutoring.service.TutoringService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Small runnable demonstration of the tutoring-management class library.
 * Not required to use the library — real applications should depend on
 * {@link TutoringService} directly.
 */
public class Main {

    public static void main(String[] args) {
        TutoringService service = new TutoringService();

        Student alice = service.registerStudent("Alice", "Nguyen", "alice@example.com", "555-0100", "10th Grade");
        Tutor mark = service.registerTutor("Mark", "Ibarra", "mark@example.com", "555-0200", new BigDecimal("35.00"));
        mark.addSubject("Algebra");

        Course algebra = service.addCourse("Algebra II", "Algebra", "Functions, polynomials and graphing", 60);

        service.enrollStudentInCourse(alice.getId(), algebra.getId());

        Session session = service.scheduleSession(
                alice.getId(), mark.getId(), algebra.getId(),
                LocalDateTime.now().plusDays(1).withHour(16).withMinute(0), 60);

        System.out.println("Scheduled: " + session);

        service.startSession(session.getId());
        service.completeSession(session.getId(), "Covered quadratic equations; solid progress.");

        System.out.println("Completed: " + service.getSessionOrThrow(session.getId()));
        System.out.println("Mark's earnings so far: $" + service.calculateEarnings(mark.getId()));
    }
}
