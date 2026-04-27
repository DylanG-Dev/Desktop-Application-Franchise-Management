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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjouterSalleController extends MenuController implements Initializable {

    @FXML
    private TextField tfDescription;

    @FXML
    private Spinner spnrNumero, spnrNbPlace;

    @FXML
    private Button bRetour, bEnregistrer;

    @FXML
    private ListView<Cinema> lvCinema;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Cinema> cinemas = getCinemaList();

        lvCinema.setItems(cinemas);
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

        int numero = (int) spnrNumero.getValue();

        String description = tfDescription.getText();

        int nbPlace = (int) spnrNbPlace.getValue();

        int idCinema = lvCinema.getSelectionModel().getSelectedItem().getIdCinema();

        Salle salle = new Salle(numero, description, nbPlace, idCinema);

        SalleDAO salleDAO = new SalleDAO();
        boolean controle = salleDAO.create(salle);
        if (controle) {
            spnrNumero.getValueFactory().setValue(0);
            tfDescription.clear();
            spnrNbPlace.getValueFactory().setValue(0);
            lvCinema.getSelectionModel().clearSelection();
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
}
