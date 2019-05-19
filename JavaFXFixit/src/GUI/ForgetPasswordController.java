/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entity.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.input.MouseEvent;
import service.ServiceUser;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author Marwa
 */
public class ForgetPasswordController implements Initializable {
    
   @FXML
   private TextField FGemail;
   private int rs;
    @FXML
    private TextField TXFlogin;
    @FXML
    private TextField TXFpwd;
    @FXML
    private Button BTNlog;
  @FXML
    public Label message ;
    @FXML
    public Text message_error;
    @FXML
    public Text message_su;
    /**
     * Initializes the controller class.
     */
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 0
         message_error.setVisible(false);
         message_su.setVisible(false);
    } 
    
    @FXML
    private void sendPassword(ActionEvent event) throws SQLException {
     final  String emailto  =FGemail.getText();
     ServiceUser srv = new ServiceUser();              
     rs= srv.check_email(emailto);
    String PWD=srv.get_passe_Paremail(emailto);
    String login=srv.get_login_Paremail(emailto);
  
       if(rs==0){
         message_error.setVisible(true);
       }else{
        final String username = "fixit.tunis2019@gmail.com";
        final String password = "Fixit2019";

        Properties prop = new Properties();
	prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("marwa.saieb@esprit.tn"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailto)
            );
            message.setSubject("récupérer votre mot de passe ");            
            message.setText("Bonjour,"
                    + "\n\n  vous avez demandé à récupérer le mot de passe: "
                    + "\n\n  Login: "+login
                    + "\n\n  Password: "+PWD
            );

            Transport.send(message);
            message_su.setVisible(true);
            System.out.println("Done send mail");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
       }
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
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    } 
}
