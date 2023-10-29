import exception.ExceptionHandler;
import facade.UserFacade;
import facade.UserFacadeImpl;
import mapper.FriendshipMapper;
import mapper.UserMapper;
import presentation.CommandParser;
import presentation.ConsoleUI;
import persistance.repository.FriendshipRepository;
import persistance.repository.UserRepository;
import service.UserServiceImpl;
import validator.FriendshipValidator;
import validator.FriendshipValidatorImpl;
import validator.UserValidator;
import validator.UserValidatorImpl;

public class Application {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserMapper userMapper;
    private final CommandParser commandParser;
    private final ExceptionHandler exceptionHandler;
    private final FriendshipMapper friendshipMapper;

    public Application(UserRepository userRepository, FriendshipRepository friendshipRepository, UserMapper userMapper, CommandParser commandParser, ExceptionHandler exceptionHandler, FriendshipMapper friendshipMapper) {
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
