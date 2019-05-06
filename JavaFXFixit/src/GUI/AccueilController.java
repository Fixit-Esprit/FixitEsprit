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
        LatLong joeSmithLocation = new LatLong(47.6197, -122.3231);
        LatLong joshAndersonLocation = new LatLong(47.6297, -122.3431);
        LatLong bobUnderwoodLocation = new LatLong(47.6397, -122.3031);
        LatLong tomChoiceLocation = new LatLong(47.6497, -122.3325);
        LatLong fredWilkieLocation = new LatLong(47.6597, -122.3357);

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(MapTypeIdEnum.HYBRID)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(12);

        map = mapView.createMap(mapOptions);

        //Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(joeSmithLocation);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(joshAndersonLocation);
        markerOptions2.title("halim");
        markerOptions2.visible(true);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(bobUnderwoodLocation);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(tomChoiceLocation);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(fredWilkieLocation);

        Marker joeSmithMarker = new Marker(markerOptions1);
        Marker joshAndersonMarker = new Marker(markerOptions2);
        Marker bobUnderwoodMarker = new Marker(markerOptions3);
        Marker tomChoiceMarker = new Marker(markerOptions4);
        Marker fredWilkieMarker = new Marker(markerOptions5);

        map.addMarker(joeSmithMarker);
        map.addMarker(joshAndersonMarker);
        map.addMarker(bobUnderwoodMarker);
        map.addMarker(tomChoiceMarker);
        map.addMarker(fredWilkieMarker);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<img src=\"IMG.jpg\" alt=Smiley face height=42 width=42>"
                + "Current Location: Safeway<br>"
                + "ETA: 45 minutes");

        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
        fredWilkeInfoWindow.open(map, fredWilkieMarker);
        fredWilkeInfoWindow.open(map, bobUnderwoodMarker);
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(new LatLong(47.6, -122.3))
                .visible(Boolean.TRUE)
                .title("My Marker");

        Marker marker = new Marker(markerOptions);
        InfoWindowOptions myWindowOptions = new InfoWindowOptions();
        myWindowOptions.content(marker.getTitle());
        InfoWindow WilkeInfoWindow = new InfoWindow(myWindowOptions);
        map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
            WilkeInfoWindow.open(map, marker);
        });
        map.addMarker(marker);

    }

    @FXML
    private void recherchePrestataireByName(ActionEvent event) {
        GridAllUser.getChildren().clear();
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
                VBox vbox = new VBox(5);
                vbox.setPrefSize(300, 300);
                vbox.setAlignment(Pos.CENTER);
                JFXButton btn = new JFXButton("Demande");
                btn.getStyleClass().add("recherche");

                /*stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btn.getScene().getWindow());*/
                btn.setOnAction((ActionEvent e) -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DetailPrestataire.fxml"));
                    Parent root;
                    try {
                        root = loader.load();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 800, 600));
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

                Button btn2 = new Button("2");
                Label lNom = new Label(prestataire.getNom() + " " + prestataire.getPrenom());
                final Pane cardsPane = new StackPane();
                ImageView imageView;
                if (prestataire.getImage() == null) {

                    Image image = new Image(getClass().getResourceAsStream("img/avatar.png"));
                    imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    vbox.getChildren().addAll(imageView, lNom, btn);
                } else {
                    vbox.getChildren().addAll(btn, btn2, lNom);
                }
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
                VBox vbox = new VBox(5);
                vbox.setPrefSize(300, 300);
                vbox.setAlignment(Pos.CENTER);
                Button btn = new Button("Demande");
                Button btn2 = new Button("2");
                Label lNom = new Label(prestataire.getNom() + " " + prestataire.getPrenom());
                final Pane cardsPane = new StackPane();
                ImageView imageView;
                if (prestataire.getImage() == null) {

                    Image image = new Image(getClass().getResourceAsStream("img/avatar.png"));
                    imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    vbox.getChildren().addAll(imageView, lNom, btn);
                } else {
                    vbox.getChildren().addAll(btn, btn2, lNom);
                }

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
}
