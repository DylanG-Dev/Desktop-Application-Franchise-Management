package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.DAO.CinemaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifierCinemaController extends MenuController implements Initializable {

    // Correction du nommage de l'attribut 'TextArea' nommé 'taLibSec' par 'taDenomination'
    @FXML
    private TextArea taDenomination, taAdresse, taVille;

    private int idCinema;
    private int idFranchise;

    @FXML
    private Button bRetour, bEnregistrer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        this.idCinema = cinema.getIdCinema();
        this.idFranchise = cinema.getIdFranchise();
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
            Parent root = fxmlLoader.load();

            ListeCinemaController listeCinemaController = fxmlLoader.getController();
            listeCinemaController.setName(nameUti);

            Stage stage = new Stage();
            // Correction du titre du stage nommé 'Liste franchises' par 'Liste cinémas
            stage.setTitle("Liste cinémas");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Accueil'
            stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

            // Configurer la fenêtre en tant que modal
            // Cette ligne ci dessous a été commenté car elle empêchait de minimiser la fenêtre
            //stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        // Ajout des attributs pour l'enregistrement
        String denomination = taDenomination.getText();
        String adresse = taAdresse.getText();
        String ville = taVille.getText();
        if (!denomination.trim().isEmpty() && !adresse.trim().isEmpty() && !ville.trim().isEmpty()) {
            // Correction des paramètres de l'objet
            // Correction du nommage de la variable nommé 'sec' par 'newCinema'
            Cinema newCinema = new Cinema(this.idCinema, denomination, adresse, ville, idFranchise);
            // Modification du nom de la variable 'sectionDAO' par 'cinemaDAO'
            CinemaDAO cinemaDAO = new CinemaDAO();
            // Mise à jour du nouveau cinéma créé
            boolean controle = cinemaDAO.update(newCinema);
            if (controle) {
                Stage stageP = (Stage) bRetour.getScene().getWindow();
                stageP.close();
                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(
                            getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
                    Parent root = fxmlLoader.load();

                    ListeCinemaController listeCinemaController = fxmlLoader.getController();
                    listeCinemaController.setName(nameUti);

                    Stage stage = new Stage();
                    // Remplacement du titre 'Liste franchises' par 'Liste cinémas'
                    stage.setTitle("Liste cinémas");
                    stage.setScene(new Scene(root));
                    // Ajout de l'icone cinema dans la page 'Accueil'
                    stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

                    // Configurer la fenêtre en tant que modal
                    // Cette ligne ci dessous a été commenté car elle empêchait de minimiser la fenêtre
                    //stage.initModality(Modality.APPLICATION_MODAL);

                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

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
