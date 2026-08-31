package com.tutoring.repository;

import java.util.List;
import java.util.Optional;

/**
 * Minimal CRUD contract shared by all in-memory repositories.
 *
 * @param <T>  the entity type
 * @param <ID> the entity's id type
 */
public interface Repository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    boolean existsById(ID id);

    long count();
}
