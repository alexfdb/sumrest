package com.sumrest.controller;

import com.sumrest.controller.screen.ScreenController;
import com.sumrest.model.User;
import com.sumrest.model.UserModel;
import com.sumrest.model.session.SessionModel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author alexfdb
 * @version 1.0.0
 */
public class ProfileController extends ScreenController {

    @FXML
    private Text textUser;
    @FXML
    private TextField textFieldUser;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Text textMessage;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonReturn;
    @FXML
    private Button buttonDelete;

    @FXML
    public void initialize() {
        if (SessionModel.getUser() != null) {
            textUser.setText(SessionModel.getUser().getName());
        } else {
            textUser.setText("Usuario no identificado");
        }
    }

    /**
     * Actualiza los datos del usuario.
     */
    @FXML
    public void buttonUpdateClick() {
        if (!validateCredentials()) {
            textMessage.setText("Credenciales invalidas");
            return;
        }
        UserModel userModel = new UserModel();
        User user = new User(textFieldUser.getText(), passwordFieldPassword.getText(),
                SessionModel.getUser().getAnswers(), SessionModel.getUser().getHits());
        userModel.updateUser(SessionModel.getUser(), user);
        textMessage.setText("Usuario actualizado");
    }

    /**
     * Cierra la sesion del usuario.
     */
    @FXML
    public void buttonCloseClick() {
        SessionModel.endSesion();
        startScreen(buttonClose);
    }

    /**
     * Vuelve a la pantalla de nivel.
     */
    @FXML
    public void buttonReturnClick() {
        levelScreen(buttonReturn);
    }

    /**
     * Elimina la cuenta del usuario.
     */
    @FXML
    public void buttonDeleteClick() {
        // Crear un Alert de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar tu cuenta?");
        alert.setContentText("Esta acción no se puede deshacer.");

        // Mostrar el Alert y esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si el usuario confirma, eliminar la cuenta
                UserModel userModel = new UserModel();
                userModel.deleteUser(SessionModel.getUser());
                startScreen(buttonDelete); // Regresar a la pantalla inicial
            } else {
                // Si el usuario cancela, no hacer nada
                alert.close();
            }
        });
    }

    /**
     * Valida las credenciales.
     * 
     * @return retorna true si estas son validas.
     */
    private boolean validateCredentials() {
        return (textFieldUser != null && textFieldUser.getText() != null && !textFieldUser.getText().isBlank() &&
                passwordFieldPassword != null && passwordFieldPassword.getText() != null
                && !passwordFieldPassword.getText().isBlank());
    }

}