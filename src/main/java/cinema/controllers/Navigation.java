package cinema.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Navigation {

    // Initialisation et déclaration des attributs
    private static Stage primaryStage;
    private static final Stack<String> historique = new Stack<>();
    private static final Map<String, Object> params = new HashMap<>();

    // Méthode qui permet d'initialiser la fenêtre
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    // Méthode qui permet d'afficher la fenêtre avec seulement le chemin du fichier 'fxml'
    public static void goTo(String fxmlPath) {
        try {
            if (primaryStage != null) {
                if (!historique.isEmpty() && !historique.peek().equals(fxmlPath)) {
                    historique.push(fxmlPath);
                } else if (historique.isEmpty()) {
                    historique.push(fxmlPath);
                }

                Parent root = FXMLLoader.load(Navigation.class.getResource(fxmlPath));
                primaryStage.setScene(new Scene(root));

                primaryStage.setTitle(getTitle(fxmlPath));

                if (!fxmlPath.equals("/cinema/views/page_connexion.fxml")) {
                    primaryStage.setResizable(true);
                }

                // ✅ Réassigner l’icône
                primaryStage.getIcons().clear();
                primaryStage.getIcons()
                        .add(new Image(Navigation.class.getResourceAsStream("/cinema/images/cinema_logo.png")));

                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode qui permet d'afficher la fenêtre avec le chemin du fichier 'fxml',
    // le nom de l'objet, et le la valeur de l'objet
    public static void goTo(String fxmlPath, String key, Object value) {
        setParam(key, value);
        goTo(fxmlPath);
    }

    // Méthode qui permet d'afficher la fenêtre avec le chemin du fichier 'fxml',
    // et la fenêtre actuelle
    public static void goTo(String fxmlPath, Window currentWindow) {
        try {
            if (primaryStage != null) {
                if (!historique.isEmpty() && !historique.peek().equals(fxmlPath)) {
                    historique.push(fxmlPath);
                } else if (historique.isEmpty()) {
                    historique.push(fxmlPath);
                }
                Parent root = FXMLLoader.load(Navigation.class.getResource(fxmlPath));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
    //            newStage.setResizable(false);
                newStage.setTitle(getTitle(fxmlPath));

                // Permet de pouvoir modifier la taille de la fenêtre sauf pour la page de connexion
                if (!fxmlPath.equals("/cinema/views/page_connexion.fxml")) {
                    primaryStage.setResizable(true);
                }

                // ✅ Ajouter l’icône à la nouvelle fenêtre
                newStage.getIcons()
                        .add(new Image(Navigation.class.getResourceAsStream("/cinema/images/cinema_logo.png")));

                newStage.show();

                if (currentWindow != null) {
                    currentWindow.hide();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode qui permet d'afficher la fenêtre avec tous les
    // paramètres possibles, qui appelle les méthodes séparément
    public static void goTo(String fxmlPath, String key, Object value, Window currentWindow) {
        setParam(key, value);
        goTo(fxmlPath, currentWindow);
    }

    // Méthode qui permet de revenir à la fenêtre précédente
    // Ajout du paramètre 'Window currentWindow' afin de
    // pouvoir fermer la fenêtre précédente automatiquement
    public static void goBack(Window currentWindow) {
        if (historique.size() >= 2) {
            historique.pop();
            String previous = historique.peek();
            goTo(previous);
            if (currentWindow != null) {
                currentWindow.hide();
            }
        }
    }

    // Méthode pour effacer l'historique
    public static void clearHistory() {
        historique.clear();
    }

    // Méthode pour initialiser les paramètres
    public static void setParam(String key, Object value) {
        params.put(key, value);
    }

    // Fonction pour retourner les paramètres
    public static <T> T getParam(String key) {
        return (T) params.get(key);
    }

    // Méthode pour effacer les paramètres
    public static void clearParams() {
        params.clear();
    }

    // Fonction qui permet de changer le titre de l'écran
    // pour avoir le titre relié à l'écran
    public static String getTitle(String fxmlPath) {
        switch(fxmlPath) {
            case "/cinema/views/page_connexion.fxml":
                return "Application de gestion de franchise - Authentification";

            case "/cinema/views/page_accueil.fxml":
                return "Accueil Gestion de franchises";

            case "/cinema/views/page_ajout_cinema.fxml":
                return "Ajouter un cinéma";

            case "/cinema/views/page_ajout_franchise.fxml":
                return "Ajouter une franchise";

            case "/cinema/views/page_ajout_salle.fxml":
                return "Ajouter une salle";

            case "/cinema/views/page_liste_cinema.fxml":
                return "Liste cinémas";

            case "/cinema/views/page_liste_franchise.fxml":
                return "Liste franchises";

            case "/cinema/views/page_liste_salle.fxml":
                return "Liste salles";

            case "/cinema/views/page_modif_cinema.fxml":
                return "Modification cinéma";

            case "/cinema/views/page_modif_franchise.fxml":
                return "Modification franchise";

            case "/cinema/views/page_modif_salle.fxml":
                return "Modification salle";

            default:
                return "Application de gestion de franchise";
        }
    }
}
