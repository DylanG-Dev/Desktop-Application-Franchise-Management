package cinema.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cinema.BO.Cinema;
import cinema.BO.Utilisateur;
import cinema.Session;

public class CinemaDAO extends DAO<Cinema> {

    public void setConfig() {
        // récupère la connexion à la base de données
        Connection conn = DBManager.getInstance();

        // récupère l'utilisateur actuellement connecté à l'application
        Utilisateur currentUti = Session.getUtilisateur();

        if (currentUti != null) {
            // Définit une variable de configuration locale à la session en cours.
            String setSql = "SELECT set_config('app.current_id_utilisateur', ?, false)";

            // prepare la requête préparée avec la connexion à la
            // base de données paramétré avec l'id utilisateur de la
            // session actuelle
            try(PreparedStatement psSet = conn.prepareStatement(setSql)) {
                // String configuré avec la valeur de l'id utilisateur
                psSet.setString(1, String.valueOf(currentUti.getIdUtilisateur()));
                // Exécution de la requête préparée
                psSet.execute();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean create(Cinema obj) {
        boolean result = false;
        try {

            setConfig();

            String query = "INSERT INTO cinema (denomination, adresse, ville, id_franchise) VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setString(1, obj.getDenomination());
            preparedStatement.setString(2, obj.getAdresse());
            preparedStatement.setString(3, obj.getVille());
            preparedStatement.setInt(4, obj.getIdFranchise());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Cinema obj) {
        boolean result = false;
        String query = "DELETE FROM cinema WHERE id_cinema = ?;";

        try (PreparedStatement preparedStatement = this.connect.prepareStatement(query)) {

            setConfig();

            preparedStatement.setInt(1, obj.getIdCinema());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean update(Cinema obj) {
        boolean result = false;
        String query = "UPDATE cinema SET denomination = ?, adresse = ?, ville = ?, id_franchise = ? WHERE id_cinema = ?;";
        try {

            setConfig();
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setString(1, obj.getDenomination());
            preparedStatement.setString(2, obj.getAdresse());
            preparedStatement.setString(3, obj.getVille());
            preparedStatement.setInt(4, obj.getIdFranchise());
            preparedStatement.setInt(5, obj.getIdCinema());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Cinema find(int id) {
        Cinema cinema = null;
        String query = "SELECT * FROM cinema WHERE id_cinema = ?;";
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cinema = new Cinema(
                        resultSet.getInt("id_cinema"),
                        resultSet.getString("denomination"),
                        resultSet.getString("adresse"),
                        resultSet.getString("ville"),
                        resultSet.getInt("id_franchise"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinema;
    }

    @Override
    public List<Cinema> findAll() {
        List<Cinema> cinemas = new ArrayList<Cinema>();
        String query = "SELECT * FROM cinema;";

        try (PreparedStatement preparedStatement = this.connect.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Cinema cinema = new Cinema(
                        resultSet.getInt("id_cinema"),
                        resultSet.getString("denomination"),
                        resultSet.getString("adresse"),
                        resultSet.getString("ville"),
                        resultSet.getInt("id_franchise"));
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinemas;
    }

}
