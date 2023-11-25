package com.example.socialnetwork_gui.facade;



import com.example.socialnetwork_gui.mapper.FriendshipMapper;
import com.example.socialnetwork_gui.mapper.UserMapper;
import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendshipDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.FriendshipRequest;
import com.example.socialnetwork_gui.request.UserCreateRequest;
import com.example.socialnetwork_gui.request.UserDeleteRequest;
import com.example.socialnetwork_gui.request.UserUpdateRequest;
import com.example.socialnetwork_gui.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final FriendshipMapper friendshipMapper;

    public UserFacadeImpl(UserService userService, UserMapper userMapper, FriendshipMapper friendshipMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.friendshipMapper = friendshipMapper;
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
    public UserDto deleteUser(UserDeleteRequest userDeleteRequest) {
        User deletedUser = userService.deleteUserByUid(userDeleteRequest.getUserUid());
        return userMapper.toDto(deletedUser);
    }

    @Override
    public FriendshipDto addFriendship(FriendshipRequest request) {
        User firstUser = userService.getUserByUid(request.getFirstUserUid());
        User secondUser = userService.getUserByUid(request.getSecondUserUid());
        Friendship friendship = friendshipMapper.toFriendship(firstUser, secondUser);
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
    public void deleteFriendship(FriendshipRequest friendshipRequest) {
        Friendship friendshipToDelete =
                userService.getFriendshipByUserIds(friendshipRequest.getFirstUserUid(), friendshipRequest.getSecondUserUid());
        userService.deleteFriendshipById(friendshipToDelete.getId());
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



    private List<Long> getFriendIds(List<Friendship> friendships, Long id) {
        return friendships
                .stream()
                .map(friendship -> friendship.getFriendId(id))
                .toList();
    }
}
