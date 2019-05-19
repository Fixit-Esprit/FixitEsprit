/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXComboBox;
import entity.Pays;
import entity.Region;
import entity.Service;
import entity.User;
import entity.Ville;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import service.ControleSaisie;
import service.PaysService;
import service.RegionService;
import service.ServiceService;
import service.ServiceUser;
import service.VilleService;

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
List<Region> region;
List<Ville> ville;

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
               this.upload();
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
    }    
    public void upload(){
        INimage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
         System.out.println("img cliqued");
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
     
   public void loadinfo(){
    ServiceService ServiceService = new ServiceService();
        service = ServiceService.getAllService();
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
   
    @FXML
    private void adduser(ActionEvent event) {
        if(validation()==1){
       String fileName;
       String hos = ".\\src\\GUI\\img\\";
             if(file!=null)                  
                fileName=hos+file.getName(); 
               else 
                fileName=null;
         ServiceUser srv = new ServiceUser();       
         int idville =srv.getIDVille((String) comboboxville.getValue());
       
          int idpays =comboboxpays.getSelectionModel().getSelectedIndex()+1;
        
         int idregion =comboboxpays.getSelectionModel().getSelectedIndex()+1;
           System.out.println("\n valeur de combo"+idville);   
          
        // User u= new User(INnom.getText(),INpnom.getText(),INAdresse.getText(),INlogin.getText(),INpwd.getText(),INphone.getText(),INemail.getText(), fileName, 500,idpays,idregion,idville);
        // srv.ajouterutilisateur(u);
         }
    
    }
   private int validation() {
          int var ;
   ControleSaisie Cs =new ControleSaisie();
         if (INlogin.getText() == null ||  INlogin.getText().isEmpty())
        {
             message_INlogin.setVisible(true);
             return 0;
                    
        }else {
              message_INlogin.setVisible(false);
           
         }
         if (INpwd.getText() == null ||  INpwd.getText().isEmpty())
        {
             
             message_INpwd.setVisible(true);
             return 0;
         }else {
              message_INpwd.setVisible(false);
         }
         if  (INpnom.getText() == null ||  INpnom.getText().isEmpty())
        {
            
             message_INpnom.setVisible(true);
             return 0;
        }else {
              message_INpnom.setVisible(false);
         }
         if (INnom.getText() == null ||  INnom.getText().isEmpty())
        {
             message_INnom.setVisible(true);
          return 0;
        }else {
              message_INnom.setVisible(false);
         }
         if (INphone.getText() == null || INphone.getText().isEmpty() || !Cs.isTel(INphone.getText()))
        {
             message_INphone.setVisible(true);
              return 0;
        }else {
              message_INphone.setVisible(false);
              
         }
        if (INemail.getText() == null ||  INemail.getText().isEmpty()|| !Cs.validemail(INemail.getText()))
        {
             message_INemail.setVisible(true);
             return 0;
        }else{
         
         message_INemail.setVisible(false); 
         
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
