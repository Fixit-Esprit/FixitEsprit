/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

 
import com.jfoenix.controls.JFXComboBox;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entity.BCrypt;
import entity.Pays;
import entity.Prestataire;
import entity.Region;
import entity.Service;
import entity.User;
import entity.Ville;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import service.ControleSaisie;
import service.PaysService;
import service.PrestataireService;
import service.RegionService;
import service.ServiceService;
import service.ServiceUser;
import service.VilleService;
import sun.misc.BASE64Decoder;
import utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author Marwa
 */
public class InscriptionPController implements Initializable {

    @FXML
    private TextField INnom;
    @FXML
    private TextField INpnom;
    @FXML
    private TextField INlogin;
    @FXML
    private TextField INpwd;
    @FXML
    private TextField INphone;
    @FXML
    private TextField INemail;
    @FXML
    private TextField INAdresse;
     @FXML
    private TextField INcin;

    @FXML
    private ImageView INimage;
    @FXML
    private JFXComboBox comboboxpays;
    @FXML
    private JFXComboBox comboboxregion;
    @FXML
    private JFXComboBox comboboxville;
    @FXML
    private JFXComboBox comboboxservice;
    @FXML
    public Text message_INnom;
    @FXML
    public Text message_INpnom;
    @FXML
    public Text message_INlogin;
    @FXML
    public Text message_INpwd;
    @FXML
    public Text message_INphone;
    @FXML
    public Text message_INemail;
    @FXML
    public Text message_INcin;
    @FXML
    public Text message_pays;
    @FXML
    public Text message_region;
    @FXML
    public Text message_ville;
    @FXML
    public Text message_secteur;
    File file;
    List<Service> service;
    List<Pays> pays;
    Map<Integer, String> paysMap;
    List<Region> region;
    Map<Integer, String> regionMap;
    List<Ville> ville;
    Map<Integer, String> villeMap;
    
    String imageEncoder;
    int idpays, idregion, idville;

    public TextField getINnom() {
        return INnom;
    }

    public TextField getINpnom() {
        return INpnom;
    }

    public TextField getINlogin() {
        return INlogin;
    }

    public TextField getINpwd() {
        return INpwd;
    }

    public TextField getINphone() {
        return INphone;
    }

    public TextField getINemail() {
        return INemail;
    }

    public TextField getINAdresse() {
        return INAdresse;
    }

    public ImageView getINimage() {
        return INimage;
    }

    public JFXComboBox getComboboxpays() {
        return comboboxpays;
    }

    public JFXComboBox getComboboxregion() {
        return comboboxregion;
    }

    public JFXComboBox getComboboxville() {
        return comboboxville;
    }

    public JFXComboBox getComboboxservice() {
        return comboboxservice;
    }

    public File getFile() {
        return file;
    }

    public void setINnom(TextField INnom) {
        this.INnom = INnom;
    }

    public void setINpnom(TextField INpnom) {
        this.INpnom = INpnom;
    }

    public void setINlogin(TextField INlogin) {
        this.INlogin = INlogin;
    }

    public void setINpwd(TextField INpwd) {
        this.INpwd = INpwd;
    }

    public void setINphone(TextField INphone) {
        this.INphone = INphone;
    }

    public void setINemail(TextField INemail) {
        this.INemail = INemail;
    }

    public void setINAdresse(TextField INAdresse) {
        this.INAdresse = INAdresse;
    }

    public void setComboboxpays(JFXComboBox comboboxpays) {
        this.comboboxpays = comboboxpays;
    }

    public void setComboboxregion(JFXComboBox comboboxregion) {
        this.comboboxregion = comboboxregion;
    }

    public void setComboboxville(JFXComboBox comboboxville) {
        this.comboboxville = comboboxville;
    }

