/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXButton;
import entity.Annonce;
import entity.Client;
import entity.Demande;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import javax.imageio.ImageIO;
import service.AnnonceService;
import service.DemandeService;
import sun.misc.BASE64Decoder;
import utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author hphqlim
 */
public class AccueilPrestataireController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Tab tabannonce;
    @FXML
    private Tab tabdemande;
    @FXML
    private TableColumn colmunclient;
    @FXML
    private TableColumn colmunclientannoce;

    @FXML
    private TableColumn colmunadress;
    @FXML
    private TableColumn colmunadressannoce;
    @FXML
    private TableColumn colmundescription;
    @FXML
    private TableColumn colmundescriptionannonce;

    @FXML
    private TableColumn<Demande, Void> colmunimage;
    @FXML
    private TableColumn<Annonce, Void> colmunimageannonce;
    @FXML
    private TableColumn colmundate;
    @FXML
    private TableColumn colmunminprix;
    @FXML
    private TableColumn colmunmaxprix;
    @FXML
    private TableColumn<Demande, Number> colmunprix;
    @FXML
    private TableColumn<Annonce, Number> colmunprixannonce;
    @FXML
    private TableColumn<Demande, Void> colmunvalide;
    @FXML
    private TableColumn<Annonce, Void> colmunvalideannonce;

    @FXML
    private TableView tableviewdemande;
    @FXML
    private TableView tableviewannonce;

    ObservableList<Demande> ObservableListDemande;
    DemandeService demandeService;
    ObservableList<Annonce> ObservableListAnnonce;
    AnnonceService annonceService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    void eventTab1(Event ev) {
        if (tabannonce.isSelected()) {
            getAllAnnonce();
        }
    }

    @FXML
    void eventTab2(Event ev) {
        if (tabdemande.isSelected()) {
            getAllDemande();
        }
    }

    private void getAllDemande() {
        Client client = new Client();
        String sql = "SELECT * FROM user";

        try (Connection conn = Utilis.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            client.setId(rs.getInt(1));
            // setLBimg(rs.getString(10));  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (client.getId() != 0) {
            demandeService = new DemandeService();
            List<Demande> result = demandeService.getAllNewDemande(6);
            System.out.println(result);
            Image image = new Image(getClass().getResourceAsStream("img/avatar.png"));

            tableviewdemande.setEditable(true);
            ObservableListDemande = FXCollections.observableArrayList();
            ObservableListDemande.addAll(result);

            colmunclient.setCellValueFactory(new PropertyValueFactory<>("nomclient"));
            colmunadress.setCellValueFactory(new PropertyValueFactory<>("adresseclient"));
            colmundescription.setCellValueFactory(new PropertyValueFactory<>("description"));

            colmundate.setCellValueFactory(new PropertyValueFactory<>("dateFunction"));
            colmunprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            tableviewdemande.setItems(ObservableListDemande);
            addColumnImageDemande(colmunimage);
            addButtonDemandeColumnValider(colmunvalide);
            addTextFieldColumnDemande(colmunprix);
        }

    }

    private void getAllAnnonce() {
        Client client = new Client();
        String sql = "SELECT * FROM user";

        try (Connection conn = Utilis.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            client.setId(rs.getInt(1));
            // setLBimg(rs.getString(10));  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (client.getId() != 0) {
            annonceService = new AnnonceService();
            List<Annonce> result = annonceService.getAnnoncePublier(2);
            System.out.println(result);
            Image image = new Image(getClass().getResourceAsStream("img/avatar.png"));

            tableviewannonce.setEditable(true);
            ObservableListAnnonce = FXCollections.observableArrayList();
            ObservableListAnnonce.addAll(result);

            colmunclientannoce.setCellValueFactory(new PropertyValueFactory<>("nomclient"));
            colmunadressannoce.setCellValueFactory(new PropertyValueFactory<>("adresseclient"));
            colmundescriptionannonce.setCellValueFactory(new PropertyValueFactory<>("description"));

            colmunminprix.setCellValueFactory(new PropertyValueFactory<>("minprix"));
            colmunmaxprix.setCellValueFactory(new PropertyValueFactory<>("maxprix"));
            colmunprixannonce.setCellValueFactory(new PropertyValueFactory<>("prix"));
            tableviewannonce.setItems(ObservableListAnnonce);
            addColumnImageAnnonce(colmunimageannonce);
            addButtonAnnonceColumnValider(colmunvalideannonce);
            addTextFieldColumnAnnonce(colmunprixannonce);
        }

    }

    private void addColumnImageDemande(TableColumn<Demande, Void> ColumnImage) {

        Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>> cellFactory = new Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>>() {
            @Override
            public TableCell<Demande, Void> call(final TableColumn<Demande, Void> param) {
                final TableCell<Demande, Void> cell = new TableCell<Demande, Void>() {
                    ImageView imageView = new ImageView();

                    {

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Demande demande = getTableView().getItems().get(getIndex());
                            BASE64Decoder decoder = new BASE64Decoder();
                            byte[] imageByte;
                            try {
                                if (demande.getImage() != null) {
                                    imageByte = decoder.decodeBuffer(demande.getImage());
                                    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                                    BufferedImage bufferedImage = ImageIO.read(bis);
                                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                                    imageView.setImage(image);
                                    imageView.setFitHeight(100);
                                    imageView.setFitWidth(180);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(AccueilPrestataireController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            setGraphic(imageView);
                        }
                    }
                };
                cell.setOnMouseClicked(event -> {
                    Demande data = (Demande) cell.getTableRow().getItem();
                    System.out.println("selectedData: " + data);
                    goToImage(data.getImage());
                });
                return cell;
            }
        };

        ColumnImage.setCellFactory(cellFactory);
    }

    private void addButtonDemandeColumnValider(TableColumn<Demande, Void> Column) {

        Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>> cellFactory = new Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>>() {
            @Override
            public TableCell<Demande, Void> call(final TableColumn<Demande, Void> param) {
                final TableCell<Demande, Void> cell = new TableCell<Demande, Void>() {

                    private final JFXButton btn = new JFXButton("Confirme");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Demande data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                            demandeService.ConfirmerDemande(data.getId(), data.getPrix());
                            btn.setDisable(true);
                        });
                        btn.getStyleClass().add("valider");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        Column.setCellFactory(cellFactory);
    }

    public void addTextFieldColumnDemande(TableColumn<Demande, Number> columnPrix) {
        // Job is a String, editable column

        // Use a TextFieldTableCell, so it can be edited
        columnPrix.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        // Set editing related event handlers (OnEditCommit)
        columnPrix.setOnEditCommit((TableColumn.CellEditEvent<Demande, Number> t) -> {
            ((Demande) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setPrix(t.getNewValue().intValue());
        });

        columnPrix.setOnEditCommit((TableColumn.CellEditEvent<Demande, Number> t) -> {
            ((Demande) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setPrix(t.getNewValue().intValue());
        });
    }

    private void addColumnImageAnnonce(TableColumn<Annonce, Void> ColumnImage) {

        Callback<TableColumn<Annonce, Void>, TableCell<Annonce, Void>> cellFactory = new Callback<TableColumn<Annonce, Void>, TableCell<Annonce, Void>>() {
            @Override
            public TableCell<Annonce, Void> call(final TableColumn<Annonce, Void> param) {
                final TableCell<Annonce, Void> cell = new TableCell<Annonce, Void>() {
                    ImageView imageView = new ImageView();

                    {

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Annonce demande = getTableView().getItems().get(getIndex());
                            BASE64Decoder decoder = new BASE64Decoder();
                            byte[] imageByte;
                            try {
                                if (demande.getImage() != null) {
                                    imageByte = decoder.decodeBuffer(demande.getImage());
                                    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                                    BufferedImage bufferedImage = ImageIO.read(bis);
                                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                                    imageView.setImage(image);
                                    imageView.setFitHeight(100);
                                    imageView.setFitWidth(180);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(AccueilPrestataireController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            setGraphic(imageView);
                        }
                    }
                };
                cell.setOnMouseClicked(event -> {
                    Annonce data = (Annonce) cell.getTableRow().getItem();
                    System.out.println("selectedData: " + data);
                    goToImage(data.getImage());
                });
                return cell;
            }
        };

        ColumnImage.setCellFactory(cellFactory);
    }

    private void addButtonAnnonceColumnValider(TableColumn<Annonce, Void> Column) {

        Callback<TableColumn<Annonce, Void>, TableCell<Annonce, Void>> cellFactory = new Callback<TableColumn<Annonce, Void>, TableCell<Annonce, Void>>() {
            @Override
            public TableCell<Annonce, Void> call(final TableColumn<Annonce, Void> param) {
                final TableCell<Annonce, Void> cell = new TableCell<Annonce, Void>() {

                    private final JFXButton btn = new JFXButton("Accepter");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Annonce data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                            demandeService.ConfirmerDemande(data.getId(), data.getPrix());
                            btn.setDisable(true);
                        });
                        btn.getStyleClass().add("valider");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        Column.setCellFactory(cellFactory);
    }

    public void addTextFieldColumnAnnonce(TableColumn<Annonce, Number> columnPrix) {
        // Job is a String, editable column

        // Use a TextFieldTableCell, so it can be edited
        columnPrix.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        // Set editing related event handlers (OnEditCommit)
        columnPrix.setOnEditCommit((TableColumn.CellEditEvent<Annonce, Number> t) -> {
            ((Annonce) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setPrix(t.getNewValue().intValue());
        });

        columnPrix.setOnEditCommit((TableColumn.CellEditEvent<Annonce, Number> t) -> {
            ((Annonce) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setPrix(t.getNewValue().intValue());
        });
    }

    private void goToImage(String image) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ImageProbleme.fxml"));
        Parent root;
        try {
            root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 720, 480));
            ImageProblemeController controller = loader.<ImageProblemeController>getController();
            controller.setData(image);
            stage.setTitle("Image");
            stage.show();
            stage.setOnCloseRequest((javafx.stage.WindowEvent event1) -> {

            });
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
