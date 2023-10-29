package persistance.repository;

import persistance.model.Entity;
import persistance.model.Observable;
import exception.EntityNotFoundException;

import java.util.*;

public abstract class BaseInMemoryRepository<T extends Entity<Long>> extends Observable<Long> implements BaseRepository<T, Long> {
    protected Map<Long, T> cache = new HashMap<>();
    private Long id = 1L;

    @Override
    public T save(T obj) {
        obj.setId(id);
        cache.put(id, obj);
        id++;
        return obj;
    }

    @Override
    public Optional<T> getById(Long id) {
        T obj = cache.get(id);
        return Optional.ofNullable(obj);
    }

    @Override
    public T update(T obj) {
        if (!cache.containsKey(obj.getId())) {
            throw new EntityNotFoundException("Object does not exist");
        }
        cache.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public T deleteById(Long id) {
        if (!cache.containsKey(id)) {
            throw new EntityNotFoundException("Object does not exist");
        }
        T obj = cache.get(id);
        cache.remove(id);
        notifyAllObservers(id);
        return obj;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(cache.values());
    }
}
