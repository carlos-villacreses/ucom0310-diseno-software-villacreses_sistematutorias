package com.tutoring.service;

import com.tutoring.exception.EntityNotFoundException;
import com.tutoring.exception.ScheduleConflictException;
import com.tutoring.model.Course;
import com.tutoring.model.Session;
import com.tutoring.model.SessionStatus;
import com.tutoring.model.Student;
import com.tutoring.model.Tutor;
import com.tutoring.repository.CourseRepository;
import com.tutoring.repository.SessionRepository;
import com.tutoring.repository.StudentRepository;
import com.tutoring.repository.TutorRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Facade that coordinates {@link Student}, {@link Tutor}, {@link Course} and {@link Session}
 * repositories and enforces the business rules of the tutoring management system.
 */
public class TutoringService {

    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;
    private final SessionRepository sessionRepository;

    public TutoringService() {
        this(new StudentRepository(), new TutorRepository(), new CourseRepository(), new SessionRepository());
    }

    public TutoringService(StudentRepository studentRepository,
                            TutorRepository tutorRepository,
                            CourseRepository courseRepository,
                            SessionRepository sessionRepository) {
        this.studentRepository = studentRepository;
        this.tutorRepository = tutorRepository;
        this.courseRepository = courseRepository;
        this.sessionRepository = sessionRepository;
    }

    // ----- Registration -----

    public Student registerStudent(String firstName, String lastName, String email, String phone, String gradeLevel) {
        return studentRepository.save(new Student(firstName, lastName, email, phone, gradeLevel));
    }

    public Tutor registerTutor(String firstName, String lastName, String email, String phone, BigDecimal hourlyRate) {
        return tutorRepository.save(new Tutor(firstName, lastName, email, phone, hourlyRate));
    }

    public Course addCourse(String name, String subject, String description, int durationMinutes) {
        return courseRepository.save(new Course(name, subject, description, durationMinutes));
    }

    // ----- Enrollment -----

    public void enrollStudentInCourse(String studentId, String courseId) {
        Student student = getStudentOrThrow(studentId);
        Course course = getCourseOrThrow(courseId);
        student.enrollInCourse(course);
    }

    // ----- Scheduling -----

    /**
     * Schedules a new session, rejecting it if it would overlap with an existing,
     * non-cancelled session for either the student or the tutor.
     */
    public Session scheduleSession(String studentId, String tutorId, String courseId,
                                    LocalDateTime scheduledAt, int durationMinutes) {
        Student student = getStudentOrThrow(studentId);
        Tutor tutor = getTutorOrThrow(tutorId);
        Course course = getCourseOrThrow(courseId);

        Session candidate = new Session(student, tutor, course, scheduledAt, durationMinutes);
        assertNoConflict(candidate);

        return sessionRepository.save(candidate);
    }

    private void assertNoConflict(Session candidate) {
        boolean conflict = sessionRepository.findAll().stream()
                .filter(existing -> isActive(existing.getStatus()))
                .anyMatch(existing ->
                        (existing.getStudent().equals(candidate.getStudent())
                                || existing.getTutor().equals(candidate.getTutor()))
                                && existing.overlapsWith(candidate));
        if (conflict) {
            throw new ScheduleConflictException(
                    "Requested time slot conflicts with an existing session for the student or tutor");
        }
    }

    private boolean isActive(SessionStatus status) {
        return status == SessionStatus.SCHEDULED || status == SessionStatus.IN_PROGRESS;
    }

    public void rescheduleSession(String sessionId, LocalDateTime newTime) {
        Session session = getSessionOrThrow(sessionId);
        LocalDateTime original = session.getScheduledAt();
        session.reschedule(newTime);
        try {
            assertNoConflict(session);
        } catch (ScheduleConflictException e) {
            session.reschedule(original);
            throw e;
        }
    }

    public void startSession(String sessionId) {
        getSessionOrThrow(sessionId).transitionTo(SessionStatus.IN_PROGRESS);
    }

    public void completeSession(String sessionId, String notes) {
        Session session = getSessionOrThrow(sessionId);
        session.transitionTo(SessionStatus.COMPLETED);
        session.setNotes(notes);
    }

    public void cancelSession(String sessionId) {
        getSessionOrThrow(sessionId).transitionTo(SessionStatus.CANCELLED);
    }

    public void markNoShow(String sessionId) {
        getSessionOrThrow(sessionId).transitionTo(SessionStatus.NO_SHOW);
    }

    // ----- Queries -----

    public List<Session> getSessionsForStudent(String studentId) {
        Student student = getStudentOrThrow(studentId);
        return sessionRepository.findByStudent(student);
    }

    public List<Session> getSessionsForTutor(String tutorId) {
        Tutor tutor = getTutorOrThrow(tutorId);
        return sessionRepository.findByTutor(tutor);
    }

    public List<Tutor> findTutorsBySubject(String subject) {
        return tutorRepository.findBySubject(subject);
    }

    /**
     * Total earnings for a tutor across all COMPLETED sessions, pro-rated by session duration.
     */
    public BigDecimal calculateEarnings(String tutorId) {
        Tutor tutor = getTutorOrThrow(tutorId);
        return sessionRepository.findByTutor(tutor).stream()
                .filter(s -> s.getStatus() == SessionStatus.COMPLETED)
                .map(s -> tutor.getHourlyRate()
                        .multiply(BigDecimal.valueOf(s.getDurationMinutes()))
                        .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ----- Lookups -----

    public Student getStudentOrThrow(String studentId) {
        return studentRepository.findById(Objects.requireNonNull(studentId))
                .orElseThrow(() -> EntityNotFoundException.forId("Student", studentId));
    }

    public Tutor getTutorOrThrow(String tutorId) {
        return tutorRepository.findById(Objects.requireNonNull(tutorId))
                .orElseThrow(() -> EntityNotFoundException.forId("Tutor", tutorId));
    }

    public Course getCourseOrThrow(String courseId) {
        return courseRepository.findById(Objects.requireNonNull(courseId))
                .orElseThrow(() -> EntityNotFoundException.forId("Course", courseId));
    }

    public Session getSessionOrThrow(String sessionId) {
        return sessionRepository.findById(Objects.requireNonNull(sessionId))
                .orElseThrow(() -> EntityNotFoundException.forId("Session", sessionId));
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public TutorRepository getTutorRepository() {
        return tutorRepository;
    }

    public CourseRepository getCourseRepository() {
        return courseRepository;
    }

    public SessionRepository getSessionRepository() {
        return sessionRepository;
    }
}
