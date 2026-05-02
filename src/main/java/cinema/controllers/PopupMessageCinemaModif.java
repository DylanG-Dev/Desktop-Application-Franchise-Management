package cinema.controllers;

import cinema.BO.Cinema;
import cinema.DAO.CinemaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class PopupMessageCinemaModif {

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
