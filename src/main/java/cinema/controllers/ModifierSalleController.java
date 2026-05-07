package cinema.controllers;

import javafx.scene.control.Label;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;

import static cinema.controllers.Navigation.getParam;

public class ModifierSalleController extends MenuController implements Initializable {

    @FXML
    private TextArea taDescription;

    @FXML
    private Spinner<Integer> spnrNumero, spnrNbPlace;

    @FXML
    private ListView<Cinema> lvCinemaSalle;

    private int idSalle;
    private int idCinema;

    @FXML
    private Label lblError;

    @FXML
    private Button bRetour, bEnregistrer;

    // Méthode qui permet d'initialiser la page
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Cinema> cinemas = getCinemaList();

        lvCinemaSalle.setItems(cinemas);

        setAttributes(getParam("salle"));


        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblError.setManaged(false);
    }

    // Fonction qui retourne une liste de tous les cinémas
    private ObservableList<Cinema> getCinemaList() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();

        ObservableList<Cinema> list = FXCollections.observableArrayList(cinemas);
        return list;
    }

    // Correction du nom de la méthode nommé 'setAttrinut'
    // par 'setAttributes'
    // Correction de la méthode complète
    // Initialisation des attributs de la 'salle' dans les champs
    public void setAttributes(Salle salle) {
        spnrNumero.getValueFactory().setValue(salle.getNumero());
        taDescription.setText(salle.getDescription());
        spnrNbPlace.getValueFactory().setValue(salle.getNbPlaces());
        lvCinemaSalle.getSelectionModel().select(salle.getIdCinema());
        this.idSalle = salle.getIdSalle();
        this.idCinema = salle.getIdCinema();
    }

    // Méthode qui permet de modifier une 'salle' si tous les champs sont remplis
    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        // Ajout des attributs pour l'enregistrement
        int idSalle = this.idSalle;
        int numero = spnrNumero.getValue();
        String description = taDescription.getText();
        int nbPlace = spnrNbPlace.getValue();
        Cinema selected = lvCinemaSalle.getSelectionModel().getSelectedItem();

        if (!description.trim().isEmpty() && selected != null) {
            int idCinema = lvCinemaSalle.getSelectionModel().getSelectedItem().getIdCinema();

            // Correction des paramètres de l'objet
            // Correction du nommage de la variable nommé 'sec' par 'newCinema'
            Salle newSalle = new Salle(this.idSalle, numero, description, nbPlace, idCinema);
            // Modification du nom de la variable 'sectionDAO' par 'cinemaDAO'
            SalleDAO salleDAO = new SalleDAO();
            // Mise à jour du nouveau cinéma créé
            boolean controle = salleDAO.update(newSalle);
            if (controle) {
                Navigation.goTo("/cinema/views/page_liste_salle.fxml", bRetour.getScene().getWindow());
                Navigation.showPopup("/cinema/views/popup_message_salle_modif.fxml", "Validation modification salle");
            }
        } else {
            messageErreur();
        }
    }

    // Méthode qui permet de revenir en arrière
    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    // Méthode qui permet d'afficher un message d'erreur si tous les champs ne sont pas remplis
    @FXML
    public void messageErreur() {
        lblError.setVisible(true);
        lblError.setManaged(true);
    }
}
