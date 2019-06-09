/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.ProfileController.Dropuser;
import com.fasterxml.jackson.core.JsonParser;
import static com.google.common.net.HttpHeaders.USER_AGENT;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.*;
import entity.Annonce;
import entity.Position;
import entity.Prestataire;
import static io.jsonwebtoken.Jwts.parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import netscape.javascript.JSObject;
import com.jfoenix.controls.JFXDatePicker;
import entity.AnnonceAccepte;
import entity.Client;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import javafx.stage.Stage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import service.AnnonceService;
import service.ServiceUser;
import utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author abdelhalim.benjmila
 */
public class AccepteAnnonceController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    /**
     * Initializes the controller class.
     */
    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;
    @FXML
    private Label client;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label prixmin;
    @FXML
    private Label prixmax;
    @FXML
    private Spinner<Integer> prix;
    @FXML
    private JFXDatePicker date;

    Position position;
    LatLong myposition;
    Annonce annonce;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mapView.setKey("AIzaSyCpBZ4AjkZIoLWHnYYF5qsdQO5CTnCpcko");
        mapView.addMapInializedListener(this);
        final int initialValue = 100;
        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, initialValue);
        prix.setValueFactory(valueFactory);
        prix.setEditable(false);
    }

    public void setData(Annonce annonce) {
        this.annonce = annonce;
        client.setText(annonce.getNomclient());
        title.setText(annonce.getTitle());
        description.setText(annonce.getDescription());
        prixmin.setText(String.valueOf(annonce.getMinprix()));
        prixmax.setText(String.valueOf(annonce.getMaxprix()));
        date.setValue(LocalDate.now());
    }

    @FXML
    private void accepte() {
        Client client = new Client();
        String sql = "SELECT * FROM user";

        try (Connection conn = Utilis.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            client.setId(rs.getInt(2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (client.getId() != 0) {
            AnnonceAccepte annonceAccepte = new AnnonceAccepte(annonce.getId(), client.getId(), prix.getValue(), date.getValue().toString());
            AnnonceService annonceService = new AnnonceService();
            int res = annonceService.ajouterAnnonceAccepte(annonceAccepte);
            if (res == 1) {
                Stage stage = (Stage) mapView.getScene().getWindow();
                stage.close();
            }
        }
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
        DirectionsPane directionsPane = mapView.getDirec();
        MarkerOptions markerOptions = new MarkerOptions();
        ServiceUser serviceUser = new ServiceUser();
        position = serviceUser.getPosition(annonce.getIdclient());
        System.out.print(position);

        try {
            sendGetPosition();
        } catch (Exception ex) {
            Logger.getLogger(AccepteAnnonceController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (position != null) {
            markerOptions.position(new LatLong(position.getLatitude(), position.getLongitude()))
                    .visible(Boolean.TRUE)
                    .title(this.annonce.getNomclient());

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

            DirectionsRequest request = new DirectionsRequest(new LatLong(position.getLatitude(), position.getLongitude()), new LatLong(36.82, 10.17), TravelModes.BICYCLING);
            DirectionsService directionsService = new DirectionsService();
            directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
        }
        if (myposition != null) {
            markerOptions.position(myposition)
                    .visible(Boolean.TRUE)
                    .title("Ta position");

            Marker marker2 = new Marker(markerOptions);
            InfoWindowOptions myWindowOptions2 = new InfoWindowOptions();
            myWindowOptions2.content(marker2.getTitle());
            InfoWindow WilkeInfoWindow2 = new InfoWindow(myWindowOptions2);
            WilkeInfoWindow2.open(map, marker2);
            map.addUIEventHandler(marker2, UIEventType.click, (JSObject obj) -> {
                WilkeInfoWindow2.open(map, marker2);
            });
            map.addMarker(marker2);
            map.setCenter(myposition);
        }
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        System.out.print(results);
    }

    private void sendGetPosition() throws Exception {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader inp = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = inp.readLine(); //you get the IP as a String
            System.out.println(ip);

            String url = "http://free.ipwhois.io/json/" + ip;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            JSONParser parser = new JSONParser();
            JSONObject resultObject = (JSONObject) parser.parse(response.toString());
            Double latitude = Double.parseDouble(resultObject.get("latitude").toString());
            Double longitude = Double.parseDouble(resultObject.get("longitude").toString());
            System.out.println(latitude + " " + longitude);
            this.myposition = new LatLong(latitude.doubleValue(), longitude.doubleValue());
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccepteAnnonceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
