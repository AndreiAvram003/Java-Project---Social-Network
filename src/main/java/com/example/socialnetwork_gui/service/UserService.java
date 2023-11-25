package com.example.socialnetwork_gui.service;

import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User addUser(User user);

    User updateUser(User user);
    Optional<User> deleteUserById(Long id);
    List<User> getAllUsers();
    User deleteUserByUid(UUID userUid);
    User getUserByUid(UUID userUid);
    Friendship addFriendship(Friendship friendship);
    List<Friendship> getAllFriendships();
    Friendship getFriendshipByUserIds(UUID firstUserUid, UUID secondUserUid);
    Optional<Friendship> deleteFriendshipById(Long id);
    List<List<User>> getFriendshipConnectedComponents();
    List<User> getLongestComponent();

    List<Friendship> getAllFriendshipsByUserId(Long id);

    List<Friendship> getAllFriendshipsByUserIdInASpecificMonth(Long id, Long luna);

    Map<Long, User> getUserIdToEntityMapping(List<Long> userIds);
}
