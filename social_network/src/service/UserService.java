package service;

import persistance.model.Friendship;
import persistance.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User addUser(User user);
    User deleteUserById(Long id);
    List<User> getAllUsers();
    void deleteUserByUid(UUID userUid);
    User getUserByUid(UUID userUid);
    Friendship addFriendship(Friendship friendship);
    List<Friendship> getAllFriendships();
    Friendship getFriendshipByUserIds(UUID firstUserUid, UUID secondUserUid);
    Friendship deleteFriendshipById(Long id);
    List<List<User>> getFriendshipConnectedComponents();
    List<User> getLongestComponent();
}
