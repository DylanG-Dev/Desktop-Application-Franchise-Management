package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import static cinema.controllers.Navigation.getParam;

public class ModifierSalleController extends MenuController implements Initializable {

    @FXML
    private TextArea taDescription;

    @FXML
    private Spinner spnrNumero, spnrNbPlace;

    @FXML
    private ListView<Cinema> lvCinemaSalle;

    private int idSalle;
    private int idCinema;

    @FXML
    private Button bRetour, bEnregistrer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Cinema> cinemas = getCinemaList();

        lvCinemaSalle.setItems(cinemas);

        setAttributes(getParam("salle"));
    }

    private ObservableList<Cinema> getCinemaList() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();

        ObservableList<Cinema> list = FXCollections.observableArrayList(cinemas);
        return list;
    }

    // Correction du nom de la méthode nommé 'setAttrinut'
    // par 'setAttributes'
    // Correction de la méthode complète
    public void setAttributes(Salle salle) {
        spnrNumero.getValueFactory().setValue(salle.getNumero());
        taDescription.setText(salle.getDescription());
        spnrNbPlace.getValueFactory().setValue(salle.getNbPlaces());
        lvCinemaSalle.getSelectionModel().select(salle.getIdCinema());
        this.idSalle = salle.getIdSalle();
        this.idCinema = salle.getIdCinema();
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        // Ajout des attributs pour l'enregistrement
        int idSalle = this.idSalle;
        int numero = (int) spnrNumero.getValue();
        String description = taDescription.getText();
        int nbPlace = (int) spnrNbPlace.getValue();
        Cinema selected = lvCinemaSalle.getSelectionModel().getSelectedItem();

        // Maybe add conditions on value 'numero' which can be equals to the same 'numero' of another 'salle'
        if (!description.trim().isEmpty()) {
            int idCinema = lvCinemaSalle.getSelectionModel().getSelectedItem().getIdCinema();

            // Correction des paramètres de l'objet
            // Correction du nommage de la variable nommé 'sec' par 'newCinema'
            Salle newSalle = new Salle(this.idSalle, numero, description, nbPlace, idCinema);
            // Modification du nom de la variable 'sectionDAO' par 'cinemaDAO'
            SalleDAO salleDAO = new SalleDAO();
            // Mise à jour du nouveau cinéma créé
            boolean controle = salleDAO.update(newSalle);
            if (controle) {
                Navigation.goTo("/cinema/views/page_liste_salle.fxml");
            }
        } else {
            try {
                // Charger le fichier FXML
                FXMLLoader fxmlLoader = new FXMLLoader(
                        // Popup ajoutEtu non existante
                        getClass().getResource("/cinema/views/popup_ajout_etu.fxml"));
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

    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }
}
