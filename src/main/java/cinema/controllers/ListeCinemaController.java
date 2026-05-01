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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListeCinemaController extends MenuController implements Initializable {

    @FXML
    private TableView<Cinema> tvCinema;

    @FXML
    private TableColumn<Cinema, String> tcDenomination, tcFranchise, tcAdresse ,tcVille;

    @FXML
    private TableColumn<Cinema, Void> tcModif, tcSupp, tcSalle;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    private ObservableList<Cinema> getCinema() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> mesCinemas = cinemaDAO.findAll();
        ObservableList<Cinema> list = FXCollections.observableArrayList(mesCinemas);
        return list;
    }

    public void bRetourClick(ActionEvent actionEvent) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }


    private void btnSupp() {
        tcSupp.setCellFactory(col -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    FranchiseDAO etudiantDAO = new FranchiseDAO();
                    if (etudiantDAO.getNbFranchiseByIdGerant(cinema.getIdCinema()) >= 1) {
                        try {
                            // Charger le fichier FXML
                            FXMLLoader fxmlLoader = new FXMLLoader(
                                    getClass().getResource("/cinema/views/popup_cinema.fxml"));
                            Parent root = fxmlLoader.load();

                            // Créer une nouvelle fenêtre (Stage)
                            Stage stage = new Stage();
                            stage.setTitle("Pop-up");
                            stage.setScene(new Scene(root));

                            // Configurer la fenêtre en tant que modal
                            stage.initModality(Modality.APPLICATION_MODAL);

                            // Afficher la fenêtre et attendre qu'elle se ferme
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        tvCinema.getItems().remove(cinema);
                        CinemaDAO cinemaDAO = new CinemaDAO();
                        cinemaDAO.delete(cinema);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }
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


    private void addButtonSupprimerToTable() {
        tcSupp.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    tvCinema.getItems().remove(cinema);
                    CinemaDAO cinemaDAO = new CinemaDAO();
                    cinemaDAO.delete(cinema);
                });
                // btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
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
