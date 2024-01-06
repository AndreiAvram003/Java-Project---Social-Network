package com.example.socialnetwork_gui.presentation.InterfaceGUI;


import com.example.socialnetwork_gui.exception.UserAlert;
import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.mapper.UserMapper;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.UserDeleteRequest;
import com.example.socialnetwork_gui.service.UserService;
import com.example.socialnetwork_gui.service.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController {
    UserFacade facade;

    UserDto userDto;
    ObservableList<UserDto> model = FXCollections.observableArrayList();
    UserMapper userMapper;

    UserService service;


    @FXML
    TableView<UserDto> tableView;
    @FXML
    TableColumn<UserDto, UUID> tableColumnUserUUID;
    @FXML
    TableColumn<UserDto, String> tableColumnUsername;
    @FXML
    TableColumn<UserDto, String> tableColumnEmail;

    public void setFacade(UserFacade facade) {
        this.facade = facade;
        //service.addObserver(this);
        initModel();
    }


    @FXML
    public void initialize() {
        tableColumnUserUUID.setCellValueFactory(new PropertyValueFactory<>("uid"));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<UserDto> users = facade.getAllUsers();
        List<UserDto> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(usersList);
    }

    public void handleDeleteUser(ActionEvent actionEvent) {
        UserDto selected = tableView.getSelectionModel().getSelectedItem();
        UUID userUid = selected.getUid();
        UserDeleteRequest userDeleteRequest = new UserDeleteRequest(userUid);
        if (selected != null) {
            UserDto deleted = facade.deleteUser(userDeleteRequest);
            if (null != deleted)
                UserAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "The user has been successfully deleted!");
        } else UserAlert.showErrorMessage(null, "You haven't selected any user!");
    }


//    @Override
//    public void update(MessageTaskChangeEvent messageTaskChangeEvent) {
//
//        initModel();
//    }

    @FXML
    public void handleUpdateUser(ActionEvent ev) {
        UserDto selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showUserEditDialogUpdate(selected);
        } else
            UserAlert.showErrorMessage(null, "You haven't selected any user!");
    }

    @FXML
    public void handleAddUser(ActionEvent ev) {
        showUserEditDialog(null);
    }


    public void showUserEditDialog(User u) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/edituser-view.fxml"));


            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Save user");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditUserController editUserController = loader.getController();
            editUserController.setFacade(facade, dialogStage, u);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUserEditDialogUpdate(UserDto ud) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/edituserdto-view.fxml"));


            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Save user");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditUserController editUserController = loader.getController();
            editUserController.setFacadeforDto(facade, dialogStage, ud);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleRefresh(ActionEvent actionEvent) {
        initModel();
    }

    public void showFriendships(ActionEvent actionEvent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/friendship-view.fxml"));
            VBox root = loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Friendships");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            FriendshipController friendshipController = loader.getController();
            friendshipController.setFacade(facade);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userOptions(ActionEvent actionEvent) {
        try {
            UserDto selected = (UserDto) tableView.getSelectionModel().getSelectedItem();
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/useroptions-view.fxml"));
            VBox root = loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("User Options");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            UserOptionsController userOptionsController = loader.getController();
            if(selected!=null){
            userOptionsController.setFacade(facade,selected);
            dialogStage.show();}
            else{
                UserAlert.showErrorMessage(null, "You haven't selected any user!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userConversations(ActionEvent actionEvent) {
        try {
            UserDto selectedUser = (UserDto) tableView.getSelectionModel().getSelectedItem();
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/conversations.fxml"));
            VBox root = loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Conversations");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            ConversationsController conversationsController = loader.getController();
            if(selectedUser!=null){
                conversationsController.setFacade(facade,selectedUser);
                dialogStage.show();}
            else{
                UserAlert.showErrorMessage(null, "You haven't selected any user!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void openLoginWindow(ActionEvent event) {
            try {
                // Încarcăm fișierul FXML pentru fereastra de login
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/login-view.fxml"));
                AnchorPane root = loader.load();

                // Creăm fereastra de login
                Stage loginStage = new Stage();
                loginStage.setTitle("Login");
                loginStage.initModality(Modality.APPLICATION_MODAL);
                loginStage.setScene(new Scene(root));

                // Afișăm fereastra de login
                loginStage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }