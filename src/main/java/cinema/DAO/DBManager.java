package cinema.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static String url = "jdbc:postgresql://localhost:5432/gestion_cinema";

    private static String user = "cinema_usr";

    private static String pass = "cinema_pwd";

    private static Connection connect;

    public static Connection getInstance() {
        if (connect == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connect = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connect;
    }

//    // --- Connexion centralisée à la base de données ---
//    private static final String URL = "jdbc:postgresql://localhost:5433/gestion_cinema";
//
//    private static final String USER = "cinema_usr";
//
//    private static final String PASSWORD = "cinema_pwd";
//
//    private static Connection connection;
//
//    /**
//     * Retourne la connexion unique à la base de données.
//     *
//     * @return la connexion active
//     */
//    public static Connection getConnection() {
//        if (connection == null) {
//            try {
//                connection = DriverManager.getConnection(URL, USER, PASSWORD);
//                System.out.println("Connexion à la base réussie.");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println("Échec de la connexion à la base de données.");
//            }
//        }
//        return connection;
//    }
//
//    /**
//     * Ferme proprement la connexion à la base de données.
//     */
//    public static void closeConnection() {
//        if (connection != null) {
//            try {
//                connection.close();
//                connection = null;
//                System.out.println("Connexion à la base fermée.");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println("Erreur lors de la fermeture de la connexion.");
//            }
//        }
//    }
}
