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

import static cinema.controllers.Navigation.getParam;

public class ModifierFranchiseController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Utilisateur> lvGerantFranchise;

    private int idFranchise;
    private int idGerant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();
        lvGerantFranchise.setItems(utilisateurs);

        setAttributes(getParam("franchise"));
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
            }
        } else {
            try {
                // Charger le fichier FXML
                FXMLLoader fxmlLoader = new FXMLLoader(
                        getClass().getResource("/cinema/views/popup_ajout_etu.fxml"));
                Parent root = fxmlLoader.load();

                // Créer une nouvelle fenêtre (Stage)
                Stage stage = new Stage();
                stage.setTitle("Pop-up");
                stage.setScene(new Scene(root));
                // Ajout de l'icone cinema dans la popup 'erreur_modification_franchise'
                stage.getIcons().add(new Image("/cinema/images/cinema_logo.png"));

                // Configurer la fenêtre en tant que modal
                stage.initModality(Modality.APPLICATION_MODAL);

                // Afficher la fenêtre et attendre qu'elle se ferme
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }
}
