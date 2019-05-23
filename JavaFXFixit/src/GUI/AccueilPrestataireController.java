/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXButton;
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
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import service.DemandeService;
import sun.misc.BASE64Decoder;

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
    private TableColumn colmunclient;
    @FXML
    private TableColumn colmunadress;
    @FXML
    private TableColumn colmundescription;
    @FXML
    private TableColumn<Demande, Void> colmunimage;
    @FXML
    private TableColumn colmundate;
    @FXML
    private TableColumn<Demande, Number> colmunprix;
    @FXML
    private TableColumn<Demande, Void> colmunvalide;

    @FXML
    private TableView tableviewdemande;

    ObservableList<Demande> ObservableListDemande;
    DemandeService demandeService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getAllDemandeAccepter();
    }

    private void getAllDemandeAccepter() {
        Client client = new Client();
        String sql = "SELECT * FROM user";

        try (Connection conn = this.connect();
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
            addButtonColumnImage(colmunimage);
            addButtonColumnValider(colmunvalide);
            addTextFieldColumn(colmunprix);
        }

    }

    private void addButtonColumnImage(TableColumn<Demande, Void> ColumnImage) {

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
                });
                return cell;
            }
        };

        ColumnImage.setCellFactory(cellFactory);
    }

    private void addButtonColumnValider(TableColumn<Demande, Void> Column) {

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

    public void addTextFieldColumn(TableColumn<Demande, Number> columnPrix) {
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

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:./db/user.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
