/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entity.BCrypt;
import entity.Pays;
import entity.Region;
import entity.Service;
import entity.User;
import entity.Ville;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import service.ServiceUser;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Label;
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
import service.RegionService;
import service.VilleService;
import service.ServiceService;
import sun.misc.BASE64Decoder;
import utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author Marwa
 */
public class ProfileController implements Initializable {

    @FXML
    private TextField LBnom;
    @FXML
    private TextField LBpnom;
    @FXML
    private TextField LBlogin;
    @FXML
    private TextField LBpwd;
    @FXML
    private TextField LBphone;
    @FXML
    private TextField LBemail;
    @FXML
    private TextField LBAdresse;
    @FXML
    private ImageView LBimage;
    @FXML
    private ImageView home;
    @FXML
    private Label LBnbPoint;
    @FXML
    private TextField LBcin;

    @FXML
    private JFXComboBox comboboxpays;
    @FXML
    private JFXComboBox comboboxregion;
    @FXML
    private JFXComboBox comboboxville;
    @FXML
    private JFXComboBox comboboxservice;
    @FXML
    private JFXTextField motcle;
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
    public Text message_INemail_exist;
    @FXML
    public Text message_INlogin_exist;
    @FXML
    public Text message_INphoto;

    File file;
    List<Service> service;
    List<Pays> pays;
    Map<Integer, String> paysMap;
    List<Region> region;
    Map<Integer, String> regionMap;
    List<Ville> ville;
    Map<Integer, String> villeMap;
    String imageEncoder;
    String imageByteSans;
    byte[] imageByte;
    int idpays, idregion, idville;

    public void setLBnom(String nom) {
        this.LBnom.setText(nom);
    }

    public void setLBpnom(String prenom) {
        this.LBpnom.setText(prenom);
    }

    public void setLBlogin(String LBlogin) {
        this.LBlogin.setText(LBlogin);
    }

    public void setLBpwd(String LBpwd) {
        this.LBpwd.setText(LBpwd);
    }

    public void setLBphone(String LBphone) {
        this.LBphone.setText(LBphone);
    }

    public void setLBemail(String LBemail) {
        this.LBemail.setText(LBemail);
    }

    public void setLBAdresse(String LBAdresse) {
        this.LBAdresse.setText(LBAdresse);
    }

    public void setLBcin(String LBcin) {
        this.LBcin.setText(LBcin);
    }

    public void setLBnbPoint(String LBnbPoint) {
        this.LBnbPoint.setText(LBnbPoint);
    }

    /**
     * *************************
     */
    public TextField getLBnom() {
        return LBnom;
    }

    public TextField getLBpnom() {
        return LBpnom;
    }

    public TextField getLBlogin() {
        return LBlogin;
    }

    public TextField getLBpwd() {
        return LBpwd;
    }

    public TextField getLBphone() {
        return LBphone;
    }

    public TextField getLBemail() {
        return LBemail;
    }

    public TextField getLBAdresse() {
        return LBAdresse;
    }

    public ImageView getLBimage() {
        return LBimage;
    }

