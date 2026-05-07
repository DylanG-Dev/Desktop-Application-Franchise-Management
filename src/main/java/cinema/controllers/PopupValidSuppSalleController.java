package cinema.controllers;

import cinema.BO.Salle;
import cinema.DAO.SalleDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static cinema.controllers.Navigation.getParam;

public class PopupValidSuppSalleController extends MenuController implements Initializable {

    @FXML
    private Button ButtonOkOnAction, ButtonRetourOnAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // Méthode qui permet de supprimer la 'salle' si appuie sur le bouton 'OK'
    public void ButtonOkOnAction(ActionEvent actionEvent) {
        Salle salle = getParam("salle");

        SalleDAO salleDAO = new SalleDAO();
        salleDAO.delete(salle);

        Stage stage = (Stage) ButtonOkOnAction.getScene().getWindow();
        stage.close();

        Navigation.showPopup("/cinema/views/popup_message_salle_suppr.fxml", "Validation suppression salle");
    }

    // Méthode qui permet de ne pas valider la suppression
    public void ButtonRetourOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonRetourOnAction.getScene().getWindow();
        stage.close();
    }
}
