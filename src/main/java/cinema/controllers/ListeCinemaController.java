package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.BO.Utilisateur;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.UtilisateurDAO;
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

public class ListeCinemaController extends MenuController implements Initializable {

    @FXML
    private TableView<Cinema> tvCinema;

    @FXML
    private TableColumn<Cinema, String> tcDenomination, tcFranchise;

    @FXML
    private TableColumn<Cinema, Void> tcModif;

    @FXML
    private TableColumn<Cinema, Void> tcSupp;

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
                    franchise != null ? franchise.getNomFranchise() + " " + franchise.getSiegeSocial() : "Aucune franchise");

        });

        // Ajout de la responsivité pour la balise 'TableView'
        tvCinema.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tcDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        tcFranchise.setCellValueFactory(new PropertyValueFactory<>("franchise"));
        ObservableList<Cinema> data = getCinema();
        tvCinema.setItems(data);

        btnModif();
        btnSupp();
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

    private void btnModif() {
        tcModif.setCellFactory(column -> new TableCell<Cinema, Void>() {
            private final Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    Stage stageP = (Stage) bRetour.getScene().getWindow();
                    stageP.close();

                    Navigation.goTo("/cinema/views/page_modif_cinema.fxml");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void btnSupp() {
        tcSupp.setCellFactory(col -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    // Nom de la variable quoi doit se nommer 'franchiseDAO' à la place de 'etudiantDAO'
                    FranchiseDAO franchiseDAO = new FranchiseDAO();
                    if (franchiseDAO.getNbFranchiseByIdGerant(cinema.getIdCinema()) >= 1) {
                        try {
                            // Charger le fichier FXML
                            FXMLLoader fxmlLoader = new FXMLLoader(
                                    // Popup non existante
                                    getClass().getResource("/cinema/views/popup_cinema.fxml"));
                            Parent root = fxmlLoader.load();

                            // Créer une nouvelle fenêtre (Stage)
                            Stage stage = new Stage();
                            stage.setTitle("Pop-up");
                            stage.setScene(new Scene(root));
                            // Ajout de l'icone cinema dans la popup 'cinema'
                            stage.getIcons().add(new Image("/cinema/images/cinema_logo.png"));

                            // Configurer la fenêtre en tant que modal
                            // Cette ligne ci dessous a été commenté car elle empêchait de minimiser la fenêtre
                            //stage.initModality(Modality.APPLICATION_MODAL);

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

}
