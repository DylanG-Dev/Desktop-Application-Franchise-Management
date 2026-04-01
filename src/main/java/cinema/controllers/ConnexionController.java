package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Utilisateur;
import cinema.DAO.UtilisateurDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnexionController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfMDP;
    @FXML
    private Button bConnexion;

    @FXML
    public void bConnexionClick(ActionEvent event) {
        // Correction des variables nommées 'truc' et 'chose' par 'login' et 'password'
        String login = tfLogin.getText();
        String password = tfMDP.getText();

        UtilisateurDAO userDAO = new UtilisateurDAO();
        // TODO
        Utilisateur user = userDAO.authenticate(login, password);

        // affichage d'une popup avec un message d'erreur
        // si l'utilisateur n'existent pas en bdd
        if (user == null) {
            showError();
            return;
        }

        // sinon l'utilisateur est connecté et renvoyé
        // sur la page d'accueil

        /* Correction de l'argument qui était l'adresse
        email de l'utilisateur qui pouvait être une faille
        de sécurité si une personne voyait son login.
        Remplacement de l'argument par le prénom et le nom
        de l'utilisateur */
        showAccueil(user.getPrenom() + " " + user.getNom());
    }

    private void showAccueil(String name) {
        Stage stageP = (Stage) bConnexion.getScene().getWindow();
        // on ferme l'écran
        stageP.close();
        try {

            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(name);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Accueil Gestion de franchises");
            stage.setScene(new Scene(root));
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
    private void showError() {
        try {
            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/ErreurConnexion.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la pop-up
            ErrorController errorController = fxmlLoader.getController();

            // Passer la variable au contrôleur de la pop-up
            // errorController.setMajLabel(Integer.toString(compteur));

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Error Window");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la popup d'erreur d'identifiants de connexion
            stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

            // Configurer la fenêtre en tant que modal afin
            // que l'utilisateur ne puisse pas retourner sur
            // la fenêtre de connexion sans la fermer
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showResetPassword() {
        try {
            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/reset_password.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la pop-up
            ResetPasswordController ResetPasswordController = fxmlLoader.getController();

            // Passer la variable au contrôleur de la pop-up
            // errorController.setMajLabel(Integer.toString(compteur));

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Réinitialiser mot de passe");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la popup de réinitialisation de mot de passe
            stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
