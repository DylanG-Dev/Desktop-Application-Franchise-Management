package cinema.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    protected MenuItem bListeFranchise, bAjouterFranchise,
            bListeCinema, bAjouterCinema, bQuitter,
            bAccueil, bListeSalle, bAjouterSalle;

    protected String nameUti;

    @FXML
    public void bQuitterClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void bAccueilClick(ActionEvent event) {
        Stage StageE = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        StageE.close();
        // Résolution du problème qui stoppait l'application lors du clique sur le bouton 'Accueil'
        try {
            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Liste franchises'
            stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bListFranchiseClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_franchise.fxml"));
            Parent root = fxmlLoader.load();


            // Obtenir le contrôleur de la nouvelle fenetre
            ListeFranchiseController listeFranchiseController = fxmlLoader.getController();
            listeFranchiseController.setName(nameUti);

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Liste franchises");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Liste franchises'
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

    @FXML
    public void bAjouterFranchiseClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_ajout_franchise.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            AjouterFranchiseController ajouterFranchiseController = fxmlLoader.getController();
            ajouterFranchiseController.setName(nameUti);

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Ajouter une franchise");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Ajout une franchise'
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

    @FXML
    public void bListeCinemaClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    // Erreur de syntaxe 'page_liste_cinemaa.fxml' au lieu de 'page_liste_cinema.fxml'
                    getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
            Parent root = fxmlLoader.load();

            ListeCinemaController listeSectionController = fxmlLoader.getController();
            listeSectionController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Liste cinéma");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Liste cinemas'
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
    public void bAjouterCinemaClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    // Erreur de syntaxe 'page_ajout_section.fxml' au lieu de 'page_ajout_cinema.fxml'
                    getClass().getResource("/cinema/views/page_ajout_section.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            // Erreur de syntaxe dans le titre, 'Ajouter d'une Section' au lieu de 'Ajout d'un cinéma'
            stage.setTitle("Ajout d'un cinéma");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Ajouter un cinéma'
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
    public void bListeSalleClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                    // Erreur de syntaxe 'page_liste_cours.fxml' au lieu de 'page_liste_salle.fxml'
                    getClass().getResource("/cinema/views/page_liste_salle.fxml"));
            Parent root = fxmlLoader.load();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            // Erreur de syntaxe dans le titre, 'Liste cours' au lieu de 'Liste salles'
            stage.setTitle("Liste salles");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Liste salles'
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

    public void setName(String nameUti) {
        this.nameUti = nameUti;
    }

    @FXML
    public void bAjouterSalleClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                    // Erreur de syntaxe 'page_ajout_cours.fxml' au lieu de 'page_ajout_salle.fxml'
                    getClass().getResource("/cinema/views/page_ajout_salle.fxml"));
            Parent root = fxmlLoader.load();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            // Erreur de syntaxe dans le titre, 'Ajout d'un cours' au lieu de 'Ajout d'une salle'
            stage.setTitle("Ajout d'une salle");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Ajouter une salle'
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
