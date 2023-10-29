package presentation;

import exception.ApplicationException;
import exception.ExceptionHandler;
import facade.UserFacade;
import persistance.model.dtos.FriendshipDto;
import request.FriendshipRequest;
import request.UserCreateRequest;
import request.UserDeleteRequest;
import persistance.model.dtos.UserDto;
import exception.ParseException;

import java.util.*;
import java.util.function.Consumer;

public class ConsoleUI implements UserInterface {
    private final Map<Command, Consumer<ParsedCommand>> commandHandler = Map.of(
            Command.ADD_USER, this::addUser,
            Command.PRINT_USERS, this::printUsers,
            Command.DELETE_USER, this::deleteUser,
            Command.PRINT_FRIENDSHIPS, this::printFriendships,
            Command.DELETE_FRIENDSHIP, this::deleteFriendship,
            Command.ADD_FRIENDSHIP, this::addFriendship,
            Command.PRINT_COMPONENTS, this::printFriendshipComponents,
            Command.PRINT_LONGEST_COMPONENT, this::printLongestComponent,
            Command.HELP, this::help
    );

    private final UserFacade userFacade;
    private final CommandParser commandParser;
    private final ExceptionHandler exceptionHandler;

    public ConsoleUI(UserFacade userFacade, CommandParser commandParser, ExceptionHandler exceptionHandler) {
        this.userFacade = userFacade;
        this.commandParser = commandParser;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(">>>");
            String input = this.getUserInput();
            try {
                ParsedCommand parsedCommand = commandParser.parse(input);
                if (parsedCommand.getOperation() == Command.EXIT) {
                    System.out.println("Exiting application...");
                    return;
                }
                commandHandler.get(parsedCommand.getOperation()).accept(parsedCommand);
            }
            catch(ParseException parseException) {
                exceptionHandler.handleParseException(parseException);
            }
            catch(ApplicationException applicationException) {
                exceptionHandler.handleApplicationException(applicationException);
            }
        }
    }

    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void addUser(ParsedCommand parsedCommand) {
        UserCreateRequest userCreateRequest = (UserCreateRequest) parsedCommand.getRequest();
        UserDto user = userFacade.addUser(userCreateRequest);

        System.out.println("User created successfully!");
        System.out.println(user);
    }

    private void printUsers(ParsedCommand parsedCommand) {
        List<UserDto> users = userFacade.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("There are no users in the application");
            return;
        }
        System.out.println("List of all the users in the application:");
        users.forEach(System.out::println);
    }

    private void deleteUser(ParsedCommand parsedCommand) {
        UserDeleteRequest userDeleteRequest = (UserDeleteRequest) parsedCommand.getRequest();
        userFacade.deleteUser(userDeleteRequest);
        System.out.printf("User with uid %s deleted successfully!%n", userDeleteRequest.getUserUid());
    }

    private void addFriendship(ParsedCommand parsedCommand) {
        FriendshipRequest request = (FriendshipRequest) parsedCommand.getRequest();
        FriendshipDto friendshipDto = userFacade.addFriendship(request);
        System.out.println("Friendship added successfully!");
        System.out.println(friendshipDto);
    }

    private void printFriendships(ParsedCommand parsedCommand) {
        List<FriendshipDto> friendships = userFacade.getAllFriendships();
        if (friendships.isEmpty()) {
            System.out.println("There are no friendship relations in the application");
            return;
        }
        System.out.println("List of all the friendships in the application:");
        friendships.forEach(System.out::println);
    }

    private void deleteFriendship(ParsedCommand parsedCommand) {
        FriendshipRequest request = (FriendshipRequest) parsedCommand.getRequest();
        userFacade.deleteFriendship(request);
        System.out.println("Friendship deleted successfully!");
    }

    private void printFriendshipComponents(ParsedCommand parsedCommand) {
        List<List<UserDto>> components = userFacade.getFriendshipConnectedComponents();
        if (components.isEmpty()) {
            System.out.println("There are no friendship relations in the application");
            return;
        }
        components.forEach(System.out::println);
    }
    private void printLongestComponent(ParsedCommand parsedCommand){
        List<UserDto> component = userFacade.getLongestComponent();
        if (component.isEmpty()) {
            System.out.println("There are no friendship relations in the application");
            return;
        }
        component.forEach(System.out::println);
    }

    private void help(ParsedCommand parsedCommand) {
        Arrays.stream(Command.values())
                .forEach(command -> System.out.printf("%s:   %s%n", command, command.getArgsDescription()));
    }
}
