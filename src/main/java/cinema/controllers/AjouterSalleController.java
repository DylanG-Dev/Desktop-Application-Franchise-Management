package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.BO.Salle;
import cinema.BO.Utilisateur;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.SalleDAO;
import cinema.DAO.UtilisateurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjouterSalleController extends MenuController implements Initializable {

    @FXML
    private TextField tfDescription;

    @FXML
    private Spinner<Integer> spnrNumero, spnrNbPlace;

    @FXML
    private Button bRetour, bEnregistrer;

    @FXML
    private ListView<Cinema> lvCinema;

    @FXML
    private Label lblSuccess, lblError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Cinema> cinemas = getCinemaList();

        lvCinema.setItems(cinemas);

        lblSuccess.setVisible(false);
        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblSuccess.setManaged(false);
        lblError.setManaged(false);
    }

    private ObservableList<Cinema> getCinemaList() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();

        ObservableList<Cinema> list = FXCollections.observableArrayList(cinemas);
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        int numero = spnrNumero.getValue();
        String description = tfDescription.getText();
        int nbPlace = spnrNbPlace.getValue();
        Cinema selectCinema = lvCinema.getSelectionModel().getSelectedItem();

        if(!description.trim().isEmpty() && selectCinema != null) {
            int idCinema = selectCinema.getIdCinema();

            Salle salle = new Salle(numero, description, nbPlace, idCinema);

            SalleDAO salleDAO = new SalleDAO();
            boolean controle = salleDAO.create(salle);
            if (controle) {
                spnrNumero.getValueFactory().setValue(0);
                tfDescription.clear();
                spnrNbPlace.getValueFactory().setValue(0);
                lvCinema.getSelectionModel().clearSelection();
                messageSuccess();
            }
        } else {
            messageErreur();
        }
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfDescription != null)
            tfDescription.clear();
        if (spnrNumero != null)
            spnrNumero.getValueFactory().setValue(0);
        if (spnrNbPlace != null)
            spnrNbPlace.getValueFactory().setValue(0);
        lvCinema.getSelectionModel().clearSelection();
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
