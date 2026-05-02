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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import static cinema.controllers.Navigation.getParam;

public class ModifierCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;

    @FXML
    private ListView<Franchise> lvFranchiseCinema;

    @FXML
    private Button bRetour, bEnregistrer;

    @FXML
    private Label lblError;

    private int idCinema;
    private int idFranchise; // stocke l'id de la franchise sélectionnée

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Franchise> franchises = getFranchiseList();
        lvFranchiseCinema.setItems(franchises);

        setAttributs(getParam("cinema"));


        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblError.setManaged(false);
    }

    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
        return list;
    }

    public void setAttributs(Cinema cinema) {
        // pré-remplit les champs avec les données actuelles du cinéma
        tfDenomination.setText(cinema.getDenomination());
        tfAdresse.setText(cinema.getAdresse());
        tfVille.setText(cinema.getVille());
        lvFranchiseCinema.getSelectionModel().select(cinema.getIdFranchise());

        // récupère les identifiants du cinéma reçu
        this.idCinema = cinema.getIdCinema();
        this.idFranchise = cinema.getIdFranchise();

        getParam("cinema");
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        // récupère les valeurs saisies dans les champs
        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();
        Franchise franchiseSelected = lvFranchiseCinema.getSelectionModel().getSelectedItem();

        // vérifie que tous les champs sont remplis et qu'une franchise est sélectionnée
        if (!denomination.trim().isEmpty() && !adresse.trim().isEmpty()
                && !ville.trim().isEmpty() && franchiseSelected != null) {

            // retrouve la franchise correspondante via l'index sélectionné dans la ListView
            int idFranchise = franchiseSelected.getIdFranchise();

            // crée le cinéma modifié avec les nouvelles valeurs
            Cinema cinema = new Cinema(idCinema, denomination, adresse, ville, idFranchise);
            CinemaDAO cinemaDAO = new CinemaDAO();
            boolean controle = cinemaDAO.update(cinema);

            if (controle) {
                Navigation.goTo("/cinema/views/page_liste_cinema.fxml", bRetour.getScene().getWindow());
                Navigation.showPopup("/cinema/views/popup_message_cinema_modif.fxml", "Validation modification cinéma");
            }
        } else {
            messageErreur();
        }
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    public void messageErreur() {
        lblError.setVisible(true);
        lblError.setManaged(true);
    }
}