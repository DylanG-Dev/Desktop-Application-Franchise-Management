package cinema.BO;

// suppression du mot de passe
// dans l'objet 'Utilisateur'
// pour ne pas stocker le mot
// de passe en mémoire

public class Utilisateur {

    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String login;

    public Utilisateur() {
    }

    // Constructeur pour créer un nouveau utilisateur pour l'insérer en base de données
    public Utilisateur(String nom, String prenom, String login) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
    }

    // Correction des affectations du constructeurs qui étaient en désordre
    // ne jamais avoir le mot de passe dans un constructeur 'Utilisateur'
    // Constructeur pour instancier un utilisateur existant en base de données
    public Utilisateur(int idUtilisateur, String nom, String prenom, String login) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    // Est-ce que c'est dérangeant de laisser 'setIdUtilisateur' ?
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

//    @Override
//    public String toString() {
//        return "Utilisateur [idUtilisateur=" + idUtilisateur + ", nom=" + nom + ", prenom="
//                + prenom + ", login=" + login + "]";
//    }

    @Override
    public String toString() {
        return this.nom + " " + this.prenom;
    }
}
