package com.tutoring.repository;

import com.tutoring.model.Student;

import java.util.List;

public class StudentRepository extends AbstractInMemoryRepository<Student, String> {

    public StudentRepository() {
        super(Student::getId);
    }

    public List<Student> findByGradeLevel(String gradeLevel) {
        return findAll().stream()
                .filter(s -> s.getGradeLevel() != null && s.getGradeLevel().equalsIgnoreCase(gradeLevel))
                .toList();
    }

    public List<Student> findByEmail(String email) {
        return findAll().stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .toList();
    }
}
