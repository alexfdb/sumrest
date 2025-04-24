package com.sumrest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.sumrest.controller.screen.ScreenController;
import com.sumrest.model.User;
import com.sumrest.model.UserModel;
import com.sumrest.model.session.SessionModel;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author alexfdb
 * @version 1.0.0
 */
public class PlayController extends ScreenController {

    @FXML
    private Text textPoint;
    @FXML
    private Text textTime;
    @FXML
    private Text textQuestion;
    @FXML
    private Button buttonAnswer1;
    @FXML
    private Button buttonAnswer2;
    @FXML
    private Button buttonAnswer3;
    @FXML
    private Button buttonAnswer4;
    @FXML
    private Button buttonReturn;

    private Integer answers;
    private Integer hits;
    private Timeline timeline;
    private int timeRemaining;
    private int number1;
    private int number2;
    private int correctAnswer;

    @FXML
    public void initialize() {
        this.answers = 0;
        this.hits = 0;
        generateNewQuestion();

        switch (SessionModel.getLevel().toLowerCase()) {
            case "easy":
                timeRemaining = 60;
                break;
            case "medium":
                timeRemaining = 40;
                break;
            case "hard":
                timeRemaining = 20;
                break;
            default:
                timeRemaining = 40;
                break;
        }

        textTime.setText("Tiempo: " + timeRemaining + "s");

        timeline = new Timeline(new javafx.animation.KeyFrame(
                Duration.seconds(1),
                event -> {
                    timeRemaining--;
                    textTime.setText("Tiempo: " + timeRemaining + "s");

                    if (timeRemaining <= 0) {
                        timeline.stop();
                        endGame(false);
                    }
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Genera una nueva operación matemática y opciones de respuesta.
     */
    private void generateNewQuestion() {
        Random random = new Random();
        number1 = random.nextInt(90) + 10;
        number2 = random.nextInt(90) + 10;
        boolean isAddition = random.nextBoolean();

        if (isAddition) {
            correctAnswer = number1 + number2;
            textQuestion.setText(number1 + " + " + number2 + " = ?");
        } else {
            correctAnswer = number1 - number2;
            textQuestion.setText(number1 + " - " + number2 + " = ?");
        }

        List<Integer> answers = new ArrayList<>();
        answers.add(correctAnswer);
        while (answers.size() < 4) {
            int wrongAnswer = random.nextInt(180) - 89;
            if (!answers.contains(wrongAnswer)) {
                answers.add(wrongAnswer);
            }
        }
        Collections.shuffle(answers);

        buttonAnswer1.setText(String.valueOf(answers.get(0)));
        buttonAnswer2.setText(String.valueOf(answers.get(1)));
        buttonAnswer3.setText(String.valueOf(answers.get(2)));
        buttonAnswer4.setText(String.valueOf(answers.get(3)));
    }

    /**
     * Verifica si la respuesta seleccionada es correcta.
     * @param selectedAnswer Índice del botón seleccionado (1-4).
     */
    private void checkAnswer(int selectedAnswer) {
        int answer = Integer.parseInt(((Button) getButtonByIndex(selectedAnswer)).getText());
        answers++;
        if (answer == correctAnswer) {
            hits++;
            textPoint.setText("Respuestas: " + hits + "/" + answers);
            generateNewQuestion();
        } else {
            textPoint.setText("Respuestas: " + hits + "/" + answers);
        }

        if (answers >= 10) {
            timeline.stop();
            endGame(true);
        }
    }

    /**
     * Obtiene el botón correspondiente al índice dado.
     * @param index Índice del botón (1-4).
     * @return El botón correspondiente.
     */
    private Button getButtonByIndex(int index) {
        switch (index) {
            case 1:
                return buttonAnswer1;
            case 2:
                return buttonAnswer2;
            case 3:
                return buttonAnswer3;
            case 4:
                return buttonAnswer4;
            default:
                throw new IllegalArgumentException("Índice de botón inválido");
        }
    }

    /**
     * Finaliza el juego.
     * @param isWin Indica si el jugador ganó o perdió.
     */
    private void endGame(boolean isWin) {
        timeline.stop();
    
        String message;
        if (isWin) {
            message = "¡Felicidades! \nAciertos: " + hits + "\nRespuestas dadas: " + answers;
        } else {
            message = "Se acabó el tiempo. Intenta de nuevo.\nAciertos: " + hits + "\nRespuestas dadas: " + answers;
        }
    
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
            alert.setTitle(isWin ? "¡A tiempo!" : "¡Derrota!");
            alert.setHeaderText(null);
            alert.showAndWait();
    
            UserModel userModel = new UserModel();
            User currentUser = SessionModel.getUser();
            if (currentUser != null) {
                int updatedAnswers = currentUser.getAnswers() + answers;
                int updatedHits = currentUser.getHits() + hits;
                User updatedUser = new User(currentUser.getName(), currentUser.getPassword(), updatedAnswers, updatedHits);
                userModel.updateUser(currentUser, updatedUser);
                SessionModel.startSesion(updatedUser);
            }
    
            levelScreen(buttonReturn);
        });
    }

    @FXML
    private void buttonAnswer1Click() {
        checkAnswer(1);
    }

    @FXML
    private void buttonAnswer2Click() {
        checkAnswer(2);
    }

    @FXML
    private void buttonAnswer3Click() {
        checkAnswer(3);
    }

    @FXML
    private void buttonAnswer4Click() {
        checkAnswer(4);
    }

    @FXML
    private void buttonReturnClick() {
        levelScreen(buttonReturn);
    }
}