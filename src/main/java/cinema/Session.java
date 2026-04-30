package cinema;
import cinema.BO.Utilisateur;

public class Session {

    // --- Gestion de l'utilisateur connecté ---
    private static Utilisateur utilisateurConnecte;

    public static void setUtilisateur(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    public static void clear() {
        utilisateurConnecte = null;
    }

    // Suppression de toute la partie qui permettait de se
    // connecter à la BDD car elle est déjà présente dans la
    // classe 'DBManager.java'
}
