package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.BO.Utilisateur;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.UtilisateurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjouterCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;

    @FXML
    private Button bRetour, bEnregistrer;

    @FXML
    private ListView<Franchise> lvFranchise;

    @FXML
    private Label lblSuccess, lblError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Franchise> franchises = getFranchiseList();

        lvFranchise.setItems(franchises);

        lblSuccess.setVisible(false);
        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblSuccess.setManaged(false);
        lblError.setManaged(false);
    }

    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();
        Franchise selectFranchise = lvFranchise.getSelectionModel().getSelectedItem();

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

    @FXML
    public void messageSuccess() {
        lblSuccess.setVisible(true);
        lblError.setVisible(false);
        lblSuccess.setManaged(true);
        lblError.setManaged(false);
    }

    @FXML
    public void messageErreur() {
        lblSuccess.setVisible(false);
        lblError.setVisible(true);
        lblSuccess.setManaged(false);
        lblError.setManaged(true);
    }
}
