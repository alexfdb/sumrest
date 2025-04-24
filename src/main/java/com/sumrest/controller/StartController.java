package com.sumrest.controller;

import com.sumrest.controller.screen.ScreenController;
import com.sumrest.model.User;
import com.sumrest.model.UserModel;
import com.sumrest.model.session.SessionModel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author alexfdb
 * @version 1.0.0
 */
public class StartController extends ScreenController {

    @FXML
    private TextField textFieldUser;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Text textMessage;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonCreate;

    /**
     * Inicia la sesion del usuario.
     */
    @FXML
    public void buttonStartClick() {
    if (!validateCredentials()) {
        textMessage.setText("Credenciales invalidas");
        return;
    }
    UserModel userModel = new UserModel();
    User user = new User(textFieldUser.getText(), passwordFieldPassword.getText());
    User startUser = userModel.readUser(user);
    if (startUser == null) {
        textMessage.setText("Credenciales incorrectas");
        return;
    }
    textMessage.setText("");
    SessionModel.startSesion(startUser);
    levelScreen(buttonStart);
    }

    /**
     * Cambia a la pantalla crear.
     */
    @FXML
    public void buttonCreateClick() {
        createScreen(buttonCreate);
    }

    /**
     * Valida las credenciales.
     * 
     * @return retorna true si estas son validas.
     */
    private boolean validateCredentials() {
        return (textFieldUser != null && textFieldUser.getText() != null && !textFieldUser.getText().trim().isBlank() &&
                passwordFieldPassword != null && passwordFieldPassword.getText() != null
                && !passwordFieldPassword.getText().trim().isBlank());
    }

}