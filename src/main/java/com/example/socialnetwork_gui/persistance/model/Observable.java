package com.example.socialnetwork_gui.persistance.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {
    private List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void notifyAllObservers(T data) {
        observers.forEach(observer -> observer.updateObserver(data));
    }
}
