package com.sumrest.controller;

import com.sumrest.controller.screen.ScreenController;
import com.sumrest.model.session.SessionModel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

/**
 * @author alexfdb
 * @version 1.0.0
 */
public class LevelController extends ScreenController {

    @FXML
    private Text textUser;
    @FXML
    private Text textAnswers;
    @FXML
    private Text textHits;
    @FXML
    private ComboBox<String> comboBoxLevel;
    @FXML
    private Button buttonPlay;
    @FXML
    private Button buttonProfile;
    @FXML
    private Button buttonStart;

    @FXML
    public void initialize() {
        if (SessionModel.getUser() != null) {
            textUser.setText(SessionModel.getUser().getName());
            textAnswers.setText("Respuestas realizadas: " + SessionModel.getUser().getAnswers());
            textHits.setText("Respuestas correctas: " + SessionModel.getUser().getHits());
        } else {
            textUser.setText("Usuario no identificado");
        }
        comboBoxLevel.getItems().addAll("Easy", "Medium", "Hard");
        comboBoxLevel.setValue("Medium");
    }

    /**
     * Selecciona la dificultad.
     */
    @FXML
    public void comboBoxLevelClick() {
        SessionModel.setLevel(comboBoxLevel.getValue());
        textAnswers.setText("Respuestas realizadas: " + SessionModel.getUser().getAnswers());
        textHits.setText("Respuestas correctas: " + SessionModel.getUser().getHits());
    }

    /**
     * Cambia a la pantalla jugar.
     */
    @FXML
    public void buttonPlayClick() {
        playScreen(buttonPlay);
    }

    /**
     * Cambia a la pantalla perfil.
     */
    @FXML
    public void buttonProfileClick() {
        profileScreen(buttonProfile);
    }

    /**
     * Cambia a la pantalla iniciar.
     */
    @FXML
    public void buttonStartClick() {
        startScreen(buttonStart);
    }

}