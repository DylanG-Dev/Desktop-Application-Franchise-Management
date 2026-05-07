package cinema.controllers;

import cinema.BO.Cinema;
import cinema.DAO.CinemaDAO;
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

    // Méthode qui permet de supprimer le 'cinéma' si appuie sur le bouton 'OK'
    public void ButtonOkOnAction(ActionEvent actionEvent) {
        Cinema cinema = getParam("cinema");
        CinemaDAO cinemaDAO = new CinemaDAO();

        System.out.println(cinema);
        cinemaDAO.delete(cinema);

        Stage stage = (Stage) ButtonOkOnAction.getScene().getWindow();
        stage.close();

        Navigation.showPopup("/cinema/views/popup_message_cinema_suppr.fxml", "Validation suppression cinéma");

    }

    // Méthode qui permet de ne pas valider la suppression
    public void ButtonRetourOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonRetourOnAction.getScene().getWindow();
        stage.close();
    }
}
