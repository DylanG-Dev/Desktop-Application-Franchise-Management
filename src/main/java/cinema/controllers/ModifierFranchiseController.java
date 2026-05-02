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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();
        lvGerantFranchise.setItems(utilisateurs);

        setAttributes(getParam("franchise"));

        lblError.setVisible(false);

        // Permet de ne pas occuper l'espace visuellement
        lblError.setManaged(false);
    }

    private ObservableList<Utilisateur> getUtilisateurList() {

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();

        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
        return list;
    }

    public void setAttributes(Franchise franchise) {

        tfNomFranchise.setText(franchise.getNomFranchise());
        tfSiegeSocial.setText(franchise.getSiegeSocial());
        lvGerantFranchise.getSelectionModel().select(franchise.getIdGerant());
        this.idGerant = franchise.getIdGerant();
        this.idFranchise = franchise.getIdFranchise();
    }

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
