package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorConnexionController {
    @FXML
    private Button ButtonOk;

    public void ButtonOkOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonOk.getScene().getWindow();
        stage.close();
    }
}