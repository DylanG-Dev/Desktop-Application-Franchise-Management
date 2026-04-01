package cinema.BO;

public class Utilisateur {

    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String login;
    private String mdp;

    public Utilisateur() {
    }

    // Correction des affectations du constructeurs qui étaient en désordre
    public Utilisateur(int idUtilisateur, String nom, String prenom, String login, String mdp) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.mdp = mdp;
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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public String toString() {
        return "Utilisateur [idUtilisateur=" + idUtilisateur + ", nom=" + nom + ", prenom="
                + prenom + ", login=" + login + ", mdp=" + mdp + "]";
    }
}
