package com.example.socialnetwork_gui.presentation.InterfaceGUI;

import com.example.socialnetwork_gui.exception.UserAlert;
import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.MessageDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.FriendshipRequest;
import com.example.socialnetwork_gui.request.FriendshipRequestGUI;
import com.example.socialnetwork_gui.service.UserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ConversationsController {

    public Label pageNumberLabel;
    ObservableList<UserDto> model = FXCollections.observableArrayList();

    UserFacade facade;
    UserDto selected;

    int pageNumber;
    int pageSize;

    UserDto selected2;
    @FXML
    TableView<UserDto> tableView;
    @FXML
    TableColumn<UserDto, UUID> tableColumnUserUUID;
    @FXML
    TableColumn<UserDto, String> tableColumnUsername;
    @FXML
    TableColumn<UserDto, String> tableColumnEmail;

    @FXML
    private TextField friendCountTextField;

    @FXML
    public void initialize() {
        tableColumnUserUUID.setCellValueFactory(new PropertyValueFactory<>("uid"));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<UserDto> users = facade.getFriendsOnPage(pageNumber,pageSize,selected.getUid());
        List<UserDto> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(usersList);
        pageNumberLabel.setText("Page : " + pageNumber);
    }
    public void setFacade(UserFacade facade,UserDto selected) {
        this.facade = facade;
        this.selected = selected;
        initModel();
    }

    public void initializeNumbers(){
        pageSize = 5;
        pageNumber = 1;
    }
    public void viewChat(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/socialnetwork_gui/views/chat.fxml"));
            VBox root = loader.load();
            UserDto selectedUser = (UserDto) tableView.getSelectionModel().getSelectedItem();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Chat");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            // Get the controller instance
            ChatController chatController = loader.getController();
            if(selectedUser!=null){
                chatController.setFacade(facade,selected,selectedUser);
                List<MessageDto> chatMessages = facade.getMessagesBetweenUsers(selectedUser.getUid(),selected.getUid());
                List<MessageDto> filteredChatMessages = new ArrayList<>();
                for (MessageDto message : chatMessages) {
                    if (message.getReply() != null) {
                        String newMessage = " [Reply to the message " + "'" + message.getReply().getMessage().toString() + "'" + "  from " + message.getFrom().getUsername() + "] " + " -> " + message.getMessage().toString();

                        // Setăm noul mesaj în locul celui existent
                        message.setMessage(newMessage);
                    }

                    // Adăugăm mesajul în lista finală
                    filteredChatMessages.add(message);
                }

                // Set chat messages in the ChatController
                chatController.setChatMessages(FXCollections.observableArrayList(chatMessages));
                dialogStage.show();
            }
            else{
                UserAlert.showErrorMessage(null, "You haven't selected any user!");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFriends(ActionEvent actionEvent) {
        pageSize = Integer.parseInt(friendCountTextField.getText());
        initModel();
    }

    public void nextPage(ActionEvent actionEvent) {
        pageNumber = pageNumber + 1;
        initModel();
    }
}