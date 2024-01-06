package com.example.socialnetwork_gui.persistance.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    Optional<T> save(T obj);
    Optional<T> getById(ID id);
    Optional<T> update(T obj);
    Optional<T> deleteById(ID id);

    List<T> getAll();

}
