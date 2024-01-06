package com.example.socialnetwork_gui;

import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.facade.UserFacadeImpl;
import com.example.socialnetwork_gui.mapper.*;
import com.example.socialnetwork_gui.persistance.repository.*;
import com.example.socialnetwork_gui.presentation.InterfaceGUI.LoginController;
import com.example.socialnetwork_gui.presentation.InterfaceGUI.UserController;
import com.example.socialnetwork_gui.service.UserService;
import com.example.socialnetwork_gui.service.UserServiceImpl;
import com.example.socialnetwork_gui.validator.FriendshipValidator;
import com.example.socialnetwork_gui.validator.FriendshipValidatorImpl;
import com.example.socialnetwork_gui.validator.UserValidator;
import com.example.socialnetwork_gui.validator.UserValidatorImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class HelloApplication extends Application {
    private UserFacade facadeUt;

    private UserService service;


    @Override
        public void start(Stage stage) throws IOException {
            UserDBRepository userDBRepository = new UserDBRepository();
            FriendshipDBRepository friendshipDBRepository = new FriendshipDBRepository();
            UserMapper userMapper = new UserMapperImpl();
            FriendshipMapper friendshipMapper = new FriendshipMapperImpl();
        MessageMapper messageMapper = new MessageMapperImpl();
        MessageDBRepository messageDBRepository = new MessageDBRepository();
        /*My_Application application = new My_Application(
                userDBRepository,
                friendshipDBRepository,
                new UserMapperImpl(),
                new CommandParserImpl(),
                new ExceptionHandlerImpl(),
                new FriendshipMapperImpl()
        );*/
        UserValidator userValidator = new UserValidatorImpl(userDBRepository);
        FriendshipValidator friendshipValidator = new FriendshipValidatorImpl(friendshipDBRepository);
        facadeUt = new UserFacadeImpl(
                new UserServiceImpl(userDBRepository, friendshipDBRepository, userValidator, friendshipValidator,messageDBRepository),
                userMapper,
                friendshipMapper,
                messageMapper);
        service = new UserServiceImpl(userDBRepository, friendshipDBRepository, userValidator, friendshipValidator,messageDBRepository);
        initView(stage);

        stage.setTitle("SocialNetwork");
        stage.show();
    }

    private void initView(Stage primaryStage) throws IOException {

        FXMLLoader userLoader = new FXMLLoader();
        userLoader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/login-view.fxml"));
        primaryStage.setScene(new Scene(userLoader.load()));

        //UserController userController = userLoader.getController();
       // userController.setFacade(facadeUt,service);
        LoginController loginController = userLoader.getController();
        loginController.setFacade(facadeUt);
    }

    public static void main(String[] args) {
        //UserInMemoryRepository userInMemoryRepository = new UserInMemoryRepository();
        /*UserDBRepository userDBRepository = new UserDBRepository();
        FriendshipDBRepository friendshipDBRepository = new FriendshipDBRepository();
        My_Application application = new My_Application(
                userDBRepository,
                friendshipDBRepository,
                new UserMapperImpl(),
                new CommandParserImpl(),
                new ExceptionHandlerImpl(),
                new FriendshipMapperImpl()
        );
        application.run();*/
        launch();
    }
}