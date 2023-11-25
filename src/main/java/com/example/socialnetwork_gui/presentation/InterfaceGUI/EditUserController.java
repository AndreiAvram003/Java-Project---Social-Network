package com.example.socialnetwork_gui.presentation.InterfaceGUI;

import com.example.socialnetwork_gui.exception.EntityNotFoundException;
import com.example.socialnetwork_gui.exception.UserAlert;
import com.example.socialnetwork_gui.facade.UserFacade;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.UserCreateRequest;
import com.example.socialnetwork_gui.request.UserUpdateRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.util.UUID;

public class EditUserController {
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldUUID;
    @FXML
    private DatePicker datePickerDate;
    private UserFacade facade;
    Stage dialogStage;
    UserDto userDto;
    User user;

    @FXML
    private void initialize() {
    }

    public void setFacade(UserFacade facade,  Stage stage, User user) {
        this.facade = facade;
        this.dialogStage=stage;
        this.user= user;
        if (null != user) {
            setFields(user);
        }
    }
    public void setFacadeforDto(UserFacade facade,  Stage stage, UserDto ud) {
        this.facade = facade;
        this.dialogStage=stage;
        this.userDto= ud;
        if (null != ud) {
            setFieldsforDto(ud);
        }
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent){
        UserCreateRequest userCreateRequest = new UserCreateRequest(textFieldUsername.getText(),textFieldPassword.getText(),textFieldEmail.getText());
        // TODO
        try {
            UserDto u1= this.facade.addUser(userCreateRequest);
            if (u1==null)
                dialogStage.close();
            UserAlert.showMessage(null, Alert.AlertType.INFORMATION,"Salvare user","Userul a fost salvat");
        } catch (EntityNotFoundException e) {
            UserAlert.showErrorMessage(null,e.getMessage());
        }
        clearFields_user();
        dialogStage.close();
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent)
    {

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(userDto.getUid(),textFieldUsername.getText(),textFieldEmail.getText());
        try {
            UserDto u= this.facade.updateUser(userUpdateRequest);
            if (u==null)
                UserAlert.showMessage(null, Alert.AlertType.INFORMATION,"Modificare user","Userul a fost modificat");
        } catch (EntityNotFoundException e) {
            UserAlert.showErrorMessage(null,e.getMessage());
        }
        clearFields_userdto();
        dialogStage.close();
    }



    private void clearFields_user() {
        textFieldUsername.setText("");
        textFieldPassword.setText("");
        textFieldEmail.setText("");
    }
    private void clearFields_userdto() {
        textFieldUsername.setText("");
        textFieldEmail.setText("");
    }
    private void setFields(User u)
    {
        textFieldUsername.setText(u.getUsername());
        textFieldPassword.setText(u.getPassword());
        textFieldEmail.setText(u.getEmail());
    }
    private void setFieldsforDto(UserDto ud)
    {
        textFieldUsername.setText(ud.getUsername());
        textFieldEmail.setText(ud.getEmail());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
