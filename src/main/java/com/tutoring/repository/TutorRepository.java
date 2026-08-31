package com.tutoring.repository;

import com.tutoring.model.Tutor;

import java.util.List;

public class TutorRepository extends AbstractInMemoryRepository<Tutor, String> {

    public TutorRepository() {
        super(Tutor::getId);
    }

    public List<Tutor> findBySubject(String subject) {
        return findAll().stream()
                .filter(t -> t.teachesSubject(subject))
                .toList();
    }
}
