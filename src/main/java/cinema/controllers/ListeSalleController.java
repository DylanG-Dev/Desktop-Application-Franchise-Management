//package cinema.controllers;
//
//import java.net.URL;
//import java.util.List;
//import java.util.Map;
//import java.util.ResourceBundle;
//import java.util.stream.Collectors;
//
//import cinema.BO.Cinema;
//import cinema.BO.Salle;
//import cinema.DAO.CinemaDAO;
//import cinema.DAO.SalleDAO;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.stage.Stage;
//
//public class ListeSalleController extends MenuController implements Initializable {
//
//    @FXML
//    private TableView<Salle> tvSalle;
//
//    @FXML
//    private TableColumn<Salle, String> tcNumero, tcDescription, tcNbPlace, tcCinema;
//
//    @FXML
//    private TableColumn<Salle, Void> tcSupp;
//
//    @FXML
//    private TableColumn<Salle, Void> tcModif;
//
//    @FXML
//    private Button bRetour;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
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
//                .collect(Collectors.toMap(Cinema::getIdCinema, u -> u));
//
//        tcCinema.setCellValueFactory(cellData -> {
//            Cinema cinema = cinemas.get(cellData.getValue().getIdCinema());
//            return new SimpleStringProperty(
//                    cinema != null ? cinema.getDenomination() + " " + cinema.getAdresse() : "Aucune cinéma");
//        });
//
//        btnModif();
//        btnSupp();
//
//        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
//        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
//        tcNbPlace.setCellValueFactory(new PropertyValueFactory<>("nombre de places"));
//        ObservableList<Salle> data = getSalle();
//        tvSalle.setItems(data);
//    }
//
//    private ObservableList<Salle> getSalle() {
//
//        SalleDAO salleDAO = new SalleDAO();
//        List<Salle> mesSalles = salleDAO.findAll();
//        ObservableList<Salle> list = FXCollections.observableArrayList(mesSalles);
//        return list;
//    }
//
//    public void bRetourClick(ActionEvent actionEvent) {
//        Navigation.goBack();
//    }
//
//    private void btnModif() {
//        tcModif.setCellFactory(column -> new TableCell<Salle, Void>() {
//            private Button btn = new Button("Modifier");
//            {
//                btn.setOnAction(event -> {
//                    Salle salle = getTableView().getItems().get(getIndex());
//                    Stage stageP = (Stage) bRetour.getScene().getWindow();
//                    stageP.close();
//
//                    Navigation.goTo("/cinema/views/page_modif_salle.fxml");
//                });
//            }
//
//            @Override
//            protected void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                setGraphic(empty ? null : btn);
//            }
//        });
//    }
//
//    private void btnSupp() {
//        tcSupp.setCellFactory(col -> new TableCell<Salle, Void>() {
//            private Button btn = new Button("Supprimer");
//            {
//                btn.setOnAction(event -> {
//                    Salle salle = getTableView().getItems().get(getIndex());
//                    // Nom de la variable quoi doit se nommer 'cinemaDAO' à la place de 'etudiantDAO'
//                    CinemaDAO cinemaDAO = new CinemaDAO();
////                    if (cinemaDAO.getNbFranchiseByIdGerant(salle.getIdSalle()) >= 1) {
//                        try {
//                            // Charger le fichier FXML
//                            FXMLLoader fxmlLoader = new FXMLLoader(
//                                    // Popup non existante
//                                    getClass().getResource("/cinema/views/popup_cinema.fxml"));
//                            Parent root = fxmlLoader.load();
//
//                            // Créer une nouvelle fenêtre (Stage)
//                            Stage stage = new Stage();
//                            stage.setTitle("Pop-up");
//                            stage.setScene(new Scene(root));
//                            // Ajout de l'icone cinema dans la popup 'cinema'
//                            stage.getIcons().add(new Image("/cinema/images/cinema_logo.png"));
//
//                            // Configurer la fenêtre en tant que modal
//                            // Cette ligne ci dessous a été commenté car elle empêchait de minimiser la fenêtre
//                            //stage.initModality(Modality.APPLICATION_MODAL);
//
//                            // Afficher la fenêtre et attendre qu'elle se ferme
//                            stage.show();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
////                    } else {
////                        tvSalle.getItems().remove(salle);
////                        SalleDAO salleDAO = new SalleDAO();
////                        salleDAO.delete(salle);
////                    }
//                });
//            }
//
//            @Override
//            protected void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                setGraphic(empty ? null : btn);
//            }
//        });
//    }
//
//}
