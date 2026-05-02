package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Utilisateur;
import cinema.DAO.UtilisateurDAO;
//import cinema.viewModels.ConnexionViewModel;
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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ConnexionController implements Initializable {
    // Initialisation d'un attribut privé nommé 'utilisateurDAO'
    // avec une instance de la classe 'UtilisateurDAO'
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    // Initialisation et déclaration d'un attribut privé nommé 'encoder'
    // pour hacher les mots de passe qui ne peut pas être réassigné après
    // initialisation
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @FXML
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

        // Transmission du mot de passe sans déclarer
        // de String afin qu'il ne reste que très peu de
        // temps en mémoire
        Utilisateur user = authenticate(tfLogin.getText(), tfMDP.getText());

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
        Remplacement de l'argument par le nom
        de l'utilisateur */
        showAccueil(user.getNom());
    }

    // Fonction authenticate qui permet d'authentifier un
    // utilisateur
    public Utilisateur authenticate(String login, String password) {

        // Appel de la fonction 'authenticate' de l'instance
        // 'utilisateurDAO' avec le paramètre 'login', stockage
        // du résultat dans la variable 'user'
        Utilisateur user = utilisateurDAO.authenticate(login);
        if (user == null) {
            return null;
        }

        // Appel de la fonction 'getPassword' de l'instance
        // 'utilisateurDAO' avec le paremètre 'login',
        // stockage du résultat dans la variable bddPassword
        String bddPassword = utilisateurDAO.getPassword(login);
        if(password == null) {
            return null;
        }

        if(verify(password, bddPassword)) {
            // Renvoi de la variable nommé 'user' si le mot
            // de passe correspond au mot de passe en BDD
            return user;
        }

        return null;
    }

    // Fonction qui permet de hacher un mot de passe
    // saisis par l'utilisateur
    public static String hash(String password) {
        return encoder.encode(password);
    }

    // Fonction qui permet de vérifier que les deux mots
    // de passe correspondent
    public static boolean verify(String password, String hash) {
        return encoder.matches(password, hash);
    }

    private void showAccueil(String name) {
        // Fonction 'goTo' qui permet de naviguer sur la page d'accueil
        // avec le nom et le prénom de l'utilisateur en paramètre
//        Navigation.goTo("/cinema/views/page_accueil.fxml", "nameUti", name);
        Navigation.goTo("/cinema/views/page_accueil.fxml", "nameUti", name, bConnexion.getScene().getWindow());
    }

    @FXML
    private void showError() {
        Navigation.showPopup("/cinema/views/erreur_connexion.fxml", "Erreur connexion");
    }

    @FXML
    private void showResetPassword() {
        Navigation.showPopup("/cinema/views/reset_password.fxml", "Réinitialiser mot de passe");
    }

}
