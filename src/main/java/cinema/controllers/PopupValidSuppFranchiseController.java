package cinema.controllers;

import cinema.BO.Franchise;
import cinema.BO.Salle;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.SalleDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static cinema.controllers.Navigation.getParam;

public class PopupValidSuppFranchiseController extends MenuController implements Initializable {

    @FXML
    private Button ButtonOkOnAction, ButtonRetourOnAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getParam("franchise");
    }

    public void ButtonOkOnAction(ActionEvent actionEvent) {
        try {
            Franchise franchise = getParam("franchise");

            FranchiseDAO franchiseDAO = new FranchiseDAO();
            franchiseDAO.delete(franchise);

            Stage stage = (Stage) ButtonOkOnAction.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ButtonRetourOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonRetourOnAction.getScene().getWindow();
        stage.close();
    }
}
