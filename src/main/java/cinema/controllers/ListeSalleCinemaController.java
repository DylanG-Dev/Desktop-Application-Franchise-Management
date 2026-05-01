package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Salle;
import cinema.DAO.SalleDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static cinema.controllers.Navigation.getParam;

public class ListeSalleCinemaController extends MenuController implements Initializable {

    @FXML
    private TableView<Salle> tvSalle;

    @FXML
    private TableColumn<Salle, Integer> tcNumero, tcNbPlaces;

    @FXML
    private TableColumn<Salle, String> tcDescription;

    @FXML
    private Button bRetour;

    private int idCinema;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcNbPlaces.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));

        setIdCinema();
    }

    public void setIdCinema() {
        int idCinema = getParam("cinema");
        // charge uniquement les salles du cinéma sélectionné
        SalleDAO salleDAO = new SalleDAO();
        List<Salle> toutes = salleDAO.findAll();

        // filtre les salles par idCinema
        List<Salle> sallesDuCinema = toutes.stream()
                .filter(s -> s.getIdCinema() == idCinema)
                .collect(Collectors.toList());

        ObservableList<Salle> data = FXCollections.observableArrayList(sallesDuCinema);
        tvSalle.setItems(data);
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }
}