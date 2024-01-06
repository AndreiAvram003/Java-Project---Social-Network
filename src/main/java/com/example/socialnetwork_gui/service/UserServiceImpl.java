package com.example.socialnetwork_gui.service;

import com.example.socialnetwork_gui.exception.EntityInvalidException;
import com.example.socialnetwork_gui.exception.EntityNotFoundException;
import com.example.socialnetwork_gui.persistance.model.Entity;
import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.Message;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.repository.FriendshipRepository;
import com.example.socialnetwork_gui.persistance.repository.MessageRepository;
import com.example.socialnetwork_gui.persistance.repository.UserRepository;
import com.example.socialnetwork_gui.utils.GraphUtils;
import com.example.socialnetwork_gui.validator.FriendshipValidator;
import com.example.socialnetwork_gui.validator.UserValidator;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserValidator userValidator;
    private final FriendshipValidator friendshipValidator;

    private final MessageRepository messageRepository;

    public UserServiceImpl(UserRepository userRepository, FriendshipRepository friendshipRepository, UserValidator userValidator, FriendshipValidator friendshipValidator,MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.userValidator = userValidator;
        this.friendshipValidator = friendshipValidator;
        this.messageRepository = messageRepository;
    }

    @Override
    public User addUser(User user) {
        userValidator.validateUser(user);
        userValidator.validateUserDoesNotAlreadyExist(user);
        return userRepository.save(user).orElseThrow();
    }

    @Override
    public Message addMessage(Message message) {
        return messageRepository.save(message).orElseThrow();
    }

    @Override
    public Optional<Message> deleteMessageById(Long id) {
        return messageRepository.deleteById(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.getAll();
    }

    @Override
    public List<Message> getChat(Long from,Long to) {
        return messageRepository.getByIds(from,to);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.update(user).orElseThrow();
    }

    @Override
    public Optional<User> deleteUserById(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }


    @Override
    public List<User> getFriendsOnPage(int pageNumber, int pageSize,Long id) {
        return userRepository.getAllFriendsOnPages(pageNumber,pageSize,id);
    }

    @Override
    public List<User> getNonFriendsOnPage(int pageNumber, int pageSize, Long id) {
        return userRepository.getAllNonFriendsOnPages(pageNumber,pageSize,id);
    }

    @Override
    public User deleteUserByUid(UUID userUid) {
        User userToDelete = this.getUserByUid(userUid);
        userRepository.deleteById(userToDelete.getId());
        return userToDelete;
    }

    @Override
    public User getUserByUid(UUID userUid) {
        return userRepository.getByUid(userUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with uid %s does not exist", userUid)));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d does not exist",id)));
    }

    @Override
    public Message getMessageByUserIds(UUID from, UUID to, LocalDateTime data) {
        User fromUser = this.getUserByUid(from);
        User toUser = this.getUserByUid(to);
        List<Message> messages = messageRepository.getByIds(fromUser.getId(), toUser.getId());
        for (Message message : messages) {
            if (message.getData().equals(data)) {
                return message;
            }
        }

        if (messages.isEmpty()) {
            throw new EntityNotFoundException("No messages found between the specified users");
        }

        throw new EntityNotFoundException("Message not found for the specified date");

    }

    @Override
    public Friendship addFriendship(Friendship friendship) {
        friendshipValidator.validateFriendship(friendship);
        friendshipValidator.validateFriendshipDoesNotAlreadyExist(friendship);
        return friendshipRepository.save(friendship).orElseThrow();
    }

    @Override
    public List<Friendship> getAllFriendships()
    {
        List<Friendship> friendships = friendshipRepository.getAll();
        return friendships.stream().
                filter(friendship -> Objects.equals(friendship.getStatus(), "accepted"))
                .collect(Collectors.toList());
    }

    @Override
    public Friendship getFriendshipByUserIds(UUID firstUserUid, UUID secondUserUid) {
        User user1 = this.getUserByUid(firstUserUid);
        User user2 = this.getUserByUid(secondUserUid);
        Optional<Friendship> friendship1 = friendshipRepository.getByUserIds(user1.getId(), user2.getId());
        Optional<Friendship> friendship2 = friendshipRepository.getByUserIds(user2.getId(), user1.getId());
        if (friendship1.isEmpty() && friendship2.isEmpty()) {
            throw new EntityInvalidException("Friendship does not exist");
        }
        return friendship1.orElseGet(friendship2::get);
    }

    @Override
    public Optional<Friendship> deleteFriendshipById(Long id) {
        return friendshipRepository.deleteById(id);
    }

    @Override
    public List<List<User>> getFriendshipConnectedComponents() {
        Map<Long, List<Long>> graph = this.getFriendshipMapping();
        List<List<Long>> userIdsComponents = GraphUtils.getConnectedComponents(graph);
        return userIdsComponents
                .stream()
                .map(this::mapUserIdsToUsers)
                .toList();
    }

    public List<List<User>> getFriendshipConnectedComponents2() {
        Map<Long, List<Long>> graph = this.getFriendshipMapping();
        List<List<Long>> userIdsComponents = GraphUtils.getLongestConnectedComponents(graph);
        return userIdsComponents
                .stream()
                .map(this::mapUserIdsToUsers)
                .toList();
    }
    public List<User> getLongestComponent() {
        List<User> longest = this.getFriendshipConnectedComponents2()
                .stream()
                .max(Comparator.comparingInt(List::size))
                .orElse(List.of());
        Long id1 = longest.get(0).getId();
        for(List<User> l : this.getFriendshipConnectedComponents()){
            for(User u : l){
                if(u.getId()==id1){
                    return l;
                }
            }
        }
        return null;
    }

    @Override
    public List<Friendship> getAllFriendshipsByUserId(Long id) {

        List<Friendship> friendships = friendshipRepository.getAllByUserId(id);
        return friendships.stream().
                filter(friendship -> Objects.equals(friendship.getStatus(), "accepted"))
                .collect(Collectors.toList());
    }

    @Override
    public List<Friendship> getAllFriendshipsByUserIdInASpecificMonth(Long id, Long luna){
        List<Friendship> friendshipsList = friendshipRepository.getAllByUserId(id);
        return friendshipsList.stream()
                .filter(friendship -> friendship.getCreatedAt().getMonth().getValue()==luna)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersThatAreNotFriends(Long id) {
        List<User> users = userRepository.getAll();
        List<User> notFriends = new ArrayList<>();
        List<Friendship> friendships = friendshipRepository.getAllByUserId(id);
        int[] ok={0};
        users.forEach(user -> {
            ok[0]=1;
            friendships.forEach(friendship -> {
                if(friendship.getIdFirstUser() == user.getId() || friendship.getIdSecondUser() == user.getId()) {
                   ok[0]=0;
                }
            });
            if(ok[0]==1 && user.getId()!=id){
                notFriends.add(user);
            }
        });
        return notFriends;
        }

    @Override
    public List<User> getUsersThatAreFriends(Long id) {
        List<User> users = userRepository.getAll();
        List<User> friends = new ArrayList<>();
        List<Friendship> friendships = friendshipRepository.getAllByUserId(id);
        int[] ok={0};
        users.forEach(user -> {
            ok[0]=0;
            friendships.forEach(friendship -> {
                if(friendship.getIdFirstUser() == user.getId() || friendship.getIdSecondUser() == user.getId()) {
                    ok[0]=1;
                }
            });
            if(ok[0]==1 && user.getId()!=id){
                friends.add(user);
            }
        });
        return friends;
    }

    @Override
    public List<User> getRequests(Long id) {
        return mapUserIdsToUsers(getFriendIdsForUserPending(id));
    }

    @Override
    public void changeStatus(Long id1, Long id2,String status) {
        Friendship friendship = friendshipRepository.getAll().stream()
                .filter(f ->
                        (f.getIdFirstUser().equals(id1) || f.getIdFirstUser().equals(id2)) &&
                                (f.getIdSecondUser().equals(id1) || f.getIdSecondUser().equals(id2))
                )
                .findFirst()
                .orElse(null);
        friendship.setStatus("accepted");
        friendshipRepository.update(friendship);
    }

    @Override
    public Message getMessageByUid(UUID replyUid) {
        return messageRepository.getByUid(replyUid)
                .orElseThrow(() -> new EntityNotFoundException("Message does not exist"));
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        List<User> users = userRepository.getAll();
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Map<Long, User> getUserIdToEntityMapping(List<Long> userIds) {
        return userIds.stream()
                .collect(Collectors.toMap(userId -> userId,userId->userRepository.getById(userId).orElseThrow()));
    }


    private List<User> mapUserIdsToUsers(List<Long> userIds) {
        return userIds
                .stream()
                .map(userId -> userRepository.getById(userId).get())
                .toList();
    }

    private Map<Long, List<Long>> getFriendshipMapping() {
        return userRepository
                .getAll()
                .stream()
                .collect(Collectors.toMap(Entity::getId, user -> getFriendIdsForUser(user.getId())));
    }

    private List<Long> getFriendIdsForUser(Long userId) {
        List<Friendship> friendships = friendshipRepository.getAllByUserId(userId);
        return friendships
                .stream()
                .map(friendship -> !friendship.getIdFirstUser().equals(userId)
                                    ? friendship.getIdFirstUser()
                                    : friendship.getIdSecondUser())
                .toList();
    }

    private List<Long> getFriendIdsForUserPending(Long userId) {
        List<Friendship> friendships = friendshipRepository.getAllByUserId(userId);
        return friendships
                .stream()
                .filter(friendship -> (Objects.equals(friendship.getStatus(), "pending") && friendship.getIdSecondUser().equals(userId)) )
                .map(Friendship::getIdFirstUser)
                .toList();
    }
}
