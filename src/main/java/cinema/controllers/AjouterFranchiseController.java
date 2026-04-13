package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Franchise;
import cinema.BO.Utilisateur;
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

public class AjouterFranchiseController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;

    @FXML
    private Button bRetour, bEnregistrer;

    @FXML
    private ListView<Utilisateur> lvGerantFranchise;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();
        lvGerantFranchise.setItems(utilisateurs);
    }

    private ObservableList<Utilisateur> getUtilisateurList() {
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

            Stage stage = new Stage();
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/cinema/images/cinema_logo.png"));

            // Commenté car empêchait de minimiser la fenêtre
            //stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        String nom = tfNomFranchise.getText();
        String adresse = tfSiegeSocial.getText();
        Utilisateur SelectGerant = lvGerantFranchise.getSelectionModel().getSelectedItem();

        if (!nom.trim().isEmpty() && !adresse.trim().isEmpty() && SelectGerant != null) {
            int Gerant = SelectGerant.getIdUtilisateur();
            Franchise franchise = new Franchise(0, nom, adresse, Gerant);
            FranchiseDAO franchiseDAO = new FranchiseDAO();
            boolean controle = franchiseDAO.create(franchise);
            if (controle) {
                tfNomFranchise.clear();
                tfSiegeSocial.clear();
                lvGerantFranchise.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfNomFranchise != null)
            tfNomFranchise.clear();
        if (tfSiegeSocial != null)
            tfSiegeSocial.clear();
        lvGerantFranchise.getSelectionModel().clearSelection();
    }
}