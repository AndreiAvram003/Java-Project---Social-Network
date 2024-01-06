package com.example.socialnetwork_gui.presentation.InterfaceGUI;

import com.example.socialnetwork_gui.exception.UserAlert;
import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.FriendshipRequest;
import com.example.socialnetwork_gui.request.FriendshipRequestGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class UserOptionsController {

    public Label pageNumberLabel;
    public TextField nonFriendCountTextField;
    ObservableList<UserDto> model = FXCollections.observableArrayList();

    UserFacade facade;
    UserDto selected;

    int pageNumber;
    int pageSize;

    @FXML
    TableView<UserDto> tableView;
    @FXML
    TableColumn<UserDto, UUID> tableColumnUserUUID;
    @FXML
    TableColumn<UserDto, String> tableColumnUsername;
    @FXML
    TableColumn<UserDto, String> tableColumnEmail;

    @FXML
    public void initialize() {
        tableColumnUserUUID.setCellValueFactory(new PropertyValueFactory<>("uid"));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.setItems(model);
    }

    public void initializeNumbers(){
        pageSize = 5;
        pageNumber = 1;
    }

    private void initModel() {
        Iterable<UserDto> users = facade.getNonFriendsOnPage(pageNumber,pageSize,selected.getUid());
        List<UserDto> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(usersList);
        pageNumberLabel.setText("Page : " + pageNumber);
    }

    public void sendRequest(ActionEvent actionEvent) {
        UserDto selectedUser = (UserDto) tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
        FriendshipRequest friendshipRequest = new FriendshipRequest(selected.getUid(), selectedUser.getUid(), "pending");
            this.facade.addFriendship(friendshipRequest);
            UserAlert.showMessage(null, Alert.AlertType.INFORMATION, "Send Request", "The request was sent successfully");
        }
        else{
            if(selectedUser==null){
            UserAlert.showErrorMessage(null, "You haven't selected any user!");}}
    }

    public void viewRequests(ActionEvent actionEvent) {
       try{ // create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/view-requests.fxml"));
        VBox root = loader.load();
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Requests");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        ViewRequestsController viewRequestsController = loader.getController();
        viewRequestsController.setFacade(facade,selected);
        dialogStage.show();

    } catch (
    IOException e) {
        e.printStackTrace();
    }
    }

    public void setFacade(UserFacade facade,UserDto selected) {
        this.facade = facade;
        this.selected = selected;
        initModel();
    }

    public void refresh(ActionEvent actionEvent) {
        initModel();
    }

    public void nextPage(ActionEvent actionEvent) {
        pageNumber = pageNumber + 1;
        initModel();
    }

    public void updateFriends(ActionEvent actionEvent) {
        pageSize = Integer.parseInt(nonFriendCountTextField.getText());
        initModel();
    }
}
