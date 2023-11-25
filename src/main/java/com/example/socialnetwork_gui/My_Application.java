package com.example.socialnetwork_gui;

import com.example.socialnetwork_gui.exception.ExceptionHandler;
import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.facade.UserFacadeImpl;
import com.example.socialnetwork_gui.mapper.FriendshipMapper;
import com.example.socialnetwork_gui.mapper.UserMapper;
import com.example.socialnetwork_gui.persistance.repository.FriendshipRepository;
import com.example.socialnetwork_gui.persistance.repository.UserRepository;
import com.example.socialnetwork_gui.presentation.ConsoleUI.CommandParser;
import com.example.socialnetwork_gui.presentation.ConsoleUI.ConsoleUI;
import com.example.socialnetwork_gui.service.UserServiceImpl;
import com.example.socialnetwork_gui.validator.FriendshipValidator;
import com.example.socialnetwork_gui.validator.FriendshipValidatorImpl;
import com.example.socialnetwork_gui.validator.UserValidator;
import com.example.socialnetwork_gui.validator.UserValidatorImpl;

public class My_Application {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserMapper userMapper;
    private final CommandParser commandParser;
    private final ExceptionHandler exceptionHandler;
    private final FriendshipMapper friendshipMapper;

    public My_Application(UserRepository userRepository, FriendshipRepository friendshipRepository, UserMapper userMapper, CommandParser commandParser, ExceptionHandler exceptionHandler, FriendshipMapper friendshipMapper) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.userMapper = userMapper;
        this.commandParser = commandParser;
        this.exceptionHandler = exceptionHandler;
        this.friendshipMapper = friendshipMapper;
    }

    public void run() {
        UserValidator userValidator = new UserValidatorImpl(userRepository);
        FriendshipValidator friendshipValidator = new FriendshipValidatorImpl(friendshipRepository);
        UserFacade userFacade = new UserFacadeImpl(
                new UserServiceImpl(userRepository, friendshipRepository, userValidator, friendshipValidator),
                userMapper,
                friendshipMapper
        );
        ConsoleUI consoleUI = new ConsoleUI(userFacade, commandParser, exceptionHandler);
        consoleUI.run();
    }
}
