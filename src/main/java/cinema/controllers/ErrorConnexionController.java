package cinema.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorConnexionController {
    @FXML
    private Button ButtonOk;

    // Méthode qui permet de fermer la popup lors du clique sur le bouton 'ButtonOk'
    public void ButtonOkOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonOk.getScene().getWindow();
        stage.close();
    }
}