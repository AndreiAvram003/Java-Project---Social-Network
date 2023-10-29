package facade;

import mapper.FriendshipMapper;
import persistance.model.Friendship;
import persistance.model.dtos.FriendshipDto;
import request.FriendshipRequest;
import request.UserDeleteRequest;
import persistance.model.User;
import persistance.model.dtos.UserDto;
import mapper.UserMapper;
import request.UserCreateRequest;
import service.UserService;

import java.util.List;
import java.util.Map;

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
    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return userMapper.toDto(users);
    }

    @Override
    public void deleteUser(UserDeleteRequest userDeleteRequest) {
        userService.deleteUserByUid(userDeleteRequest.getUserUid());
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
}
