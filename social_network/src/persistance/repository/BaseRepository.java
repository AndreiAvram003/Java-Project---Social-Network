package persistance.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    T save(T obj);
    Optional<T> getById(ID id);
    T update(T obj);
    T deleteById(ID id);
    List<T> getAll();
}
