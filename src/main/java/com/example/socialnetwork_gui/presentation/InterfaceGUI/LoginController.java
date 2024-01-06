package com.example.socialnetwork_gui.presentation.InterfaceGUI;

import com.example.socialnetwork_gui.exception.UserAlert;
import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class LoginController {
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;

    UserFacade facade;


    public void handleLogin(ActionEvent actionEvent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/useronly-view.fxml"));


            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            UserDto wantedUser = facade.getUserbyUsernameAndPassword(usernameField.getText(),passwordField.getText());
            if (wantedUser != null){
                UserOnlyController userOnlyController = loader.getController();
                userOnlyController.setFacade(facade,wantedUser);
                userOnlyController.initialize(wantedUser.getUsername());

                dialogStage.show();
            }
            else
            {
                UserAlert.showErrorMessage(null, "Wrong username or password! ");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFacade(UserFacade facade) {
        this.facade = facade;
    }
}
