package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static cinema.controllers.Navigation.setParam;

public class ListeSalleController extends MenuController implements Initializable {

    @FXML
    private TableView<Salle> tvSalle;

    @FXML
    private TableColumn<Salle, String> tcNumero, tcDescription, tcNbPlace, tcCinema;

    @FXML
    private TableColumn<Salle, Void> tcSupp;

    @FXML
    private TableColumn<Salle, Void> tcModif;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rafraichirApresSuppr();

//        CinemaDAO cinemaDAO = new CinemaDAO();
//
//        // Programmation fonctionnelle
//        // Collecteur de flux :
//        // https://www.ionos.fr/digitalguide/sites-internet/developpement-web/les-collectors-de-streams-en-java/
//        // toMap :
//        // https://www.geeksforgeeks.org/java/collectors-tomap-method-in-java-with-examples/
//        //
//        Map<Integer, Cinema> cinemas = cinemaDAO.findAll()
//                .stream()
//                .collect(Collectors.toMap(Cinema::getIdCinema, c -> c));
//
//        tcCinema.setCellValueFactory(cellData -> {
//            Cinema cinema = cinemas.get(cellData.getValue().getIdCinema());
//            return new SimpleStringProperty(
//                    cinema != null ? cinema.getDenomination() : "Aucun cinéma");
//        });
//
//
//        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
//        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
//        tcNbPlace.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));
//        ObservableList<Salle> data = getSalleList();
//        tvSalle.setItems(data);
//
//        btnModif();
//        btnSupp();
    }

    public void rafraichirApresSuppr() {
        CinemaDAO cinemaDAO = new CinemaDAO();

        // Programmation fonctionnelle
        // Collecteur de flux :
        // https://www.ionos.fr/digitalguide/sites-internet/developpement-web/les-collectors-de-streams-en-java/
        // toMap :
        // https://www.geeksforgeeks.org/java/collectors-tomap-method-in-java-with-examples/
        //
        Map<Integer, Cinema> cinemas = cinemaDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Cinema::getIdCinema, c -> c));

        tcCinema.setCellValueFactory(cellData -> {
            Cinema cinema = cinemas.get(cellData.getValue().getIdCinema());
            return new SimpleStringProperty(
                    cinema != null ? cinema.getDenomination() : "Aucun cinéma");
        });


        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcNbPlace.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));
        ObservableList<Salle> data = getSalleList();
        tvSalle.setItems(data);

        btnModif();
        btnSupp();
    }

    private ObservableList<Salle> getSalleList() {

        SalleDAO salleDAO = new SalleDAO();
        List<Salle> salles = salleDAO.findAll();
        ObservableList<Salle> list = FXCollections.observableArrayList();
        if (salles != null) {
            list.addAll(salles);
        }
        return list;
    }

    public void bRetourClick(ActionEvent actionEvent) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }


    private void btnSupp() {
        tcSupp.setCellFactory(column -> new TableCell<Salle, Void>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    setParam("salle", salle);
                    Navigation.showPopup("/cinema/views/popup_valid_suppr_salle.fxml", "Message d'alerte");
                    rafraichirApresSuppr();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    // Mettre à jour le prix sans recharger la page
    // Ajouter un message de validation de suppression
    private void btnModif() {
        tcModif.setCellFactory(column -> new TableCell<Salle, Void>() {
            private final Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());

                    Navigation.goTo("/cinema/views/page_modif_salle.fxml", "salle", salle, btn.getScene().getWindow());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        tcModif.setSortable(false);
    }
}