    public void setComboboxservice(JFXComboBox comboboxservice) {
        this.comboboxservice = comboboxservice;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

    public void setPays(List<Pays> pays) {
        this.pays = pays;
    }

    public void setRegion(List<Region> region) {
        this.region = region;
    }

    public void setVille(List<Ville> ville) {
        this.ville = ville;
    }

    public void setINimage(ImageView INimage) {
        this.INimage = INimage;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.loadinfo();
        message_INnom.setVisible(false);
        message_INpnom.setVisible(false);
        message_INlogin.setVisible(false);
        message_INpwd.setVisible(false);
        message_INphone.setVisible(false);
        message_INemail.setVisible(false);
        message_pays.setVisible(false);
        message_region.setVisible(false);
        message_ville.setVisible(false);
        message_secteur.setVisible(false);
         message_INcin.setVisible(false);
    }
    private static int workload = 12;
    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        System.out.println(salt);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return (hashed_password);
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

            INimage.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadinfo() {
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

        paysMap = new HashMap<>();
        for (Pays p : pays) {
            paysMap.put(p.getId(), p.getNom());
        }

        ObservableList<String> olist = FXCollections.observableArrayList(new TreeSet(paysMap.values()));

        comboboxpays.setItems(olist);

        comboboxpays.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                RegionService regionservice = new RegionService();
                region = regionservice.getRegionByPays(Utilis.getKeys(paysMap, newValue));
                idpays = Utilis.getKeys(paysMap, newValue);
                region.toString();

                regionMap = new HashMap<>();
                for (Region r : region) {
                    regionMap.put(r.getId(), r.getNom());
                }

                ObservableList<String> olistregion = FXCollections.observableArrayList(new TreeSet(regionMap.values()));
                comboboxregion.setItems(olistregion);
            }

        });
        comboboxpays.getSelectionModel().select(208);

        comboboxregion.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                VilleService villeService = new VilleService();
                ville = villeService.getVilleByRegion(Utilis.getKeys(regionMap, newValue));
                idregion = Utilis.getKeys(regionMap, newValue);
                ville.toString();
                villeMap = new HashMap<>();

                for (Ville v : ville) {
                    villeMap.put(v.getId(), v.getNom());
                }

                ObservableList<String> olistregion = FXCollections.observableArrayList(new TreeSet(villeMap.values()));
                comboboxville.setItems(olistregion);
            }
        });
        comboboxville.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                idville = Utilis.getKeys(villeMap, newValue);

            }
        });
    }

    @FXML
    private void adduser(ActionEvent event) {
        if (validation() == 1) {

            ServiceUser srv = new ServiceUser();
            PrestataireService prest = new PrestataireService();

            int idregion = comboboxpays.getSelectionModel().getSelectedIndex() + 1;
            System.out.println("\n valeur de combo" + idville);
            /**
             * *************************SMS*******************************
             */
            Random r = new Random();
            //int verifCode = r.nextInt(9999);
            int verifCode = r.nextInt((9999 - 1000) + 1) + 1000;
            String ACCOUNT_SID = "AC53695a0810423ac99cc123e2d09a000d";
            String AUTH_TOKEN = "e82e3599383dd1019008b436610c8a9e";
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String verifMessage = "Votre code de vérification est " + verifCode;
            Message message = Message.creator(new PhoneNumber("+216" + INphone.getText()),
                    new PhoneNumber("+12568134657"),
                    verifMessage).create();
            System.out.println(message.getSid());

            /**
             * ************************SMS********************************
             */
            String service = (String) comboboxservice.getValue();
            int adresse_id = prest.ajouteadresse(idpays, idregion, idville, INAdresse.getText());
            Prestataire p = new Prestataire(adresse_id, INnom.getText(), INpnom.getText(), INemail.getText(), INlogin.getText(), INphone.getText(), hashPassword(INpwd.getText()) , imageEncoder, 500, verifCode, service,INcin.getText());
            int res = prest.ajoutePrestataire(p);
            if (res == 1) {
                // load page verification de code sms en cours 
                System.out.println(" load page verification de code sms en cours ...");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/InscriptionE2.fxml"));
                    Parent root = loader.load();
                    InscriptionE2Controller irc = loader.getController();
                    INnom.getScene().setRoot(root);

                } catch (IOException ex) {
                    Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private int validation() {
         
        ControleSaisie Cs = new ControleSaisie();
        if (INlogin.getText() == null || INlogin.getText().isEmpty()) {
            message_INlogin.setVisible(true);
            return 0;

        } else {
            message_INlogin.setVisible(false);

        }
        if (INpwd.getText() == null || INpwd.getText().isEmpty()) {

            message_INpwd.setVisible(true);
            return 0;
        } else {
            message_INpwd.setVisible(false);
        }
        if (INpnom.getText() == null || INpnom.getText().isEmpty()) {

            message_INpnom.setVisible(true);
            return 0;
        } else {
            message_INpnom.setVisible(false);
        }
        if (INnom.getText() == null || INnom.getText().isEmpty()) {
            message_INnom.setVisible(true);
            return 0;
        } else {
            message_INnom.setVisible(false);
        }
        if (INphone.getText() == null || INphone.getText().isEmpty() || !Cs.isTel(INphone.getText())) {
            message_INphone.setVisible(true);
            return 0;
        } else {
            message_INphone.setVisible(false);

        }
        if (INemail.getText() == null || INemail.getText().isEmpty() || !Cs.validemail(INemail.getText())) {
            message_INemail.setVisible(true);
            return 0;
        } else {

            message_INemail.setVisible(false);

        }
        if (INcin.getText() == null || INcin.getText().isEmpty() || !Cs.iscin(INcin.getText())) {
            message_INcin.setVisible(true);
            return 0;
        } else {
            message_INcin.setVisible(false);

        }
        return 1;

    }

    @FXML
    private void retour() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Switcher.fxml"));
            Parent root = loader.load();
            SwitcherController irc = loader.getController();
            INnom.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
