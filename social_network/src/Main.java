import exception.ExceptionHandlerImpl;
import mapper.FriendshipMapperImpl;
import mapper.UserMapperImpl;
import persistance.repository.FriendshipRepository;
import persistance.repository.UserRepository;
import presentation.CommandParserImpl;
import persistance.repository.FriendshipInMemoryRepository;
import persistance.repository.UserInMemoryRepository;
import utils.GraphUtils;
import validator.UserValidatorImpl;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        UserInMemoryRepository userInMemoryRepository = new UserInMemoryRepository();
        FriendshipInMemoryRepository friendShipInMemoryRepository = new FriendshipInMemoryRepository();
        userInMemoryRepository.addObserver(friendShipInMemoryRepository);
        Application application = new Application(
                userInMemoryRepository,
                friendShipInMemoryRepository,
                new UserMapperImpl(),
                new CommandParserImpl(),
                new ExceptionHandlerImpl(),
                new FriendshipMapperImpl()
        );
        application.run();
    }
}