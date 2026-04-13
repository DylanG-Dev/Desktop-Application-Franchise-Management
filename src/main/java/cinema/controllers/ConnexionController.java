package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Utilisateur;
import cinema.viewModels.ConnexionViewModel;
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

import cinema.controllers.Navigation;

public class ConnexionController implements Initializable {

    // Initialisation d'un attribut privé 'viewModel'
    // avec une instance de la classe 'ConnexionViewModel'
    private ConnexionViewModel viewModel = new ConnexionViewModel();

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Récupération de la valeur du champ 'login'
        // Liaison bidirectionnelle de la valeur à 'loginProperty()'
        // dans la classe 'ConnexionViewModel'

        // Toute modification dans la vue mettra à jour le viewModel et vice-versa
        tfLogin.textProperty().bindBidirectional(viewModel.loginProperty());
    }

    @FXML
    private TextField tfLogin;

    @FXML
    private PasswordField tfMDP;

    @FXML
    private Button bConnexion;

    @FXML
    public void bConnexionClick(ActionEvent event) {
 //        boolean user = viewModel.login();
        // Correction des variables nommées 'truc' et 'chose' par 'login' et 'password'
//        String login = tfLogin.getText();
//        String password = tfMDP.getText();
//
//        UtilisateurDAO userDAO = new UtilisateurDAO();
//        // TODO
//        Utilisateur user = userDAO.authenticate(login, password);

        // Appel de la méthode 'login()' du viewModel
        // et stockage du résultat (objet Utilisateur)
        // dans la variable 'user'
//        Utilisateur user = viewModel.login();


        // Transmission du mot de passe sans déclarer
        // de String afin qu'il ne reste que très peu de
        // temps en mémoire
        Utilisateur user = viewModel.login(tfMDP.getText());

        // Effacement des champs 'tfLogin' et 'tfMDP'
        tfLogin.clear();
        tfMDP.clear();

        // affichage d'une popup avec un message d'erreur
        // si l'utilisateur n'existent pas en bdd ou s'il
        // se trompe de login ou de mot de passe
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
        // Fonction 'goTo' qui permet de naviguer sur la page d'accueil
        // avec le nom et le prénom de l'utilisateur en paramètre
//        Navigation.goTo("/cinema/views/page_accueil.fxml", "nameUti", name);
        Navigation.goTo("/cinema/views/page_accueil.fxml", "nameUti", name, bConnexion.getScene().getWindow());
    }

    @FXML
    private void showError() {
        try {
            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/erreur_connexion.fxml"));
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
            stage.getIcons().add(new Image("/cinema/images/cinema_logo.png"));

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
            stage.getIcons().add(new Image("/cinema/images/cinema_logo.png"));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
