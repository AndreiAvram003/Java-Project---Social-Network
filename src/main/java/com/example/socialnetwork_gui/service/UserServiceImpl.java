package com.example.socialnetwork_gui.service;

import com.example.socialnetwork_gui.exception.EntityInvalidException;
import com.example.socialnetwork_gui.exception.EntityNotFoundException;
import com.example.socialnetwork_gui.persistance.model.Entity;
import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.repository.FriendshipRepository;
import com.example.socialnetwork_gui.persistance.repository.UserRepository;
import com.example.socialnetwork_gui.utils.GraphUtils;
import com.example.socialnetwork_gui.validator.FriendshipValidator;
import com.example.socialnetwork_gui.validator.UserValidator;

import java.util.*;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserValidator userValidator;
    private final FriendshipValidator friendshipValidator;

    public UserServiceImpl(UserRepository userRepository, FriendshipRepository friendshipRepository, UserValidator userValidator, FriendshipValidator friendshipValidator) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.userValidator = userValidator;
        this.friendshipValidator = friendshipValidator;
    }

    @Override
    public User addUser(User user) {
        userValidator.validateUser(user);
        userValidator.validateUserDoesNotAlreadyExist(user);
        return userRepository.save(user).orElseThrow();
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
    public Friendship addFriendship(Friendship friendship) {
        friendshipValidator.validateFriendship(friendship);
        friendshipValidator.validateFriendshipDoesNotAlreadyExist(friendship);
        return friendshipRepository.save(friendship).orElseThrow();
    }

    @Override
    public List<Friendship> getAllFriendships() {
        return friendshipRepository.getAll();
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
        return friendshipRepository.getAllByUserId(id);
    }

    @Override
    public List<Friendship> getAllFriendshipsByUserIdInASpecificMonth(Long id, Long luna){
        List<Friendship> friendshipsList = friendshipRepository.getAllByUserId(id);
        return friendshipsList.stream()
                .filter(friendship -> friendship.getCreatedAt().getMonth().getValue()==luna)
                .collect(Collectors.toList());
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
}
