package mapper;

import persistance.model.Entity;
import persistance.model.User;
import persistance.model.dtos.UserDto;
import request.UserCreateRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapperImpl implements UserMapper {
    @Override
    public User toUser(UserCreateRequest userRequest) {
        return new User(
                null,
                UUID.randomUUID(),
                userRequest.getUsername(),
                userRequest.getPassword(),
                userRequest.getEmail()
        );
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(
                user.getUid(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Override
    public List<UserDto> toDto(List<User> users) {
        return users
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Map<Long, UserDto> toDtoMap(List<User> users) {
        return users
                .stream()
                .collect(Collectors.toMap(Entity::getId, this::toDto));
    }

    @Override
    public List<List<UserDto>> userComponentsToDtoComponents(List<List<User>> userComponents) {
        return userComponents
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<UserDto> userComponentToDtoComponent(List<User> userComponent) {
        return userComponent.stream().map(this::toDto).toList();
    }
}

