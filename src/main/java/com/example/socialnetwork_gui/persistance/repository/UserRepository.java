package com.example.socialnetwork_gui.persistance.repository;

import com.example.socialnetwork_gui.persistance.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> getByUid(UUID uid);
    Optional<User> getByUsername(String username);
    Optional<User> getByEmail(String email);

    List<User> getAllFriendsOnPages(int pageNumber, int pageSize, Long id);

    List<User> getAllNonFriendsOnPages(int pageNumber, int pageSize, Long id);

}
