package cinema.app;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.stage.Stage;
import cinema.controllers.Navigation;

//import java.io.IOException;

//public class MainApplication extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        try {
//            // chargement de la vue de connexion
//            Parent parent = FXMLLoader.load(getClass().getResource("/cinema/views/page_connexion.fxml"));
//
//            // configuration de la scène
//            Scene scene = new Scene(parent);
//
//            // paramétrage du stage (fenêtre principale)
//            primaryStage.setTitle("Application de gestion de franchise - Authentification");
//            // Est-ce qu'il est nécessaire de pouvoir modifier la taille
//            // de la fenêtre de connexion ? Responsivité à faire dans ce
//            // cas
//            primaryStage.setResizable(false);
//            primaryStage.centerOnScreen();
//            primaryStage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));
//            primaryStage.setScene(scene);
//
//            // Toujours au-dessus des autres fenêtres
//            primaryStage.setAlwaysOnTop(true);
//            // affichage
//            primaryStage.show();
//            primaryStage.setAlwaysOnTop(false);
//        } // end try
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        // On enregistre le stage principal dans le controller
        // 'Navigation'
        Navigation.setPrimaryStage(primaryStage);

        // paramétrage du stage (fenêtre principale)
        primaryStage.setTitle("Application de gestion de franchise - Authentification");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        // Toujours au-dessus des autres fenêtres
        primaryStage.setAlwaysOnTop(true);

        // Utilisation de la fonction 'goTo' dans le controller
        // 'navigation'
        Navigation.goTo("/cinema/views/page_connexion.fxml");

        // Désactive l'option qui permet à la fenêtre d'être
        // toujours au-dessus des autres fenêtres
        // Cela permet de faire apparaître la fenêtre au dessus
        // de toutes les autres fenêtes avec l'option 'true'
        // pour le lancement, et d'enlever l'option une fois que
        // l'application est lancé avec l'option 'false' pour que
        // les utilisateurs puissent réduire la fenêtre
        primaryStage.setAlwaysOnTop(false);
    }
}
