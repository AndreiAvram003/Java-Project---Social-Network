package com.example.socialnetwork_gui.presentation.InterfaceGUI;

import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.persistance.model.dtos.MessageDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.MessageRequest;
import com.example.socialnetwork_gui.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;

public class ChatController {
    UserDto selected1;
    UserDto selected2;


    UserFacade facade;

    UserService service;


    @FXML
    private ListView<MessageDto> chatListView;

    @FXML
    private TextArea replyTextArea;

    private ObservableList<MessageDto> chatMessages = FXCollections.observableArrayList();


    public void initialize() {
        chatListView.setItems(chatMessages);
    }

    public void setChatMessages(ObservableList<MessageDto> messages) {
        chatMessages.setAll(messages);
    }

    public void setFacade(UserFacade facade,UserDto selected1,UserDto selected2) {
        this.facade = facade;
        this.selected1 = selected1;
        this.selected2 = selected2;
        initialize();
    }

   @FXML
    private void sendButtonClicked(ActionEvent actionEvent) {
        String replyText = replyTextArea.getText();
        MessageRequest messageRequest = new MessageRequest(selected1.getUid(),selected2.getUid(),replyText,null);
        MessageDto sent = facade.addMessage(messageRequest);
        sendMessage(sent);
        replyTextArea.clear();
    }

    private void sendMessage(MessageDto message) {
        chatMessages.add(message);
    }

    public void replyMethod(ActionEvent actionEvent) {
        MessageDto selectedMessage =chatListView.getSelectionModel().getSelectedItem();
        String replyText = replyTextArea.getText().trim();
        String reply = " [Reply to the message " + "'" + selectedMessage.getMessage().toString() +"'" + "  from " + selectedMessage.getFrom().getUsername()  + "] " + " -> ";
        String newMessage = reply + replyText;
        sendMessage(new MessageDto(selectedMessage.getUid(),selected1,selected2,newMessage,LocalDateTime.now(),selectedMessage.getReply()));
        MessageRequest messageRequest = new MessageRequest(selected1.getUid(),selected2.getUid(),replyText,selectedMessage.getUid());
        facade.addMessage(messageRequest);
        replyTextArea.clear();
    }
}