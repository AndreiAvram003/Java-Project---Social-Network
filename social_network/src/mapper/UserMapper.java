package mapper;

import persistance.model.User;
import persistance.model.dtos.UserDto;
import request.UserCreateRequest;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User toUser(UserCreateRequest userRequest);
    UserDto toDto(User user);
    List<UserDto> toDto(List<User> users);
    Map<Long, UserDto> toDtoMap(List<User> users);
    List<List<UserDto>> userComponentsToDtoComponents(List<List<User>> userComponents);
    List<UserDto> userComponentToDtoComponent(List<User> userComponent);
}
