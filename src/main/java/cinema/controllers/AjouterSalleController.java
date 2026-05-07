package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    // Méthode qui initialise la page
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Appel de la fonction 'getFranchiseList()' pour remplir
        // une 'ObservableList' avec toutes les franchises
        ObservableList<Cinema> cinemas = getCinemaList();

        // Mettre toutes les options de franchises dans la 'ListView'
        lvCinema.setItems(cinemas);
        // Rend invisible les labels d'erreur et de succès

        lblSuccess.setVisible(false);
        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblSuccess.setManaged(false);
        lblError.setManaged(false);
    }

    // Fonction qui permet de récupérer toutes les franchises
    private ObservableList<Cinema> getCinemaList() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();

        ObservableList<Cinema> list = FXCollections.observableArrayList(cinemas);
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
        int numero = spnrNumero.getValue();
        String description = tfDescription.getText();
        int nbPlace = spnrNbPlace.getValue();
        Cinema selectCinema = lvCinema.getSelectionModel().getSelectedItem();

        // Condition 'if' qui permet de vérifier que tous les champs soient remplis
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

    // Méthode qui permet de remettre tous les champs vident du formulaire
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
