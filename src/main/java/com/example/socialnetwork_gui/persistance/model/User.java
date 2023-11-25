package com.example.socialnetwork_gui.persistance.model;

import java.util.Objects;
import java.util.UUID;

public class User extends Entity<Long> {
    private UUID uid;
    private String username;
    private String password;
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(super.getId(), user.getId()) && Objects.equals(uid, user.uid) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), uid, username, password, email);
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(Long id, UUID uid, String username, String password, String email) {
        super(id);
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
