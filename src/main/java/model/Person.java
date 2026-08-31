package com.tutoring.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Base type for any human participant in the tutoring system (Student, Tutor).
 */
public abstract class Person {

    private final String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    protected Person(String firstName, String lastName, String email, String phone) {
        this.id = UUID.randomUUID().toString();
        this.firstName = requireNonBlank(firstName, "firstName");
        this.lastName = requireNonBlank(lastName, "lastName");
        this.email = requireNonBlank(email, "email");
        this.phone = phone;
    }

    protected static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = requireNonBlank(firstName, "firstName");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = requireNonBlank(lastName, "lastName");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = requireNonBlank(email, "email");
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
