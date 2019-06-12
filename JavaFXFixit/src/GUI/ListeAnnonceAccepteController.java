/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXButton;
import entity.Annonce;
import entity.AnnonceAccepte;
import entity.Demande;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import service.AnnonceService;

/**
 * FXML Controller class
 *
 * @author abdelhalim.benjmila
 */
public class ListeAnnonceAccepteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableColumn colmunprestataire;
    @FXML
    private TableColumn colmundate;
    @FXML
    private TableColumn colmunprix;
    @FXML
    private TableColumn<AnnonceAccepte, Void> colmunpayer;
    @FXML
    private TableView TableView;

    @FXML
    private Label title;
    @FXML
    private Label description;

    ObservableList<AnnonceAccepte> ObservableListAnnonce;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Annonce annonce) {
        System.out.print(annonce);
        if (annonce.getTitle() != null) {
            title.setText(annonce.getTitle());
        }
        if (annonce.getDescription() != null) {
            description.setText(annonce.getDescription());
        }
        AnnonceService annonceService = new AnnonceService();
        List<AnnonceAccepte> result = annonceService.getAnnonceAccepte(annonce.getId());

        TableView.setEditable(true);
        ObservableListAnnonce = FXCollections.observableArrayList();
        ObservableListAnnonce.addAll(result);

        colmunprestataire.setCellValueFactory(new PropertyValueFactory<>("nomprestataire"));
        colmunprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colmundate.setCellValueFactory(new PropertyValueFactory<>("date"));
        addButtonToTable(colmunpayer);
        TableView.setItems(ObservableListAnnonce);
    }

    private void addButtonToTable(TableColumn<AnnonceAccepte, Void> colmunpayer) {

        Callback<TableColumn<AnnonceAccepte, Void>, TableCell<AnnonceAccepte, Void>> cellFactory = new Callback<TableColumn<AnnonceAccepte, Void>, TableCell<AnnonceAccepte, Void>>() {
            @Override
            public TableCell<AnnonceAccepte, Void> call(final TableColumn<AnnonceAccepte, Void> param) {
                final TableCell<AnnonceAccepte, Void> cell = new TableCell<AnnonceAccepte, Void>() {

                    private final JFXButton btn = new JFXButton("Payer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            AnnonceAccepte data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                        btn.getStyleClass().add("payer");
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

        colmunpayer.setCellFactory(cellFactory);
    }

}
