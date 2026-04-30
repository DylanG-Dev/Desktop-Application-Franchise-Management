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
        getParam("salle");
    }

    public void ButtonOkOnAction(ActionEvent actionEvent) {
        Salle salle = getParam("salle");

        SalleDAO salleDAO = new SalleDAO();
        salleDAO.delete(salle);

        Stage stage = (Stage) ButtonOkOnAction.getScene().getWindow();
        stage.close();
    }

    public void ButtonRetourOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonRetourOnAction.getScene().getWindow();
        stage.close();
    }
}
