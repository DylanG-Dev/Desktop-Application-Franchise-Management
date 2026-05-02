package cinema.controllers;

import javafx.scene.control.Label;
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

    @FXML
    private Label lblSuccess, lblError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();

        lvGerantFranchise.setItems(utilisateurs);

        lblSuccess.setVisible(false);
        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblSuccess.setManaged(false);
        lblError.setManaged(false);
    }

    private ObservableList<Utilisateur> getUtilisateurList() {

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();

        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        // Correction des noms de variables 'x' et 'y' qui doivent se nommer 'nomFranchise' et 'siegeSocial'
        String nomFranchise = tfNomFranchise.getText();
        String siegeSocial = tfSiegeSocial.getText();
        Utilisateur selectGerant = lvGerantFranchise.getSelectionModel().getSelectedItem();

        if (!nomFranchise.trim().isEmpty() && !siegeSocial.trim().isEmpty() && selectGerant != null) {
            // Correction du nom de variable 'z' qui doit se nommer 'idGerant'
            int idGerant = selectGerant.getIdUtilisateur();
            // Correction du nom de variable 'bloup' qui doit se nommer 'franchise'
            Franchise franchise = new Franchise(nomFranchise, siegeSocial, idGerant);

            FranchiseDAO franchiseDAO = new FranchiseDAO();
            boolean controle = franchiseDAO.create(franchise);
            if (controle) {
                tfNomFranchise.clear();
                tfSiegeSocial.clear();
                lvGerantFranchise.getSelectionModel().clearSelection();
                messageSuccess();
            }
        } else {
            messageErreur();
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
