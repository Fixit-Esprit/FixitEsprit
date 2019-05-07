/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.sun.prism.PhongMaterial;
import javafx.fxml.Initializable;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import entity.Pays;
import entity.Position;
import entity.Prestataire;
import entity.Region;
import entity.Service;
import entity.Ville;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxfixit.JavaFXFixit;
import netscape.javascript.JSObject;
import service.PaysService;
import service.PrestataireService;
import service.RegionService;
import service.ServiceService;
import service.VilleService;

public class AccueilController implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    @FXML
    private Pane panep;

    @FXML
    private JFXComboBox comboboxservice;

    @FXML
    private JFXComboBox comboboxpays;

    @FXML
    private JFXComboBox comboboxregion;

    @FXML
    private JFXComboBox comboboxville;

    @FXML
    private GridPane GridAllUser;

    @FXML
    private JFXTextField motcle;

    List<Service> service;
    List<Pays> pays;
    List<Region> region;
    List<Ville> ville;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.setKey("AIzaSyCpBZ4AjkZIoLWHnYYF5qsdQO5CTnCpcko");
        mapView.addMapInializedListener(this);

        ServiceService serviceService = new ServiceService();
        service = serviceService.getAllService();
        ArrayList<String> listservise = new ArrayList<String>();
        for (Service s : service) {
            listservise.add(s.getDescription());
        }
        ObservableList<String> olistservice = FXCollections.observableArrayList(listservise);
        comboboxservice.setItems(olistservice);

        PaysService paysService = new PaysService();
        pays = paysService.getAllPays();
        ArrayList<String> listp = new ArrayList<String>();
        for (Pays p : pays) {
            listp.add(p.getNom());
        }
        ObservableList<String> olist = FXCollections.observableArrayList(listp);
        comboboxpays.setItems(olist);

        comboboxpays.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                int id = (int) pays.stream().filter(p -> p.getNom().equals(newValue)).mapToInt(p -> p.getId()).average().getAsDouble();
                RegionService regionservice = new RegionService();
                region = regionservice.getRegionByPays(id);
                region.toString();
                ArrayList<String> listr = new ArrayList<String>();
                for (Region p : region) {
                    listr.add(p.getNom());
                }
                ObservableList<String> olistregion = FXCollections.observableArrayList(listr);
                comboboxregion.setItems(olistregion);
            }
        });
        comboboxpays.getSelectionModel().select(204);

        comboboxregion.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                int id = (int) region.stream().filter(r -> r.getNom().equals(newValue)).mapToInt(r -> r.getId()).average().getAsDouble();
                VilleService villeService = new VilleService();
                ville = villeService.getVilleByRegion(id);
                ville.toString();
                ArrayList<String> listr = new ArrayList<String>();
                for (Ville v : ville) {
                    listr.add(v.getNom());
                }
                ObservableList<String> olistregion = FXCollections.observableArrayList(listr);
                comboboxville.setItems(olistregion);
            }
        });

    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(36.82, 10.17))
                .mapType(MapTypeIdEnum.HYBRID)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(7);

        map = mapView.createMap(mapOptions);
    }

    @FXML
    private void recherchePrestataireByName(ActionEvent event) {
        GridAllUser.getChildren().clear();
        map.clearMarkers();
        try {
            System.out.println(motcle.getText());
            int idService = (int) service.stream().filter(r -> r.getDescription().equals(comboboxservice.getValue())).mapToInt(r -> r.getId()).average().getAsDouble();
            System.out.println(idService);

            PrestataireService prestataireService = new PrestataireService();
            List<Prestataire> Prestataires = prestataireService.getPrestatairByService(idService);
            System.out.println(Prestataires);

            int Column = 0;
            int Row = 0;
            for (Prestataire prestataire : Prestataires) {
                addpositions(prestataire);
                VBox vbox = new VBox(5);
                vbox.setPrefSize(300, 300);
                HBox hbox = new HBox(2);

                vbox.setAlignment(Pos.CENTER);
                hbox.setAlignment(Pos.CENTER);
                hbox.setPadding(Insets.EMPTY);
                JFXButton btndemande = new JFXButton("Demande");
                btndemande.getStyleClass().add("detail");

                JFXButton btndetail = new JFXButton("Detail");
                btndetail.getStyleClass().add("detail");

                /*stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btn.getScene().getWindow());*/
                btndetail.setOnAction((ActionEvent e) -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Detailprestataire.fxml"));
                    Parent root;
                    try {
                        root = loader.load();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 820, 480));
                        DetailPrestataireController controller = loader.<DetailPrestataireController>getController();
                        controller.setData(prestataire);
                        stage.setTitle("Detail " + prestataire.getNom() + " " + prestataire.getPrenom());
                        stage.show();
                        stage.setOnCloseRequest((javafx.stage.WindowEvent event1) -> {

                        });
                    } catch (IOException ex) {
                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                hbox.getChildren().addAll(btndemande, btndetail);
                Label lNom = new Label(prestataire.getNom() + " " + prestataire.getPrenom());
                lNom.getStyleClass().add("textd");
                Label lEmail = new Label(prestataire.getEmail());
                lEmail.getStyleClass().add("textd");
                Label lTel = new Label(prestataire.getTel());
                lTel.getStyleClass().add("textd");
                
                final Pane cardsPane = new StackPane();
                ImageView imageView;
                if (prestataire.getImage() == null) {

                    Image image = new Image(getClass().getResourceAsStream("img/avatar.png"));
                    imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    vbox.getChildren().addAll(imageView, lNom,lEmail,lTel, hbox);
                } else {
                    vbox.getChildren().addAll(lNom, hbox);
                }
                vbox.setPadding(new Insets(10, 20, 30, 30));
                GridAllUser.resize(5000, 1000);
                GridAllUser.add(vbox, Column, Row);

                if (Column < 2) {
                    Column++;
                } else {
                    Row++;
                    Column = 0;
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void recherchePrestataireByAdress(ActionEvent event) {

        GridAllUser.getChildren().clear();
        map.clearMarkers();
        try {
            int idVille = (int) ville.stream().filter(r -> r.getNom().equals(comboboxville.getValue())).mapToInt(r -> r.getId()).average().getAsDouble();
            System.out.println(idVille);
            int idService = (int) service.stream().filter(r -> r.getDescription().equals(comboboxservice.getValue())).mapToInt(r -> r.getId()).average().getAsDouble();
            System.out.println(idService);

            PrestataireService prestataireService = new PrestataireService();
            List<Prestataire> Prestataires = prestataireService.getPrestatairByVilleAndService(idVille, idService);
            System.out.println(Prestataires);

                        int Column = 0;
            int Row = 0;
            for (Prestataire prestataire : Prestataires) {
                addpositions(prestataire);
                VBox vbox = new VBox(5);
                vbox.setPrefSize(300, 300);
                HBox hbox = new HBox(2);

                vbox.setAlignment(Pos.CENTER);
                hbox.setAlignment(Pos.CENTER);
                hbox.setPadding(Insets.EMPTY);
                JFXButton btndemande = new JFXButton("Demande");
                btndemande.getStyleClass().add("detail");

                JFXButton btndetail = new JFXButton("Detail");
                btndetail.getStyleClass().add("detail");

                /*stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btn.getScene().getWindow());*/
                btndetail.setOnAction((ActionEvent e) -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Detailprestataire.fxml"));
                    Parent root;
                    try {
                        root = loader.load();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 820, 480));
                        DetailPrestataireController controller = loader.<DetailPrestataireController>getController();
                        controller.setData(prestataire);
                        stage.setTitle("Detail " + prestataire.getNom() + " " + prestataire.getPrenom());
                        stage.show();
                        stage.setOnCloseRequest((javafx.stage.WindowEvent event1) -> {

                        });
                    } catch (IOException ex) {
                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                hbox.getChildren().addAll(btndemande, btndetail);
                Label lNom = new Label(prestataire.getNom() + " " + prestataire.getPrenom());
                lNom.getStyleClass().add("textd");
                Label lEmail = new Label(prestataire.getEmail());
                lEmail.getStyleClass().add("textd");
                Label lTel = new Label(prestataire.getTel());
                lTel.getStyleClass().add("textd");
                
                final Pane cardsPane = new StackPane();
                ImageView imageView;
                if (prestataire.getImage() == null) {

                    Image image = new Image(getClass().getResourceAsStream("img/avatar.png"));
                    imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    vbox.getChildren().addAll(imageView, lNom,lEmail,lTel, hbox);
                } else {
                    vbox.getChildren().addAll(lNom, hbox);
                }
                vbox.setPadding(new Insets(10, 20, 30, 30));
                GridAllUser.resize(5000, 1000);
                GridAllUser.add(vbox, Column, Row);

                if (Column < 2) {
                    Column++;
                } else {
                    Row++;
                    Column = 0;
                }
            }

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    private void addpositions(Prestataire prestataire) {
        if (mapView.isVisible()) {
            PrestataireService prestataireService = new PrestataireService();
            Position position = prestataireService.getPrestatairPosition(prestataire.getId());
            if (position != null) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLong(position.getLatitude(), position.getLongitude()))
                        .visible(Boolean.TRUE)
                        .title(prestataire.getNom() + " " + prestataire.getPrenom());

                Marker marker = new Marker(markerOptions);
                InfoWindowOptions myWindowOptions = new InfoWindowOptions();
                myWindowOptions.content(marker.getTitle());
                InfoWindow WilkeInfoWindow = new InfoWindow(myWindowOptions);
                WilkeInfoWindow.open(map, marker);
                map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
                    WilkeInfoWindow.open(map, marker);
                });
                map.addMarker(marker);
                map.setCenter(new LatLong(position.getLatitude(), position.getLongitude()));

            }
        }
    }
}
