package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ResetPasswordController implements Initializable {
    @FXML
    private Button ButtonEnvoyer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ButtonEnvoyerOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonEnvoyer.getScene().getWindow();
        stage.close();
    }
}
