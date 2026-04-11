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
    private Spinner spNumero, spNbPlace;

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
        // On fait le lien avec l'ecran actuel
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        // on ferme l'écran
        stageP.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            // Correction du titre qui doit être 'Accueil' au lieu de 'Liste franchises'
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Accueil'
            stage.getIcons().add(new Image("/cinema/images/cinema_logo.png"));


            // Configurer la fenêtre en tant que modal
            // Cette ligne ci dessous a été commenté car elle empêchait de minimiser la fenêtre
            //stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {

        int numero = (int) spNumero.getValue();

        String description = tfDescription.getText();

        int nbPlace = (int) spNbPlace.getValue();

        int idCinema = lvCinema.getSelectionModel().getSelectedItem().getIdCinema();

        Salle salle = new Salle(numero, description, nbPlace, idCinema);

        SalleDAO salleDAO = new SalleDAO();
        boolean controle = salleDAO.create(salle);
        if (controle) {
            spNumero.getValueFactory().setValue(0);
            tfDescription.clear();
            spNbPlace.getValueFactory().setValue(0);
            lvCinema.getSelectionModel().clearSelection();
        }
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfDescription != null)
            tfDescription.clear();
        if (spNumero != null)
            spNumero.getValueFactory().setValue(0);
        if (spNbPlace != null)
            spNbPlace.getValueFactory().setValue(0);
        lvCinema.getSelectionModel().clearSelection();
    }
}
