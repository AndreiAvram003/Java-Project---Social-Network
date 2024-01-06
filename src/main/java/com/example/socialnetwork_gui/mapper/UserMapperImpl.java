package com.example.socialnetwork_gui.mapper;



import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.UserCreateRequest;
import com.example.socialnetwork_gui.persistance.model.Entity;
import com.example.socialnetwork_gui.request.UserUpdateRequest;

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
    public User toUser(User userToUpdate,UserUpdateRequest userUpdateRequest) {
        return new User(
                userToUpdate.getId(),
                userToUpdate.getUid(),
                userUpdateRequest.getUsername(),
                userToUpdate.getPassword(),
                userUpdateRequest.getEmail()
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
    public Map<List<Long>, List<User>> toDtosMap(List<User> users) {
        return null;
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

