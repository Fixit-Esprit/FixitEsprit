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
import service.ServiceUser;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import service.VilleService;
import service.ServiceService;
import entity.BCrypt;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;
/**
 * FXML Controller class
 *
 * @author EXTHONE-marwa
 */
public class InscriptionController implements Initializable {
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

     private static int workload = 12;

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        System.out.println(salt);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return (hashed_password);
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
         message_INcin.setVisible(false);
         
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
          /***************************SMS********************************/
            Random r = new Random();
            int verifCode = r.nextInt(9999);
            String ACCOUNT_SID = "AC53695a0810423ac99cc123e2d09a000d";
            String AUTH_TOKEN = "e82e3599383dd1019008b436610c8a9e";
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String verifMessage = "Votre code de vérification est " + verifCode;
            Message message = Message.creator(new PhoneNumber("+216"+INphone.getText()),
            new PhoneNumber("+12568134657"), 
            verifMessage).create();
            System.out.println(message.getSid());
             
        /**************************SMS*********************************/
         User u= new User(INnom.getText(),INpnom.getText(),INAdresse.getText(),INlogin.getText(),hashPassword(INpwd.getText()),INphone.getText(),INemail.getText(), fileName, 500,idpays,idregion,idville,INcin.getText(),verifCode);
        int res= srv.ajouterutilisateur(u);
        if(res==1){
           // load page verification de code sms en cours 
        } 
        }
    
    }
   private int validation() {
         
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
          if (INemail.getText() == null ||  INemail.getText().isEmpty()|| !Cs.validemail(INemail.getText()))
        {
             message_INemail.setVisible(true);
             return 0;
        }else{
         
         message_INemail.setVisible(false); 
         
        }
         if (INphone.getText() == null || INphone.getText().isEmpty() || !Cs.isTel(INphone.getText()))
        {
             message_INphone.setVisible(true);
              return 0;
        }else {
              message_INphone.setVisible(false);
              
         }
         if (INcin.getText() == null || INcin.getText().isEmpty() || !Cs.iscin(INcin.getText()))
        {
             message_INcin.setVisible(true);
              return 0;
        }else {
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
