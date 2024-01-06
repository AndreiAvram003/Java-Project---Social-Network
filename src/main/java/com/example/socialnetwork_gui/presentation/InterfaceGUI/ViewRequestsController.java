package com.example.socialnetwork_gui.presentation.InterfaceGUI;
import com.example.socialnetwork_gui.exception.UserAlert;
import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendshipDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.FriendshipRequest;
import com.example.socialnetwork_gui.request.FriendshipRequestGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javafx.event.ActionEvent;

public class ViewRequestsController {

    ObservableList<UserDto> model = FXCollections.observableArrayList();

    UserFacade facade;
    UserDto selected;

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

    private void initModel() {
        Iterable<UserDto> users = facade.getRequests(selected.getUid());
        List<UserDto> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(usersList);
    }
    public void acceptRequest(ActionEvent actionEvent) {
        UserDto selectedUser = (UserDto) tableView.getSelectionModel().getSelectedItem();
        FriendshipRequest friendshipRequest = new FriendshipRequest(selectedUser.getUid(),selected.getUid(),"accepted");
        if(selectedUser!=null) {
            this.facade.changeStatus(friendshipRequest);
            UserAlert.showMessage(null, Alert.AlertType.INFORMATION, "Accept Request", "The request has been accepted!");

        }
        else{
        UserAlert.showErrorMessage(null, "You haven't selected any user!");}
    }

    public void declineRequest(ActionEvent actionEvent) {
        UserDto selectedUser = (UserDto) tableView.getSelectionModel().getSelectedItem();
        FriendshipRequest friendshipRequest = new FriendshipRequest(selectedUser.getUid(),selected.getUid(),"accepted");
        if(selectedUser!=null) {
            this.facade.deleteFriendship(friendshipRequest);
            UserAlert.showMessage(null, Alert.AlertType.INFORMATION, "Decline Request", "The request has been declined!");
        }
        else{
        UserAlert.showErrorMessage(null, "You haven't selected any user!");}
    }
    public void setFacade(UserFacade facade,UserDto selected) {
        this.facade = facade;
        this.selected = selected;
        initModel();
    }

    public void refresh(ActionEvent actionEvent) {
        initModel();
    }
}
