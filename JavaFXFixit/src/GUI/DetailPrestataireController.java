/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import entity.Position;
import entity.Prestataire;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import netscape.javascript.JSObject;
import service.PrestataireService;

/**
 * FXML Controller class
 *
 * @author abdelhalim.benjmila
 */
public class DetailPrestataireController implements Initializable, MapComponentInitializedListener {

    /**
     * Initializes the controller class.
     */
    @FXML
    private GoogleMapView mapView2;

    private GoogleMap map2;

    @FXML
    private ImageView imageprestataire;

    @FXML
    private Label nometprenom;

    @FXML
    private Label service;

    @FXML
    private Label adresse;

    Position position;
    Prestataire prestataire;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView2.setKey("AIzaSyCpBZ4AjkZIoLWHnYYF5qsdQO5CTnCpcko");
        mapView2.addMapInializedListener(this);

    }

    public void setData(Prestataire prestataire) {
        
        this.prestataire = prestataire;
        System.out.println(prestataire);
        nometprenom.setText(prestataire.getNom() + " " + prestataire.getPrenom());
        service.setText(prestataire.getService());
        if (prestataire.getImage() == null) {
            Image image = new Image(getClass().getResourceAsStream("img/avatar.png"));
            imageprestataire.setImage(image);
            imageprestataire.setFitHeight(100);
            imageprestataire.setFitWidth(100);
        }
        PrestataireService prestataireService = new PrestataireService();
        position = prestataireService.getPrestatairPosition(prestataire.getId());
        System.out.println(position);
        adresse.setText(position.getPays() + " -> " + position.getRegion() + " -> " + position.getVille());
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

        map2 = mapView2.createMap(mapOptions);

        MarkerOptions markerOptions = new MarkerOptions();
        if (position != null) {
            markerOptions.position(new LatLong(position.getLatitude(), position.getLongitude()))
                    .visible(Boolean.TRUE)
                    .title(this.prestataire.getNom()+" "+this.prestataire.getPrenom());

            Marker marker = new Marker(markerOptions);
            InfoWindowOptions myWindowOptions = new InfoWindowOptions();
            myWindowOptions.content(marker.getTitle());
            InfoWindow WilkeInfoWindow = new InfoWindow(myWindowOptions);
            WilkeInfoWindow.open(map2, marker);
            map2.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
                WilkeInfoWindow.open(map2, marker);
            });
            map2.addMarker(marker);
            map2.setCenter(new LatLong(position.getLatitude(), position.getLongitude()));

        }

    }
}
