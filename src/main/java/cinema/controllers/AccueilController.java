package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AccueilController extends MenuController implements Initializable {

    @FXML
    private Label bienvenue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    // nameUti qui renvoie l'adresse email de l'utilisateur connecté actuellement
    public void setBienvenue() {
        bienvenue.setText("BIENVENUE " + nameUti.toUpperCase());
    }

}
