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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static cinema.controllers.Navigation.getParam;

public class ModifierCinemaController extends MenuController implements Initializable {

    // Correction du nommage de l'attribut 'TextArea' nommé 'taLibSec' par 'taDenomination'
    @FXML
    private TextArea taDenomination, taAdresse, taVille;

    @FXML
    ListView<Franchise> lvFranchiseCinema;

    private int idCinema;
    private int idFranchise;

    @FXML
    private Button bRetour, bEnregistrer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Franchise> franchises = getFranchiseList();
        lvFranchiseCinema.setItems(franchises);

        setAttributes(getParam("cinema"));
    }

    private ObservableList<Franchise> getFranchiseList() {
        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
        return list;
    }

    public void setIdCinema(int idCinema) {
        this.idCinema = idCinema;
    }

    // Correction du nom de la méthode nommé 'setAttrinut'
    // par 'setAttributes'
    // Correction de la méthode complète
    public void setAttributes(Cinema cinema) {
        taDenomination.setText(cinema.getDenomination());
        taAdresse.setText(cinema.getAdresse());
        taVille.setText(cinema.getVille());
        lvFranchiseCinema.getSelectionModel().select(cinema.getIdFranchise());
        this.idCinema = cinema.getIdCinema();
        this.idFranchise = cinema.getIdFranchise();
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        // Ajout des attributs pour l'enregistrement
        String denomination = taDenomination.getText();
        String adresse = taAdresse.getText();
        String ville = taVille.getText();

        Franchise selected = lvFranchiseCinema.getSelectionModel().getSelectedItem();
        if (!denomination.trim().isEmpty() && !adresse.trim().isEmpty() && !ville.trim().isEmpty()) {
            int idFranchise = lvFranchiseCinema.getSelectionModel().getSelectedItem().getIdFranchise();
            // Correction des paramètres de l'objet
            // Correction du nommage de la variable nommé 'sec' par 'newCinema'
            Cinema newCinema = new Cinema(this.idCinema, denomination, adresse, ville, idFranchise);
            // Modification du nom de la variable 'sectionDAO' par 'cinemaDAO'
            CinemaDAO cinemaDAO = new CinemaDAO();
            // Mise à jour du nouveau cinéma créé
            boolean controle = cinemaDAO.update(newCinema);
            if (controle) {
                Navigation.goTo("/cinema/views/page_liste_cinema.fxml");
            }
        } else {
            try {
                // Charger le fichier FXML
                FXMLLoader fxmlLoader = new FXMLLoader(
                        // Popup ajoutEtu non existante
                        // Remplacement par le nom 'popup_erreur_saisie', ou seulement renvoyer un msg d'erreur, qui sera faites pendant le lot 2
                        getClass().getResource("/cinema/views/popup_erreur_saisie.fxml"));
                Parent root = fxmlLoader.load();

                // Créer une nouvelle fenêtre (Stage)
                Stage stage = new Stage();
                stage.setTitle("Pop-up");
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
    }
}
