package com.example.socialnetwork_gui.facade;



import com.example.socialnetwork_gui.mapper.FriendshipMapper;
import com.example.socialnetwork_gui.mapper.MessageMapper;
import com.example.socialnetwork_gui.mapper.UserMapper;
import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.Message;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendshipDto;
import com.example.socialnetwork_gui.persistance.model.dtos.MessageDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.*;
import com.example.socialnetwork_gui.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final FriendshipMapper friendshipMapper;

    private final MessageMapper messageMapper;
    public UserFacadeImpl(UserService userService, UserMapper userMapper, FriendshipMapper friendshipMapper, MessageMapper messageMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.friendshipMapper = friendshipMapper;
        this.messageMapper = messageMapper;
    }

    @Override
    public UserDto addUser(UserCreateRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        User savedUser = userService.addUser(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserUpdateRequest userUpdateRequest) {
        User userToUpdate = userService.getUserByUid(userUpdateRequest.getUid());
        User user = userMapper.toUser(userToUpdate,userUpdateRequest);
        User updatedUser = userService.updateUser(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return userMapper.toDto(users);
    }


    @Override
    public MessageDto addMessage(MessageRequest messageRequest) {
        User fromUser = userService.getUserByUid(messageRequest.getFrom());
        User toUser = userService.getUserByUid(messageRequest.getTo());
        Message reply = messageRequest.getReply() == null ? null : userService.getMessageByUid(messageRequest.getReply());
        Message mess = messageMapper.toMessage(messageRequest,fromUser.getId(), toUser.getId(), reply);
        userService.addMessage(mess);

        UserDto fromUserDto = userMapper.toDto(fromUser);
        UserDto toUserDto = userMapper.toDto(toUser);
        return messageMapper.toDto(fromUserDto,toUserDto, mess, reply);
    }


    @Override
    public Optional<Message> deleteMessage(MessageDeleteRequest messageRequest) {
        Message messageToDelete =
                userService.getMessageByUserIds(messageRequest.getFrom(),messageRequest.getTo(),messageRequest.getData());
        return userService.deleteMessageById(messageToDelete.getId());
    }

    @Override
    public UserDto deleteUser(UserDeleteRequest userDeleteRequest) {
        User deletedUser = userService.deleteUserByUid(userDeleteRequest.getUserUid());
        return userMapper.toDto(deletedUser);
    }

    @Override
    public FriendshipDto addFriendship(FriendshipRequest request) {
        User firstUser = userService.getUserByUid(request.getFirstUserUid());
        User secondUser = userService.getUserByUid(request.getSecondUserUid());
        String status = request.getStatus();
        Friendship friendship = friendshipMapper.toFriendship(firstUser, secondUser,status);
        userService.addFriendship(friendship);

        UserDto firstUserDto = userMapper.toDto(firstUser);
        UserDto secondUserDto = userMapper.toDto(secondUser);
        return friendshipMapper.toDto(firstUserDto, secondUserDto);
    }



    @Override
    public List<FriendshipDto> getAllFriendships() {
        List<Friendship> friendships = userService.getAllFriendships();
        List<User> users = userService.getAllUsers();
        Map<Long, UserDto> userIdToDtoMapping = userMapper.toDtoMap(users);
        return friendshipMapper.toDto(friendships, userIdToDtoMapping);
    }

    @Override
    public List<MessageDto> getMessagesBetweenUsers(UUID from_id, UUID to_id) {
        User from = userService.getUserByUid(from_id);
        User to = userService.getUserByUid(to_id);
        List<Message> messages = userService.getChat(from.getId(),to.getId());
        List<User> users = userService.getAllUsers();
        Map<Long, UserDto> userIdToDtoMapping = userMapper.toDtoMap(users);
        return messageMapper.toDto(messages,userIdToDtoMapping, getMessageIdToEntityMapping(messages));
    }


    @Override
    public void changeStatus(FriendshipRequest friendshipRequest) {
        UUID uuid1 = friendshipRequest.getFirstUserUid();
        UUID uuid2 =friendshipRequest.getSecondUserUid();
        String status = friendshipRequest.getStatus();
        User user1 = userService.getUserByUid(uuid1);
        User user2 = userService.getUserByUid(uuid2);
        userService.changeStatus(user1.getId(),user2.getId(),status);
    }


    @Override
    public Optional<Friendship> deleteFriendship(FriendshipRequest friendshipRequest) {
        Friendship friendshipToDelete =
                userService.getFriendshipByUserIds(friendshipRequest.getFirstUserUid(), friendshipRequest.getSecondUserUid());
        return userService.deleteFriendshipById(friendshipToDelete.getId());
    }

    @Override
    public List<List<UserDto>> getFriendshipConnectedComponents() {
        List<List<User>> userConnectedComponents = userService.getFriendshipConnectedComponents();
        return userMapper.userComponentsToDtoComponents(userConnectedComponents);
    }
    public List<UserDto> getLongestComponent(){
        List<User> userComponent = userService.getLongestComponent();
        return userMapper.userComponentToDtoComponent(userComponent);
    }
    @Override
    public List<FriendDto> getFriendsForUser(UUID userUid, Long luna) {
        User user = userService.getUserByUid(userUid);
        List<Friendship> friendships = userService.getAllFriendshipsByUserIdInASpecificMonth(user.getId(),luna);
        List<Long> friendIds = this.getFriendIds(friendships, user.getId());
        Map<Long, User> userIdToEntityMapping = userService.getUserIdToEntityMapping(friendIds);
        return friendshipMapper.toFriendDto(friendships, user.getId(), userIdToEntityMapping);
    }

    @Override
    public List<UserDto> getFriendsForUser(UUID userUid) {
        User user = userService.getUserByUid(userUid);
        List<User> users = userService.getUsersThatAreFriends(user.getId());
        return userMapper.toDto(users);
    }

    @Override
    public List<UserDto> getFriendsOnPage(int pageNumber, int pageSize, UUID userUid) {
        User user = userService.getUserByUid(userUid);
        List<User> users = userService.getFriendsOnPage(pageNumber,pageSize,user.getId());
        return userMapper.toDto(users);
    }

    @Override
    public List<UserDto> getNonFriendsOnPage(int pageNumber, int pageSize, UUID userUid) {
        User user = userService.getUserByUid(userUid);
        List<User> users = userService.getNonFriendsOnPage(pageNumber,pageSize,user.getId());
        return userMapper.toDto(users);
    }

    @Override

    public List<UserDto> getUsersThatAreNotFriends(UUID userUid){
        User user = userService.getUserByUid(userUid);
        List<User> notFriends = userService.getUsersThatAreNotFriends(user.getId());
        return userMapper.toDto(notFriends);
    }

    @Override
    public List<UserDto> getRequests(UUID uid) {
        User user = userService.getUserByUid(uid);
        List<User> requests = userService.getRequests(user.getId());
        return userMapper.toDto(requests);
    }

    @Override
    public UserDto getUserbyUsernameAndPassword(String username, String password) {
        User user = userService.getUserByUsernameAndPassword(username,password);
        if(user != null) {
            return userMapper.toDto(user);
        }
        else
            return null;
    }


    private List<Long> getFriendIds(List<Friendship> friendships, Long id) {
        return friendships
                .stream()
                .map(friendship -> friendship.getFriendId(id))
                .toList();
    }

    private Map<Long, Message> getMessageIdToEntityMapping(List<Message> messages) {
        return messages
                .stream()
                .collect(Collectors.toMap(Message::getId, message -> message));
    }
}
