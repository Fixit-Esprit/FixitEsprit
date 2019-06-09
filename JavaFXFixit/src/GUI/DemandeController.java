/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import entity.Client;
import entity.Demande;
import entity.Disponiblite;
import entity.Prestataire;
import entity.Service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import service.DemandeService;
import service.PrestataireService;
import service.ServiceService;
import sun.misc.BASE64Decoder;
import utilis.Utilis;

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
    private JFXTextField title;
    @FXML
    private JFXTextArea description;

    @FXML
    private ImageView imageannonce;
    @FXML
    private Text errordisponiblite;
    
    Prestataire prestataire;
    List<Disponiblite> disponiblites;
    String imageEncoder;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setData(Prestataire prestataire) {
        
        errordisponiblite.setVisible(false);
        
        this.prestataire = prestataire;
        System.out.println(prestataire);
        
        RequiredFieldValidator validatortitle = new RequiredFieldValidator();
        validatortitle.setMessage("tilte est obligatoire");
        
        title.getValidators().add(validatortitle);
        title.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                title.validate();
            }
        });
        
        RequiredFieldValidator validatordescription = new RequiredFieldValidator();
        validatordescription.setMessage("discription est obligatoire");
        
        description.getValidators().add(validatordescription);
        description.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                description.validate();
            }
        });
        
        PrestataireService prestataireService = new PrestataireService();
        disponiblites = prestataireService.getDisponiblite(prestataire.getId());
        ArrayList<String> listdisponiblites = new ArrayList<String>();
        for (Disponiblite d : disponiblites) {
            listdisponiblites.add(d.getDate());
        }
        ObservableList<String> olistservice = FXCollections.observableArrayList(listdisponiblites);
        comboboxdisponiblites.setItems(olistservice);
        
        comboboxdisponiblites.valueProperty().addListener((obs, oldValue, newValue)
                -> errordisponiblite.setVisible(false));
    }
    
    @FXML
    private void envoyerDemande(ActionEvent event) {
        if (description.validate() && title.validate() && comboboxdisponiblites.getValue() != null) {
            
            Client client = new Client();
            String sql = "SELECT * FROM user";
            
            try (Connection conn = Utilis.connect();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {
                rs.next();
                client.setId(rs.getInt(2));
                // setLBimg(rs.getString(10));  
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (client.getId() != 0) {
                Demande demande;
                demande = new Demande(prestataire.getId(), client.getId(),title.getText(), description.getText(), imageEncoder, new Date(), comboboxdisponiblites.getValue().toString());
                DemandeService demandeService = new DemandeService();
                int result = demandeService.ajouterDemande(demande);
                if (result == 1) {
                    Stage stage = (Stage) comboboxdisponiblites.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                }
            }
        } else {
            title.validate();
            errordisponiblite.setVisible(true);
        }
    }
    
    public void upload(MouseEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Image Files", new String[]{"*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"}), new FileChooser.ExtensionFilter("JPG", new String[]{"*.jpg"}), new FileChooser.ExtensionFilter("JPEG", new String[]{"*.jpeg"}), new FileChooser.ExtensionFilter("BMP", new String[]{"*.bmp"}), new FileChooser.ExtensionFilter("PNG", new String[]{"*.png"}), new FileChooser.ExtensionFilter("GIF", new String[]{"*.gif"})});
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);
        
        try {
            
            byte[] imageByte;
            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytes);
            fis.close();
            imageEncoder = Base64.getEncoder().encodeToString(bytes);
            
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageEncoder);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            
            imageannonce.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(AnnonceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
