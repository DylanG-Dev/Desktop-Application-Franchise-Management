package cinema.controllers;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static cinema.controllers.Navigation.getParam;

public class PopupValidSuppCinemaController extends MenuController implements Initializable {

    @FXML
    private Text tMessage;

    @FXML
    private Button ButtonOkOnAction, ButtonRetourOnAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Cinema cinema = getParam("cinema");
    }

    public void ButtonOkOnAction(ActionEvent actionEvent) {
        try {
            Cinema cinema = getParam("cinema");
            CinemaDAO cinemaDAO = new CinemaDAO();

            System.out.println(cinema);
            cinemaDAO.delete(cinema);

            Stage stage = (Stage) ButtonOkOnAction.getScene().getWindow();
            stage.close();

            Navigation.showPopup("/cinema/views/popup_message_cinema_suppr.fxml", "Validation suppression cinéma");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ButtonRetourOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonRetourOnAction.getScene().getWindow();
        stage.close();
    }
}
