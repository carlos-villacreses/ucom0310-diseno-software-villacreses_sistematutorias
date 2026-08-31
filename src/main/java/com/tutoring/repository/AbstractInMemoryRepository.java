package com.tutoring.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Thread-safe, in-memory {@link Repository} implementation backed by a {@link ConcurrentHashMap}.
 *
 * @param <T>  the entity type
 * @param <ID> the entity's id type
 */
public abstract class AbstractInMemoryRepository<T, ID> implements Repository<T, ID> {

    private final Map<ID, T> store = new ConcurrentHashMap<>();
    private final Function<T, ID> idExtractor;

    protected AbstractInMemoryRepository(Function<T, ID> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public T save(T entity) {
        store.put(idExtractor.apply(entity), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void deleteById(ID id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return store.containsKey(id);
    }

    @Override
    public long count() {
        return store.size();
    }
}
