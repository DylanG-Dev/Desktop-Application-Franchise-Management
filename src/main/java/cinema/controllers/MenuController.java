package cinema.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    protected MenuItem bListeFranchise, bAjouterFranchise,
            bListeCinema, bAjouterCinema, bQuitter,
            bAccueil, bListeSalle, bAjouterSalle;

    private Stage getStage(ActionEvent event) {
        return (Stage) ((MenuItem) event.getSource())
                .getParentPopup()
                .getOwnerWindow();
    }

    protected String nameUti;

    @FXML
    public void bQuitterClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void bAccueilClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_accueil.fxml", getStage(event));
    }

    @FXML
    public void bListFranchiseClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_liste_franchise.fxml", getStage(event));
    }

    @FXML
    public void bAjouterFranchiseClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_ajout_franchise.fxml", getStage(event));

    }

    @FXML
    public void bListeCinemaClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_liste_cinema.fxml", getStage(event));
    }

    @FXML
    public void bAjouterCinemaClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_ajout_cinema.fxml", getStage(event));
    }

    @FXML
    public void bListeSalleClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_liste_salle.fxml", getStage(event));
    }

    @FXML
    public void bAjouterSalleClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_ajout_salle.fxml", getStage(event));
    }
}
