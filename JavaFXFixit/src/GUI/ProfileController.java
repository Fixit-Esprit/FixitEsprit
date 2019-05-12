/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

 
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entity.Pays;
import entity.Region;
import entity.Service;
import entity.User;
import entity.Ville;
import service.ServiceUser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable; 
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent; 
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxfixit.JavaFXFixit;
import org.apache.commons.io.FileUtils;
import service.PaysService;
import service.RegionService;
import service.VilleService;
import service.ServiceService;

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
 private TextField LBnbPoint;
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
File file;
List<Service> service;
List<Pays> pays;
List<Region> region;
List<Ville> ville;
       
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

     
/****************************/
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

  

    

   
       
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
        String sql = "SELECT * FROM user";        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
             rs.next();
             setLBnom(rs.getString(4));
             setLBpnom(rs.getString(5));                      
             setLBphone(rs.getString(6));
             setLBlogin(rs.getString(7)); 
             setLBpwd(rs.getString(8));
             setLBemail(rs.getString(9));
            // setLBimg(rs.getString(10));  
            File file = new File(rs.getString(10));
            Image image = new Image(file.toURI().toString());             
            LBimage.setImage(image);
            }catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
         this.upload();
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
    public void upload(){
        LBimage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
         System.out.println("img pressed ");
         FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Open Resource File");
         file =fileChooser.showOpenDialog(new Stage());
         if(file!=null){      
 String fileName=file.getName();
String hos = ".\\src\\GUI\\img\\"; 
 System.out.println("home dir path is"+hos);
 String windPath=hos.replaceAll("\\\\", "/");                    
 System.out.println("windows path for copy is"+windPath);
 File  targetFile  = new File(hos+fileName);
 try {
 FileUtils.copyFile(file, targetFile);
             } catch (IOException ex) {
              Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
             }
             }else{              
                 System.out.println("Cancelled");
              }
     });
   } 
     
        @FXML
    private void updateuser(ActionEvent event) {
          String fileName;
             if(file!=null) 
                     fileName=".\\src\\GUI\\img\\"+file.getName(); 
               else 
                   fileName=null;
        ServiceUser srv = new ServiceUser();       
         int idville =srv.getIDVille((String) comboboxville.getValue());
         System.out.println("\n valeur de combo"+comboboxpays.getSelectionModel().getSelectedIndex());
         int idpays =comboboxpays.getSelectionModel().getSelectedIndex()+1;
         int idregion =comboboxpays.getSelectionModel().getSelectedIndex()+1;         
         User u= new User(2,LBnom.getText(),LBpnom.getText(),LBAdresse.getText(),LBlogin.getText(),LBpwd.getText(),LBphone.getText(),LBemail.getText(), fileName, 500,idpays,idregion,idville);
         srv.Updateutilisateur(u);
    
    }
       @FXML
    private void goHome(ActionEvent event) {
       
        try {
           
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Accueil.fxml"));             
           Parent root = loader.load();          
           AccueilController irc = loader.getController();          
           LBnom.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
}
