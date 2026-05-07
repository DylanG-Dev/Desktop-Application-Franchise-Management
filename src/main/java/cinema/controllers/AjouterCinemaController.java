package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AjouterCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;

    @FXML
    private Button bRetour, bEnregistrer;

    @FXML
    private ListView<Franchise> lvFranchise;

    @FXML
    private Label lblSuccess, lblError;

    // Méthode qui initialise la page
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Appel de la fonction 'getFranchiseList()' pour remplir
        // une 'ObservableList' avec toutes les franchises
        ObservableList<Franchise> franchises = getFranchiseList();

        // Mettre toutes les options de franchises dans la 'ListView'
        lvFranchise.setItems(franchises);

        // Rend invisible les labels d'erreur et de succès
        lblSuccess.setVisible(false);
        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblSuccess.setManaged(false);
        lblError.setManaged(false);
    }

    // Fonction qui permet de récupérer toutes les franchises
    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
        return list;
    }

    // Méthode qui permet de revenir à la page précédente
    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    // Méthode qui permet d'enregistrer le cinéma en base de données si tous les
    // champs sont remplis
    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();
        Franchise selectFranchise = lvFranchise.getSelectionModel().getSelectedItem();

        // Condition 'if' qui permet de vérifier que tous les champs soient remplis
        if(!denomination.trim().isEmpty() && !adresse.trim().isEmpty() && !ville.trim().isEmpty() && selectFranchise != null) {
            int idFranchise = selectFranchise.getIdFranchise();
            // Correction du nom de variable 'bloup' qui doit se nommer 'franchise'
            Cinema cinema = new Cinema(denomination, adresse, ville, idFranchise);

            CinemaDAO cinemaDAO = new CinemaDAO();
            boolean controle = cinemaDAO.create(cinema);
            if (controle) {
                tfDenomination.clear();
                tfAdresse.clear();
                tfVille.clear();
                lvFranchise.getSelectionModel().clearSelection();
                messageSuccess();
            }
        } else {
            messageErreur();
        }
    }

    // Méthode qui permet de remettre tous les champs vident du formulaire
    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfDenomination != null)
            tfDenomination.clear();
        if (tfAdresse != null)
            tfAdresse.clear();
        if (tfVille != null)
            tfVille.clear();
        lvFranchise.getSelectionModel().clearSelection();
    }

    // Méthode qui permet d'afficher un message de succès si l'enregistrement en
    // base de données a bien été effectués
    @FXML
    public void messageSuccess() {
        lblSuccess.setVisible(true);
        lblError.setVisible(false);
        lblSuccess.setManaged(true);
        lblError.setManaged(false);
    }

    // Méthode qui permet d'afficher un message d'erreur si l'enregistrement en base
    // de données n'a pas pu être effectué
    @FXML
    public void messageErreur() {
        lblSuccess.setVisible(false);
        lblError.setVisible(true);
        lblSuccess.setManaged(false);
        lblError.setManaged(true);
    }
}
