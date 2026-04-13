package cinema.viewModels;

import cinema.BO.Utilisateur;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import cinema.services.AuthService;

// Création d'une viewModel pour la connexion
public class ConnexionViewModel {
    // Initialisation d'un attribut privé 'login'
    // avec le type 'StringProperty' afin
    // de pouvoir observer les changement (listeners),
    // de faire du binding et de synchronsier
    // facilement avec l'UI
    // 'SimpleStringProperty permet d'instancier
    // concrètement cette propriété
    private StringProperty login = new SimpleStringProperty();

    // Initialisation d'un attribut privé 'authService'
    // avec une instance de la classe 'AuthService'
    private AuthService authService = new AuthService();

    // Getter qui retourne la propriété 'login' pour
    // permettre le binding et l'observation
    public StringProperty loginProperty() {
        return login;
    }

    // Appel de la fonction 'authenticate' de la classe
    // 'AuthService' avec en paramètre les valeurs actuelles
    // 'login et 'password' avec retour de son résultat
    public Utilisateur login(String password) {
        return authService.authenticate(login.get(), password);
    }
}
