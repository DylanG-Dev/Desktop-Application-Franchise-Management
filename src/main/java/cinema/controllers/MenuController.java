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
    private MenuItem bListeFranchise, bAjouterFranchise,
            bListeCinema, bAjouterCinema, bQuitter,
            bAccueil, bListeSalle, bAjouterSalle;

    // Fonction qui permet de récupérer la fenêtre de la page principale
    private Stage getStage(ActionEvent event) {
        return (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
    }

    // Méthode qui ferme l'application
    @FXML
    public void bQuitterClick(ActionEvent event) {
        Platform.exit();
    }

    // Méthode qui permet de se diriger sur la page d'accueil
    @FXML
    public void bAccueilClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_accueil.fxml", getStage(event));
    }

    // Méthode qui permet de se diriger sur la page 'Liste franchise'
    @FXML
    public void bListFranchiseClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_liste_franchise.fxml", getStage(event));
    }

    // Méthode qui permet de se diriger sur la page 'Ajouter une franchise'
    @FXML
    public void bAjouterFranchiseClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_ajout_franchise.fxml", getStage(event));
    }

    // Méthode qui redirige vers la page 'Liste cinémas'
    @FXML
    public void bListeCinemaClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_liste_cinema.fxml", getStage(event));
    }

    // Méthode qui redirige vers la page 'Ajouter un cinéma'
    @FXML
    public void bAjouterCinemaClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_ajout_cinema.fxml", getStage(event));
    }

    // Méthode qui redirige vers la page 'Liste salles'
    @FXML
    public void bListeSalleClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_liste_salle.fxml", getStage(event));
    }

    // Méthode qui redirige vers la page 'Ajouter une salle'
    @FXML
    public void bAjouterSalleClick(ActionEvent event) {
        Navigation.goTo("/cinema/views/page_ajout_salle.fxml", getStage(event));
    }
}
