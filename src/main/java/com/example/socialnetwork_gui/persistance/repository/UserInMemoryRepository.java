package com.example.socialnetwork_gui.persistance.repository;

import com.example.socialnetwork_gui.persistance.model.User;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class UserInMemoryRepository extends BaseInMemoryRepository<User> implements UserRepository {
    @Override
    public Optional<User> getByUid(UUID uid) {
        return this.getByFilter(user -> user.getUid().equals(uid));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return this.getByFilter(user -> user.getUsername().equals(username));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return this.getByFilter(user -> user.getEmail().equals(email));
    }

    private Optional<User> getByFilter(Function<User, Boolean> filterFct) {
        return cache
                .values()
                .stream()
                .filter(filterFct::apply)
                .findAny();
    }
}
