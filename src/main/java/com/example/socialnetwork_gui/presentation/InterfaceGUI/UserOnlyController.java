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
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserOnlyController {

    @FXML
    private Label helloLabel;

    @FXML
    private VBox mainVBox;

    UserFacade facade;
    UserDto wantedUser;


    public void setFacade(UserFacade facade,UserDto wantedUser) {
        this.facade = facade;
        this.wantedUser = wantedUser;
    }


    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }






    public void handleViewFriends(ActionEvent actionEvent) throws IOException {
        // create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/conversations.fxml"));
        VBox root = loader.load();
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Friends");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        ConversationsController conversationsController = loader.getController();
        conversationsController.initializeNumbers();
        conversationsController.setFacade(facade, wantedUser);
        dialogStage.show();
    }

    public void handleSendRequests(ActionEvent actionEvent) throws IOException {

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
            userOptionsController.initializeNumbers();
            userOptionsController.setFacade(facade,wantedUser);
            dialogStage.show();
    }

    public void initialize(String username) {
            helloLabel.setText("Hello, " + username + "!");
    }
}
