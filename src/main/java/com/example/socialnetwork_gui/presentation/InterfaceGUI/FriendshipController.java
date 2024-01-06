package com.example.socialnetwork_gui.presentation.InterfaceGUI;

import com.example.socialnetwork_gui.exception.UserAlert;
import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.Observer;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendshipDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.FriendshipRequest;
import com.example.socialnetwork_gui.request.UserDeleteRequest;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class  FriendshipController implements Observer {

    @FXML
    TableView<FriendshipDto> tableView;

    @FXML
    TableColumn<FriendshipDto, String> tableColumnFirstUser;
    @FXML
    TableColumn<FriendshipDto, String> tableColumnSecondUser;

    @FXML
    TableColumn<FriendshipDto, LocalDateTime> tableColumnCreatedAt;

    ObservableList<FriendshipDto> model = FXCollections.observableArrayList();


    private UserFacade facade;

    public void setFacade(UserFacade facade) {
        this.facade = facade;
        //service.addObserver(this);
        initModel();
    }


    @FXML
    public void initialize() {
        tableColumnFirstUser.setCellValueFactory(cellData -> {
            UserDto user =cellData.getValue().getFirstUser();
            return new SimpleObjectProperty<>(user.getUsername());
        });
        tableColumnSecondUser.setCellValueFactory(cellData -> {
            UserDto user =cellData.getValue().getSecondUser();
            return new SimpleObjectProperty<>(user.getUsername());
        });
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        tableView.setItems(model);
    }

    private void initModel() {
        List<FriendshipDto> friendshipsList = facade.getAllFriendships();
        model.setAll(friendshipsList);
    }

    public void handleDeleteFriendship(ActionEvent actionEvent) {
        FriendshipDto selected = (FriendshipDto) tableView.getSelectionModel().getSelectedItem();
        UUID userUid1 = selected.getFirstUser().getUid();
        UUID userUid2 = selected.getSecondUser().getUid();
        FriendshipRequest friendshipRequest = new FriendshipRequest(userUid1,userUid2,"accepted");
        if (selected != null) {
            Optional<Friendship> deleted = facade.deleteFriendship(friendshipRequest);
            if (null != deleted)
                UserAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "The friendships has been deleted successfully!");
        } else UserAlert.showErrorMessage(null, "You haven't selected any friendship!");
    }

    public void handleRefresh(ActionEvent actionEvent) {
        initModel();
    }

    @Override
    public void updateObserver(Object data) {
        initModel();
    }
}
