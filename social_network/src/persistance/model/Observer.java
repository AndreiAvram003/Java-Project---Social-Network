package persistance.model;

public interface Observer<T> {
    void updateObserver(T data);
}
