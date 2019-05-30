/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entity.BCrypt;
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
 * @author Marwa
 */
public class LoginUserController implements Initializable {

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
            User u = new User(TXFlogin.getText(), TXFpwd.getText());

            ServiceUser srv = new ServiceUser();
            rs = srv.login(u);
            if (rs == 1) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Accueil.fxml"));
                Parent root = loader.load();
                TXFlogin.getScene().getWindow().setWidth(1200);
                TXFlogin.getScene().getWindow().setHeight(700);
                TXFlogin.getScene().setRoot(root);
            } else if (rs == 2) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AccueilPrestataire.fxml"));
                Parent root = loader.load();
                TXFlogin.getScene().getWindow().setWidth(1250);
                TXFlogin.getScene().getWindow().setHeight(640);
                TXFlogin.getScene().setRoot(root);
            } else {

                message.setVisible(true);

            }
        } catch (IOException ex) {
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void InscriptionUser(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Switcher.fxml"));
            Parent root = loader.load();
            SwitcherController irc = loader.getController();
            TXFlogin.getScene().setRoot(root);

        } catch (IOException ex) {
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void ForgetPassword(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ForgetPassword.fxml"));
            Parent root = loader.load();
            ForgetPasswordController irc = loader.getController();
            TXFlogin.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private static int workload = 12;

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        System.out.println(salt);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return (hashed_password);
    }

}
