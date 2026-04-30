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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Franchise> franchises = getFranchiseList();

        lvFranchise.setItems(franchises);
    }

    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
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
        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();
        int idFranchise = lvFranchise.getSelectionModel().getSelectedItem().getIdFranchise();



        // Correction du nom de variable 'bloup' qui doit se nommer 'franchise'
        Cinema cinema = new Cinema(0,denomination, adresse, ville, idFranchise);

        CinemaDAO cinemaDAO = new CinemaDAO();
        boolean controle = cinemaDAO.create(cinema);
        if (controle) {
            tfDenomination.clear();
            tfAdresse.clear();
            tfVille.clear();
            lvFranchise.getSelectionModel().clearSelection();
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
}
