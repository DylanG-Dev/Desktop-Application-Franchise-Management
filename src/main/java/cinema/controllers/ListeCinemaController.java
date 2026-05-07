package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static cinema.controllers.Navigation.setParam;

public class ListeCinemaController extends MenuController implements Initializable {

    @FXML
    private TableView<Cinema> tvCinema;

    @FXML
    private TableColumn<Cinema, String> tcDenomination, tcFranchise, tcAdresse ,tcVille;

    @FXML
    private TableColumn<Cinema, Void> tcModif, tcSupp, tcSalle;

    @FXML
    private Button bRetour;

    // Méthode qui permet d'initialiser les cinémas dans page 'Liste cinémas'
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hydrateCinema();
    }

    // Méthode qui permet de récupérer tous les cinémas en base de données
    public void hydrateCinema() {
        FranchiseDAO franchiseDAO = new FranchiseDAO();

        // Programmation fonctionnelle
        // Collecteur de flux :
        // https://www.ionos.fr/digitalguide/sites-internet/developpement-web/les-collectors-de-streams-en-java/
        // toMap :
        // https://www.geeksforgeeks.org/java/collectors-tomap-method-in-java-with-examples/
        //
        Map<Integer, Franchise> franchises = franchiseDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Franchise::getIdFranchise, f -> f));

        tcFranchise.setCellValueFactory(cellData -> {
            Franchise franchise = franchises.get(cellData.getValue().getIdFranchise());
            return new SimpleStringProperty(
                    franchise != null ? franchise.getNomFranchise() : "Aucune franchise");
        });

        tcDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        tcAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tcVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        ObservableList<Cinema> data = getCinema();
        tvCinema.setItems(data);
        addButtonModifierToTable();
        addButtonSupprimerToTable();
        addButtonSallesToTable();
    }

    // Fonction qui permet de récupérer tous les cinémas en base de données
    private ObservableList<Cinema> getCinema() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> mesCinemas = cinemaDAO.findAll();
        ObservableList<Cinema> list = FXCollections.observableArrayList(mesCinemas);
        return list;
    }

    // Méthode qui permet de revenir sur la page précédente
    public void bRetourClick(ActionEvent actionEvent) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    // Initialisation d'un bouton "Modifier" sur chaque lignes 'cinéma' qui permet de rediriger vers la page de modification
    // avec les informations du cinéma concerné
    private void addButtonModifierToTable() {
        tcModif.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    Navigation.goTo("/cinema/views/page_modif_cinema.fxml", "cinema", cinema, btn.getScene().getWindow());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        //supprimer le filtre sur la colone
        tcModif.setSortable(false);
    }

    // Méthode qui permet lors du clique sur le bouton 'Supprimer', d'afficher une popup afin de valider la suppression
    // pour éviter les suppressions par erreur
    private void addButtonSupprimerToTable() {
        tcSupp.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    setParam("cinema", cinema);
                    Navigation.showPopup("/cinema/views/popup_valid_suppr_cinema.fxml", "Message d'alerte");
                    hydrateCinema();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        //supprimer le filtre sur la colone
        tcSupp.setSortable(false);
    }

    // Méthode qui permet d'initialiser un bouton 'Voir les salles'
    // qui permet de voir toutes les salles d'un cinéma
    private void addButtonSallesToTable() {
        tcSalle.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Voir les salles");
            {
                btn.setOnAction(event -> {
                    // récupère le cinéma de la ligne cliquée
                    Cinema cinema = getTableView().getItems().get(getIndex());

                    int idCinema = cinema.getIdCinema();

                    Navigation.goTo("/cinema/views/page_liste_salle_cinema.fxml", "cinema", idCinema, bRetour.getScene().getWindow());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        // supprime le filtre sur la colonne
        tcSalle.setSortable(false);
    }

}
