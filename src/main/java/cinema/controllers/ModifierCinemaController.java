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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifierCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;

    @FXML
    private List<Franchise> franchises;

    @FXML
    private ListView<String> lvFranchise;

    @FXML
    private Button bRetour, bEnregistrer;

    private int idCinema;
    private int idFranchise; // stocke l'id de la franchise sélectionnée

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FranchiseDAO franchiseDAO = new FranchiseDAO();
        franchises = franchiseDAO.findAll(); // stocke la liste complète
        ObservableList<String> noms = FXCollections.observableArrayList();
        for (Franchise f : franchises) {
            noms.add(f.getNomFranchise());
        }
        lvFranchise.setItems(noms);
    }

    public void setAttributs(Cinema cinema) {
        // récupère les identifiants du cinéma reçu
        this.idCinema = cinema.getIdCinema();
        this.idFranchise = cinema.getIdFranchise();

        // pré-remplit les champs avec les données actuelles du cinéma
        tfDenomination.setText(cinema.getDenomination());
        tfAdresse.setText(cinema.getAdresse());
        tfVille.setText(cinema.getVille());

        // pré-sélectionne la franchise du cinéma dans la ListView
        for (int i = 0; i < franchises.size(); i++) {
            if (franchises.get(i).getIdFranchise() == cinema.getIdFranchise()) {
                lvFranchise.getSelectionModel().select(i);
                break;
            }
        }
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        // récupère les valeurs saisies dans les champs
        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();

        // retrouve la franchise correspondante via l'index sélectionné dans la ListView
        int index = lvFranchise.getSelectionModel().getSelectedIndex();
        Franchise selectedFranchise = index >= 0 ? franchises.get(index) : null;

        // vérifie que tous les champs sont remplis et qu'une franchise est sélectionnée
        if (!denomination.trim().isEmpty() && !adresse.trim().isEmpty()
                && !ville.trim().isEmpty() && selectedFranchise != null) {

            // crée le cinéma modifié avec les nouvelles valeurs
            Cinema cinema = new Cinema(idCinema, denomination, adresse, ville, selectedFranchise.getIdFranchise());
            CinemaDAO cinemaDAO = new CinemaDAO();
            boolean controle = cinemaDAO.update(cinema);

            if (controle) {
                Navigation.goTo("/cinema/views/page_liste_cinema.fxml");
            }
        } else {
            // affiche une popup d'erreur si un champ est vide ou aucune franchise sélectionnée
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(
                        getClass().getResource("/cinema/views/popup_erreur_saisie.fxml"));
                Parent root = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setTitle("Pop-up");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
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