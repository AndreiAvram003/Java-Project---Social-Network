package com.example.socialnetwork_gui.service;

import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.Message;
import com.example.socialnetwork_gui.persistance.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User addUser(User user);

    Message addMessage(Message message);

    Optional<Message> deleteMessageById(Long id);

    List<Message> getAllMessages();

    List<Message> getChat(Long from,Long to);

    User updateUser(User user);
    Optional<User> deleteUserById(Long id);
    List<User> getAllUsers();


    List<User> getFriendsOnPage(int pageNumber,int pageSize,Long id);

    List<User> getNonFriendsOnPage(int pageNumber,int pageSize,Long id);
    User deleteUserByUid(UUID userUid);
    User getUserByUid(UUID userUid);

    User getUserById(Long id);

    Message getMessageByUserIds(UUID from, UUID to, LocalDateTime data);
    Friendship addFriendship(Friendship friendship);
    List<Friendship> getAllFriendships();
    Friendship getFriendshipByUserIds(UUID firstUserUid, UUID secondUserUid);
    Optional<Friendship> deleteFriendshipById(Long id);
    List<List<User>> getFriendshipConnectedComponents();
    List<User> getLongestComponent();

    List<User> getUsersThatAreNotFriends(Long id);
    List<User> getUsersThatAreFriends(Long id);

    List<Friendship> getAllFriendshipsByUserId(Long id);

    List<Friendship> getAllFriendshipsByUserIdInASpecificMonth(Long id, Long luna);

    Map<Long, User> getUserIdToEntityMapping(List<Long> userIds);

    List<User> getRequests(Long id);
    void changeStatus(Long id1, Long id2,String status);

    Message getMessageByUid(UUID replyUid);

    User getUserByUsernameAndPassword(String username,String password);
}
