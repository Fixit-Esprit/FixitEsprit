/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxfixit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author hphqlim
 */
public class JavaFXFixit extends Application {

    @Override
    public void start(Stage primaryStage) {
try {
             createNewuser();
             String sql = "SELECT COUNT(*) as nb  FROM user";        
             try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
           rs.next();
           int count = rs.getInt(1);
           System.out.println("nbr de row dans sqllit "+count);
           if(count!=0){               
           Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProfileUser.fxml"));                
           
            Scene scene = new Scene(root);                     
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("nom user " +rs.getString(4));
           }else{
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/LoginUser.fxml"));       
            Scene scene = new Scene(root);                     
            primaryStage.setScene(scene);
            primaryStage.show();
           }
           
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
                        
        } catch (IOException ex) {
            Logger.getLogger(JavaFXFixit.class.getName()).log(Level.SEVERE, null, ex);
        }
      /*  try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Accueil.fxml"));
            Scene scene = new Scene(root, 1200, 660);

            final ObservableList<String> stylesheets = scene.getStylesheets();
            stylesheets.addAll(JavaFXFixit.class.getResource("/GUI/accueil.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(660);
            primaryStage.setMinWidth(1200);

            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(JavaFXFixit.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
 public static void createNewuser() {
        // SQLite connection string
        String url = "jdbc:sqlite:./db/user.db";
        // SQL statement for creating a new table
        
        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	id_user integer,\n"
                + "	Adr_id integer NOT NULL,\n"
                + "	nom varchar(100) ,\n"
                + "	prenom varchar(100) ,\n"
                + "	login varchar(100) ,\n"
                + "	pwd varchar(100) ,\n"
                + "	telephone varchar(100) ,\n"
                + "	email varchar(255) ,\n"

                + "	image blob ,\n"

                + "	nbPoint integer,\n"
                + "	type integer,\n"
                + "	cin varchar(255),\n"
                + "	Pay_id integer,\n"
                + "	Reg_id integer,\n"
                + "	Vil_id integer,\n"
                + "	description varchar(255)\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
