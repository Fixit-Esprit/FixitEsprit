/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXComboBox;
import entity.User;
import service.ServiceUser;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import service.PaysService;
import service.RegionService;
import service.ServiceService;
import service.VilleService;

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
 private ImageView INimage;
   File file;
    @FXML
    private JFXComboBox comboboxpays;

    @FXML
    private JFXComboBox comboboxregion;

    @FXML
    private JFXComboBox comboboxville;


    

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

    
    
    
    /**
     * Initializes the controller class.
     */
    public void setINimage(ImageView INimage) {
        this.INimage = INimage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.upload();
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
     
   
   
    @FXML
    private void adduser(ActionEvent event) {
       
        User u= new User(INnom.getText(),INpnom.getText(),1,INlogin.getText(),INpwd.getText(),INphone.getText(),INemail.getText(), file.getName(), 500);
        ServiceUser srv = new ServiceUser();
        srv.ajouterutilisateur(u);
    
    }
    
 
}