    public TextField getLBcin() {
        return LBcin;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        message_INnom.setVisible(false);
        message_INpnom.setVisible(false);
        message_INlogin.setVisible(false);
        message_INpwd.setVisible(false);
        message_INphone.setVisible(false);
        message_INemail.setVisible(false);
        message_pays.setVisible(false);
        message_region.setVisible(false);
        message_ville.setVisible(false);
        message_INcin.setVisible(false);
        message_INemail_exist.setVisible(false);
        message_INlogin_exist.setVisible(false);
        message_INphoto.setVisible(false);
        String sql = "SELECT * FROM user";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            setLBnom(rs.getString(4));
            setLBpnom(rs.getString(5));
            setLBphone(rs.getString(6));
            setLBlogin(rs.getString(7));
            //setLBpwd(rs.getString(8));
            setLBemail(rs.getString(9));
            setLBcin(rs.getString(13));
            setLBAdresse(rs.getString(17));
            setLBnbPoint("Vous avez " + rs.getInt(11) + " point !");
            BASE64Decoder decoder = new BASE64Decoder();
            imageByteSans=rs.getString(10);
            System.out.println("here :: "+rs.getString(10));
            try {
                imageByte = decoder.decodeBuffer(rs.getString(10));
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                BufferedImage bufferedImage = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                LBimage.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

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

            LBimage.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private static int workload = 12;

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        System.out.println(salt);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return (hashed_password);
    }

    @FXML
    private void updateuser(ActionEvent event) {
        if (validation() == 1) {
            ServiceUser srv = new ServiceUser();
            System.out.println("id user" + getIdUser());
            if(imageEncoder==null){
            imageEncoder=imageByteSans;
            }
            User u = new User(getIdUser(), LBnom.getText(), LBpnom.getText(), LBAdresse.getText(), LBlogin.getText(), hashPassword(LBpwd.getText()), LBphone.getText(), LBemail.getText(), imageEncoder, 500, idpays, idregion, idville, LBcin.getText());
            srv.Updateutilisateur(u);
        }
    }

    @FXML
    private void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Accueil.fxml"));
            Parent root = loader.load();
            AccueilController irc = loader.getController();
            LBnom.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void logout() {
        Dropuser();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/LoginUser.fxml"));
            Parent root = loader.load();
            LoginUserController irc = loader.getController();
            LBnom.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Dropuser() {
        // SQLite connection string
        String url = "jdbc:sqlite:./db/user.db";
        // SQL statement for creating a new table

        String sql = "DROP TABLE IF EXISTS user ";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getIdUser() {
        String sql = "SELECT * FROM user";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            return rs.getInt(2);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int paiement() {
        return 1;
    }

    private int validation() {
        ServiceUser srv = new ServiceUser();
        ControleSaisie Cs = new ControleSaisie();
        if (LBlogin.getText() == null || LBlogin.getText().isEmpty()) {
            message_INlogin.setVisible(true);
            return 0;

        } else {
            message_INlogin.setVisible(false);

        }
        /* if (srv.check_login(LBlogin.getText())==0)
        {
             
         message_INlogin_exist.setVisible(false); 
           
        }else{
        
          
             message_INlogin_exist.setVisible(true);
             return 0;
         
        }*/

        if (LBpwd.getText() == null || LBpwd.getText().isEmpty()) {

            message_INpwd.setVisible(true);
            return 0;
        } else {
            message_INpwd.setVisible(false);
        }
        if (LBpnom.getText() == null || LBpnom.getText().isEmpty()) {

            message_INpnom.setVisible(true);
            return 0;
        } else {
            message_INpnom.setVisible(false);
        }
        if (LBnom.getText() == null || LBnom.getText().isEmpty()) {
            message_INnom.setVisible(true);
            return 0;
        } else {
            message_INnom.setVisible(false);
        }
        if (LBemail.getText() == null || LBemail.getText().isEmpty() || !Cs.validemail(LBemail.getText())) {
            message_INemail.setVisible(true);
            return 0;
        } else {

            message_INemail.setVisible(false);

        }
        /* if (srv.check_email(LBemail.getText())==0)
        {
             
         message_INemail_exist.setVisible(false); 
           
        }else{
        
          
             message_INemail_exist.setVisible(true);
             return 0;
         
        }*/

        if (LBphone.getText() == null || LBphone.getText().isEmpty() || !Cs.isTel(LBphone.getText())) {
            message_INphone.setVisible(true);
            return 0;
        } else {
            message_INphone.setVisible(false);

        }
        if (LBcin.getText() == null || LBcin.getText().isEmpty() || !Cs.iscin(LBcin.getText())) {
            message_INcin.setVisible(true);
            return 0;
        } else {
            message_INcin.setVisible(false);

        }
     

        return 1;

    }
}
