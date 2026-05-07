package cinema.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static String url = "jdbc:postgresql://localhost:5433/gestion_cinema";

//    private static String url = "jdbc:postgresql://172.16.102.20:5433/bdd_groupe6";

    private static String user = "cinema_usr";

//    private static String user = "dylan";

    private static String pass = "cinema_pwd";

//    private static String pass = "dylan";

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
}
