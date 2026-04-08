package cinema.services;

import cinema.BO.Utilisateur;
import cinema.DAO.UtilisateurDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class AuthService {

    // Initialisation d'un attribut privé nommé 'utilisateurDAO'
    // avec une instance de la classe 'UtilisateurDAO'
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    // Initialisation d'un attribut privé nommé '
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
        } else {
            return null;
        }
    }

    // Fonction qui permet de hacher un mot de passe
    public static String hash(String password) {
        return encoder.encode(password);
    }

    // Fonction qui permet de vérifier que les deux mots
    // de passe correspondent
    public static boolean verify(String password, String hash) {
        return encoder.matches(password, hash);
    }
}
