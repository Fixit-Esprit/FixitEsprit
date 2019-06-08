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
import entity.Annonce;
import entity.Client;
import entity.Demande;
import entity.Service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import service.AnnonceService;
import service.DemandeService;
import service.ServiceService;
import sun.misc.BASE64Decoder;
import utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author hphqlim
 */
public class AnnonceController implements Initializable {

    @FXML
    private JFXComboBox comboboxservice;
    @FXML
    private Spinner<Integer> minprix;
    @FXML
    private Spinner<Integer> maxprix;
    @FXML
    private ImageView imageannonce;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXTextField title;
    @FXML
    private Text errorsecteur;
    @FXML
    private Text errorprix;

    List<Service> service;
    File file;
    String imageEncoder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        errorprix.setVisible(false);
        errorsecteur.setVisible(false);

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

        final int initialValue = 100;
        SpinnerValueFactory<Integer> valueFactoryMin
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, initialValue);
        SpinnerValueFactory<Integer> valueFactoryMax
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, initialValue);
        minprix.setValueFactory(valueFactoryMin);
        maxprix.setValueFactory(valueFactoryMax);
        minprix.setEditable(false);
        minprix.setEditable(false);

        minprix.valueProperty().addListener((obs, oldValue, newValue)
                -> {
            if (newValue > maxprix.getValue()) {
                errorprix.setVisible(true);
            } else {
                errorprix.setVisible(false);
            }
        });
        maxprix.valueProperty().addListener((obs, oldValue, newValue)
                -> {
            if (newValue < minprix.getValue()) {
                errorprix.setVisible(true);
            } else {
                errorprix.setVisible(false);
            }
        });

        ServiceService serviceService = new ServiceService();
        service = serviceService.getAllService();
        ArrayList<String> listservise = new ArrayList<String>();
        for (Service s : service) {
            listservise.add(s.getDescription());
        }
        ObservableList<String> olistservice = FXCollections.observableArrayList(listservise);
        comboboxservice.setItems(olistservice);
        comboboxservice.valueProperty().addListener((obs, oldValue, newValue)
                -> errorsecteur.setVisible(false));
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

    @FXML
    private void annoncer(ActionEvent event) {
        if (description.validate() && title.validate() && comboboxservice.getValue() != null && minprix.getValue() <= maxprix.getValue()) {
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
                int idService = (int) service.stream().filter(r -> r.getDescription().equals(comboboxservice.getValue())).mapToInt(r -> r.getId()).average().getAsDouble();

                Annonce annonce;
                annonce = new Annonce(client.getId(), idService, new Date(),title.getText(), description.getText(), imageEncoder, minprix.getValue(), maxprix.getValue());
                AnnonceService annonceService = new AnnonceService();
                int result = annonceService.ajouterAnnonce(annonce);
                if (result == 1) {
                    Stage stage = (Stage) comboboxservice.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                }
            }

        } else {
            title.validate();
            if (comboboxservice.getValue() == null) {
                errorsecteur.setVisible(true);
            }
            if (minprix.getValue() > maxprix.getValue()) {
                errorprix.setVisible(true);
            }
        }
    }

}
