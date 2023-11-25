package com.example.socialnetwork_gui.presentation.ConsoleUI;

import com.example.socialnetwork_gui.exception.ParseException;
import com.example.socialnetwork_gui.request.FilterByUidAndMonthRequest;
import com.example.socialnetwork_gui.request.FriendshipRequest;
import com.example.socialnetwork_gui.request.UserCreateRequest;
import com.example.socialnetwork_gui.request.UserDeleteRequest;

import java.util.List;
import java.util.UUID;

public class ParsedCommandFactory {
    private static final ParsedCommandFactory instance = new ParsedCommandFactory();

    public static ParsedCommandFactory getInstance() {
        return instance;
    }

    public ParsedCommand getParsedCommand(Command command, List<String> args) {
        this.validateNumberOfArgs(args, command.getNumberOfArgs());
        return switch(command) {
            case ADD_USER -> getUserCreateRequestCommand(args);
            case PRINT_USERS -> getPrintUsersCommand();
            case DELETE_USER -> getDeleteUserCommand(args);
            case ADD_FRIENDSHIP -> getAddFriendshipCommand(args);
            case PRINT_FRIENDSHIPS -> getPrintFriendshipsCommand();
            case DELETE_FRIENDSHIP -> getDeleteFriendshipCommand(args);
            case PRINT_COMPONENTS -> getPrintComponentsCommand();
            case PRINT_LONGEST_COMPONENT -> getPrintLongestComponent();
            case PRINT_FRIENDS -> getPrintFriends(args);
            case HELP -> getHelpCommand();
            case EXIT -> getExitCommand();
        };
    }

    private ParsedCommand getUserCreateRequestCommand(List<String> args) {
        String username = args.get(0);
        String password = args.get(1);
        String email = args.get(2);
        UserCreateRequest userCreateRequest = new UserCreateRequest(username, password, email);
        return new ParsedCommand(Command.ADD_USER, userCreateRequest);
    }

    private ParsedCommand getPrintUsersCommand() {
        return new ParsedCommand(Command.PRINT_USERS, null);
    }

    private ParsedCommand getPrintFriendshipsCommand() {
        return new ParsedCommand(Command.PRINT_FRIENDSHIPS, null);
    }

    private ParsedCommand getExitCommand() {
        return new ParsedCommand(Command.EXIT, null);
    }

    private ParsedCommand getPrintFriends(List<String>args) {
        UUID uid = extractUid(args.get(0));
        Long luna = Long.valueOf(args.get(1));
        FilterByUidAndMonthRequest uid_luna = new FilterByUidAndMonthRequest(uid,luna);
        return new ParsedCommand(Command.PRINT_FRIENDS,uid_luna);}

    private ParsedCommand getPrintLongestComponent(){return new ParsedCommand(Command.PRINT_LONGEST_COMPONENT,null);}

    private ParsedCommand getHelpCommand() {
        return new ParsedCommand(Command.HELP, null);
    }

    private ParsedCommand getDeleteUserCommand(List<String> args) {
        UUID userUid = this.extractUid(args.get(0));
        UserDeleteRequest userDeleteRequest = new UserDeleteRequest(userUid);
        return new ParsedCommand(Command.DELETE_USER, userDeleteRequest);
    }

    private ParsedCommand getAddFriendshipCommand(List<String> args) {
        UUID firstUserUid = this.extractUid(args.get(0));
        UUID secondUserUid = this.extractUid(args.get(1));
        FriendshipRequest request = new FriendshipRequest(firstUserUid, secondUserUid);
        return new ParsedCommand(Command.ADD_FRIENDSHIP, request);
    }

    private ParsedCommand getDeleteFriendshipCommand(List<String> args) {
        UUID firstUserUid = this.extractUid(args.get(0));
        UUID secondUserUid = this.extractUid(args.get(1));
        FriendshipRequest request = new FriendshipRequest(firstUserUid, secondUserUid);
        return new ParsedCommand(Command.DELETE_FRIENDSHIP, request);
    }

    private ParsedCommand getPrintComponentsCommand() {
        return new ParsedCommand(Command.PRINT_COMPONENTS, null);
    }

    private void validateNumberOfArgs(List<String> args, int validNumberOfArgs) {
        if (args.size() != validNumberOfArgs) {
            throw new ParseException(String.format("Invalid number of args (REQUIRED: %d args)", validNumberOfArgs));
        }
    }

    private UUID extractUid(String str) {
        try {
            return UUID.fromString(str);
        } catch (Exception e) {
            throw new ParseException(String.format("%s is not a valid UUID", str));
        }

    }

    private ParsedCommandFactory() {}

}
