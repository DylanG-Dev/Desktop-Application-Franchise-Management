package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Franchise;
import cinema.BO.Cinema;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.CinemaDAO;
import cinema.DAO.UtilisateurDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


public class ListeFranchiseController extends MenuController implements Initializable {
    @FXML
    private TableView<Franchise> tvFranchises;

    @FXML
    private TableColumn<Franchise, String> tcNomFranchise;

    @FXML
    private TableColumn<Franchise, String> tcSiegeSocial;

    @FXML
    private TableColumn<Franchise, String> tcGerant;

    @FXML
    private TableColumn<Franchise, Void> tcModifier;

    @FXML
    private TableColumn<Franchise, Void> tcSupprimer;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtilisateurDAO gerantDAO = new UtilisateurDAO();

        // Programmation fonctionnelle
        // Collecteur de flux :
        // https://www.ionos.fr/digitalguide/sites-internet/developpement-web/les-collectors-de-streams-en-java/
        // toMap :
        // https://www.geeksforgeeks.org/java/collectors-tomap-method-in-java-with-examples/
        //
        Map<Integer, Utilisateur> gerants = gerantDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Utilisateur::getIdUtilisateur, u -> u));

        tcGerant.setCellValueFactory(cellData -> {
            Utilisateur gerant = gerants.get(cellData.getValue().getIdGerant());
            return new SimpleStringProperty(
                    gerant != null ? gerant.getNom() : "Aucun gérant");
        });
        tcNomFranchise.setCellValueFactory(new PropertyValueFactory<>("nomFranchise"));
        tcSiegeSocial.setCellValueFactory(new PropertyValueFactory<>("siegeSocial"));
        ObservableList<Franchise> data = getFranchiseList();
        tvFranchises.setItems(data);

        addButtonModifierToTable();
        addButtonSupprimerToTable();

        // Ajout de la responsivité pour la balise 'TableView'
        tvFranchises.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private ObservableList<Franchise> getFranchiseList() {

        // Renommage des variables 'var1' et 'var2' par 'franchiseDAO' et 'franchises'
        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList();
        if (franchises != null) {
            list.addAll(franchises);
        }
        return list;
    }

    @FXML
    private void bRetourClick() {
        Navigation.goBack(bRetour.getScene().getWindow());
    }



    private void addButtonModifierToTable() {
        tcModifier.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Franchise franchise = getTableView().getItems().get(getIndex());

                    Navigation.goTo("/cinema/views/page_modif_franchise.fxml", "franchise", franchise, btn.getScene().getWindow());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        tcModifier.setSortable(false);
    }

    private void addButtonSupprimerToTable() {
        tcSupprimer.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");

            {
                btn.setOnAction(event -> {
                    Franchise franchise = getTableView().getItems().get(getIndex());
                    tvFranchises.getItems().remove(franchise);
                    FranchiseDAO franchiseDAO = new FranchiseDAO();
                    franchiseDAO.delete(franchise);
                });
                // btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        tcSupprimer.setSortable(false);
    }
}