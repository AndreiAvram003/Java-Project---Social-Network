package com.example.socialnetwork_gui.persistance.repository;

import com.example.socialnetwork_gui.persistance.model.Entity;
import com.example.socialnetwork_gui.persistance.model.Observable;

import java.util.*;


public abstract class BaseInMemoryRepository<T extends Entity<Long>> extends Observable<Long> implements BaseRepository<T, Long> {
    protected Map<Long, T> cache = new HashMap<>();
    private Long id = 1L;

    @Override
    public Optional<T> save(T obj) {
        obj.setId(id);
        cache.put(id, obj);
        id++;
        return Optional.of(obj);
    }

    @Override
    public Optional<T> getById(Long id) {
        T obj = cache.get(id);
        return Optional.ofNullable(obj);
    }

    @Override
    public Optional<T> update(T obj) {
        if (!cache.containsKey(obj.getId())) {
            return Optional.empty();
        }
        cache.put(obj.getId(), obj);
        return Optional.of(obj);
    }

    @Override
    public Optional<T> deleteById(Long id) {
        if (!cache.containsKey(id)) {
            return Optional.empty();
        }
        T obj = cache.get(id);
        cache.remove(id);
        notifyAllObservers(id);
        return Optional.of(obj);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(cache.values());
    }
}