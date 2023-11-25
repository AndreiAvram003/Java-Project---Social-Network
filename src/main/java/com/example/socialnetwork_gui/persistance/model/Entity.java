package com.example.socialnetwork_gui.persistance.model;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
