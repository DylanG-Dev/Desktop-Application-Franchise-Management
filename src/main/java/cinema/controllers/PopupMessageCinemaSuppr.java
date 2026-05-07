package cinema.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class PopupMessageCinemaSuppr {

    @FXML
    private Button ButtonOkOnAction;

    public void ButtonOkOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ButtonOkOnAction.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
