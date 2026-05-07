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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AjouterFranchiseController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;

    @FXML
    private Button bRetour, bEnregistrer;

    @FXML
    private ListView<Utilisateur> lvGerantFranchise;

    @FXML
    private Label lblSuccess, lblError;

    // Méthode qui initialise la page
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Appel de la fonction 'getFranchiseList()' pour remplir
        // une 'ObservableList' avec toutes les franchises
        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();

        // Mettre toutes les options de franchises dans la 'ListView'
        lvGerantFranchise.setItems(utilisateurs);

        // Rend invisible les labels d'erreur et de succès
        lblSuccess.setVisible(false);
        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblSuccess.setManaged(false);
        lblError.setManaged(false);
    }

    // Fonction qui permet de récupérer tous les utilisateurs
    private ObservableList<Utilisateur> getUtilisateurList() {

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();

        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
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
        // Correction des noms de variables 'x' et 'y' qui doivent se nommer 'nomFranchise' et 'siegeSocial'
        String nomFranchise = tfNomFranchise.getText();
        String siegeSocial = tfSiegeSocial.getText();
        Utilisateur selectGerant = lvGerantFranchise.getSelectionModel().getSelectedItem();

        // Condition 'if' qui permet de vérifier que tous les champs soient remplis
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

    // Méthode qui permet de remettre tous les champs vident du formulaire
    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfNomFranchise != null)
            tfNomFranchise.clear();
        if (tfSiegeSocial != null)
            tfSiegeSocial.clear();
        lvGerantFranchise.getSelectionModel().clearSelection();
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
