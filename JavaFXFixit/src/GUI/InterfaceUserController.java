/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entity.User;
import service.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author bhk
 */
public class InterfaceUserController implements Initializable {
    private int rs;
    @FXML
    private TextField TXFlogin;
    @FXML
    private TextField TXFpwd;
    @FXML
    private Button BTNlog;
    @FXML
    private Label inc;
    @FXML
    public Label message;

    /**
     * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        message.setVisible(false);
    }    

    @FXML
    private void LoginUser(ActionEvent event) {
            try {
            User u= new User( TXFlogin.getText(), TXFpwd.getText());
            
            ServiceUser srv = new ServiceUser();              
           rs= srv.login(u);         
             if(rs==1){      
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Profile.fxml"));             
           Parent root = loader.load();          
           ProfileController irc = loader.getController();
           irc.setLBnom(TXFlogin.getText()); 
           TXFlogin.getScene().setRoot(root);
            }else if(rs==2){      
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Accueil.fxml"));             
           Parent root = loader.load();                    
           TXFlogin.getScene().setRoot(root);
            }else{
              
               message.setVisible(true);

            }
        } catch (IOException ex) {
            Logger.getLogger(InterfaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
     @FXML    
  private void InscriptionUser(MouseEvent  event) {
            try {                 
                   
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Inscription.fxml"));             
           Parent root = loader.load();          
           InscriptionController irc = loader.getController();           
           TXFlogin.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(InterfaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }  
    
}
