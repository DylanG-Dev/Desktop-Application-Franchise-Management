package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cinema.BO.Utilisateur;

public class UtilisateurDAO extends DAO<Utilisateur> {

    // Inutilisé, ici pour respecter extension
    // DAO<Utilisateur>
    @Override
    public boolean create(Utilisateur obj) {
        throw new UnsupportedOperationException(
                "Utiliser createWithHashedPassword(Utilisateur obj, String password)"
        );
    }

    public boolean createWithHashedPassword(Utilisateur obj, String password) {
        boolean result = false;
        try {
            // Il manque les valeurs 'nom' et 'prenom'
            // ainsi que deux placeholders pour
            // l'insertion
            // Ajout du mot de passe haché via
            // le service 'authService' pour ne pas
            // stocker en mémoire le mot de passe
            // dans l'objet 'Utilisateur'
            String sql = "INSERT INTO utilisateur(nom, prenom, login, mdp) VALUES(?,?,?,?)";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getLogin());
            ps.setString(4, password);
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Utilisateur obj) {
        boolean result = false;
        try {
            String sql = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setInt(1, obj.getIdUtilisateur());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Utilisateur obj) {
        boolean result = false;
        try {
            // Il manque les valeurs 'nom' et 'prenom',
            // ainsi que deux placeholders pour
            // la mise à jour
            // Plus de champ 'mdp' car l'objet
            // 'Utilisateur' ne le le contient plus
            String sql = "UPDATE Utilisateur SET nom=?, prenom=?, login=? WHERE id_utilisateur = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getLogin());
            ps.setInt(4, obj.getIdUtilisateur());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Fonction pour mettre à jour le mot de passe
    public boolean updatePassword(String login, String password) {
        boolean result = false;
        try {
            String sql = "UPDATE Utilisateur SET mdp=? WHERE login = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, login);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Utilisateur hydrate(ResultSet resultSet) throws SQLException {
        return new Utilisateur(resultSet.getInt("id_utilisateur"),
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("login"));
    }

    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> mesUtilisateurs = new ArrayList<>();
        Utilisateur utilisateur;
        try {
            String sql = "SELECT * FROM utilisateur";
            Statement statement = this.connect.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                utilisateur = hydrate(rs);
                mesUtilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesUtilisateurs;
    }

    @Override
    public Utilisateur find(int idUtilisateur) {
        Utilisateur user;
        try {
            String sql = "SELECT * FROM utilisateur WHERE id_utilisateur = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setInt(1, idUtilisateur);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                user = hydrate(result);
            } else {
                user = null;
            }
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    // Modification de la fonction qui nécessite seulement
    // le 'login' car le mot de passe sera comparé haché
    // dans la classe 'AuthService'
    public Utilisateur authenticate(String login) {
        Utilisateur user = null;
        try {
            String sql = "SELECT * FROM utilisateur WHERE login = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                user = hydrate(result);
            }
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    // Ajout d'une fonction afin de récupérer le mot de
    // passe sans utiliser l'objet 'Utilisateur'
    public String getPassword(String login) {
        String password = null;
        try {
            String sql = "SELECT mdp FROM utilisateur WHERE login = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                password = result.getString("mdp");
            }
        } catch (SQLException e) {
            return null;
        }
        return password;
    }
}
