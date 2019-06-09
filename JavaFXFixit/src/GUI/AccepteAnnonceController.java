/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.ProfileController.Dropuser;
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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import netscape.javascript.JSObject;

/**
 * FXML Controller class
 *
 * @author abdelhalim.benjmila
 */
public class AccepteAnnonceController implements Initializable, MapComponentInitializedListener {

    /**
     * Initializes the controller class.
     */
    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    Position position;
    Prestataire prestataire;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mapView.setKey("AIzaSyCpBZ4AjkZIoLWHnYYF5qsdQO5CTnCpcko");
        mapView.addMapInializedListener(this);
    }

    @FXML
    private void accepte() {

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

        MarkerOptions markerOptions = new MarkerOptions();
        if (position != null) {
            markerOptions.position(new LatLong(position.getLatitude(), position.getLongitude()))
                    .visible(Boolean.TRUE)
                    .title(this.prestataire.getNom() + " " + this.prestataire.getPrenom());

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
