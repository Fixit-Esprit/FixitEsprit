/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import entity.Client;
import entity.Demande;
import entity.Disponiblite;
import entity.Prestataire;
import entity.Service;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.DemandeService;
import service.PrestataireService;
import service.ServiceService;

/**
 * FXML Controller class
 *
 * @author hphqlim
 */
public class DemandeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox comboboxdisponiblites;

    @FXML
    private JFXTextArea description;

    Prestataire prestataire;
    List<Disponiblite> disponiblites;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Prestataire prestataire) {

        this.prestataire = prestataire;
        System.out.println(prestataire);

        PrestataireService prestataireService = new PrestataireService();
        disponiblites = prestataireService.getDisponiblite(prestataire.getId());
        ArrayList<String> listdisponiblites = new ArrayList<String>();
        for (Disponiblite d : disponiblites) {
            listdisponiblites.add(d.getDate());
        }
        ObservableList<String> olistservice = FXCollections.observableArrayList(listdisponiblites);
        comboboxdisponiblites.setItems(olistservice);
    }

    @FXML
    private void envoyerDemande(ActionEvent event) {
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
            Demande demande;
            demande = new Demande(prestataire.getId(), client.getId(), description.getText(), null, new Date(), comboboxdisponiblites.getValue().toString());
            DemandeService demandeService = new DemandeService();
            int result = demandeService.ajouterDemande(demande);
            if (result == 1) {
                Stage stage = (Stage) comboboxdisponiblites.getScene().getWindow();
                // do what you have to do
                stage.close();
            }
        }

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
