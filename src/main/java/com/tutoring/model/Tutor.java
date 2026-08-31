package com.tutoring.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A tutor who teaches one or more subjects and runs tutoring sessions.
 */
public class Tutor extends Person {

    private final Set<String> subjects = new LinkedHashSet<>();
    private BigDecimal hourlyRate;

    public Tutor(String firstName, String lastName, String email, String phone, BigDecimal hourlyRate) {
        super(firstName, lastName, email, phone);
        setHourlyRate(hourlyRate);
    }

    public Set<String> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    public void addSubject(String subject) {
        subjects.add(Person.requireNonBlank(subject, "subject"));
    }

    public void removeSubject(String subject) {
        subjects.remove(subject);
    }

    public boolean teachesSubject(String subject) {
        return subjects.contains(subject);
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        if (hourlyRate == null || hourlyRate.signum() < 0) {
            throw new IllegalArgumentException("hourlyRate must be non-negative");
        }
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "Tutor{id='%s', name='%s', subjects=%s, hourlyRate=%s}"
                .formatted(getId(), getFullName(), subjects, hourlyRate);
    }
}
