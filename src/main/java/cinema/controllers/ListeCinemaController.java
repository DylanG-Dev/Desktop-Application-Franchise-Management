package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
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
    private TableColumn<Cinema, Void> tcModif, tcSupp;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tcDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        tcFranchise.setCellValueFactory(new PropertyValueFactory<>("franchise"));
        ObservableList<Cinema> data = getCinema();
        tvCinema.setItems(data);
    }

    private ObservableList<Cinema> getCinema() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> mesCinemas = cinemaDAO.findAll();
        ObservableList<Cinema> list = FXCollections.observableArrayList(mesCinemas);
        return list;
    }

    public void bRetourClick(ActionEvent actionEvent) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            // Correction du titre qui doit être 'Accueil' au lieu de 'Liste franchises'
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            // Ajout de l'icone cinema dans la page 'Accueil'
            stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

            // Configurer la fenêtre en tant que modal
            // Cette ligne ci dessous a été commenté car elle empêchait de minimiser la fenêtre
            //stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void btnModif() {
        tcModif.setCellFactory(column -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    Stage stageP = (Stage) bRetour.getScene().getWindow();
                    stageP.close();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("/cinema/views/page_modif_cinema.fxml"));
                        Parent root = fxmlLoader.load();

                        Stage stage = new Stage();
                        stage.setTitle("Modification cinema");
                        stage.setScene(new Scene(root));
                        // Ajout de l'icone cinema dans la page 'Modification cinema'
                        stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

                        // Configurer la fenêtre en tant que modal
                        // Cette ligne ci dessous a été commenté car elle empêchait de minimiser la fenêtre
                        //stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
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
                            stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));

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
