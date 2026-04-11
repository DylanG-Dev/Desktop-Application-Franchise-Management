package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.DAO.CinemaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifierCinemaController extends MenuController implements Initializable {

    @FXML
    private TextArea taLibCinema;

    private int idCinema;

    @FXML
    private Button bRetour, bEnregistrer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setIdCinema(int idCinema) {
        this.idCinema = idCinema;
    }

    public void setAttributs() {
        CinemaDAO cinemaDAO = new CinemaDAO();
        Cinema cinema = cinemaDAO.find(idCinema);
        taLibCinema.setText(cinema.getDenomination());
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
            Parent root = fxmlLoader.load();

            ListeCinemaController listeCinemaController = fxmlLoader.getController();
            listeCinemaController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Liste franchises");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String lib = taLibCinema.getText();
        if (!lib.trim().isEmpty()) {
            Cinema cinema = new Cinema(idCinema, lib, lib, lib, idCinema);
            CinemaDAO cinemaDAO = new CinemaDAO();
            boolean controle = cinemaDAO.update(cinema);
            if (controle) {
                Stage stageP = (Stage) bRetour.getScene().getWindow();
                stageP.close();
                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(
                            getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
                    Parent root = fxmlLoader.load();

                    ListeCinemaController listeCinemaController = fxmlLoader.getController();
                    listeCinemaController.setName(nameUti);

                    Stage stage = new Stage();
                    stage.setTitle("Liste franchises");
                    stage.setScene(new Scene(root));

                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                // Charger le fichier FXML
                FXMLLoader fxmlLoader = new FXMLLoader(
                        getClass().getResource("/cinema/views/popup_ajout_etu.fxml"));
                Parent root = fxmlLoader.load();

                // Créer une nouvelle fenêtre (Stage)
                Stage stage = new Stage();
                stage.setTitle("Pop-up");
                stage.setScene(new Scene(root));

                // Configurer la fenêtre en tant que modal
                stage.initModality(Modality.APPLICATION_MODAL);

                // Afficher la fenêtre et attendre qu'elle se ferme
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
