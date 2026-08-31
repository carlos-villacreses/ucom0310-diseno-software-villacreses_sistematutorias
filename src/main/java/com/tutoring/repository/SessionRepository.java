package com.tutoring.repository;

import com.tutoring.model.Session;
import com.tutoring.model.Student;
import com.tutoring.model.Tutor;

import java.util.List;

public class SessionRepository extends AbstractInMemoryRepository<Session, String> {

    public SessionRepository() {
        super(Session::getId);
    }

    public List<Session> findByStudent(Student student) {
        return findAll().stream()
                .filter(s -> s.getStudent().equals(student))
                .toList();
    }

    public List<Session> findByTutor(Tutor tutor) {
        return findAll().stream()
                .filter(s -> s.getTutor().equals(tutor))
                .toList();
    }
}
