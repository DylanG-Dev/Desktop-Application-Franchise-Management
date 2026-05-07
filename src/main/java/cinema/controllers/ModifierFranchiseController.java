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

import static cinema.controllers.Navigation.getParam;

public class ModifierFranchiseController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;

    @FXML
    private Button bRetour;

    @FXML
    private ListView<Utilisateur> lvGerantFranchise;

    @FXML
    private Label lblError;

    private int idFranchise;
    private int idGerant;

    // Méthode qui permet d'initialiser la page
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();
        lvGerantFranchise.setItems(utilisateurs);

        setAttributes(getParam("franchise"));

        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblError.setManaged(false);
    }

    // Fonction qui retourne une liste de tous les utilisateurs
    private ObservableList<Utilisateur> getUtilisateurList() {

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();

        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
        return list;
    }

    // Initialisation des attributs de la 'franchise' dans les champs
    public void setAttributes(Franchise franchise) {

        tfNomFranchise.setText(franchise.getNomFranchise());
        tfSiegeSocial.setText(franchise.getSiegeSocial());
        lvGerantFranchise.getSelectionModel().select(franchise.getIdGerant());
        this.idGerant = franchise.getIdGerant();
        this.idFranchise = franchise.getIdFranchise();
    }

    // Méthode qui permet de modifier une 'franchise' si tous les champs sont remplis
    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String nom = tfNomFranchise.getText();
        String siegeSocial = tfSiegeSocial.getText();
        Utilisateur selected = lvGerantFranchise.getSelectionModel().getSelectedItem();

        if (nom != null && siegeSocial != null && selected != null && !nom.trim().isEmpty()
                && !siegeSocial.trim().isEmpty()) {
            int idGerant = lvGerantFranchise.getSelectionModel().getSelectedItem().getIdUtilisateur();
            Franchise newFranchise = new Franchise(this.idFranchise, nom, siegeSocial, idGerant);

            FranchiseDAO franchiseDAO = new FranchiseDAO();
            boolean controle = franchiseDAO.update(newFranchise);
            if (controle) {
                Navigation.goTo("/cinema/views/page_liste_franchise.fxml", bRetour.getScene().getWindow());
                Navigation.showPopup("/cinema/views/popup_message_franchise_modif.fxml", "Validation modification franchise");
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
