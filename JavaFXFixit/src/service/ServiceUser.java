/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import utilis.*;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bhk
 */
public class ServiceUser {
    
 private Connection Conn = DaoConnection.getInstance().getConnect();
    
    public int login(User u1){  
      
  try {
        
        Statement   st = Conn.createStatement();    
        String req ="Select * from `utilisateur` where login='"+u1.getLogin()+"' and  motdepasse='"+u1.getPwd()+"' ";
        st.execute(req);
        ResultSet rs = st.executeQuery(req);   
            if (!rs.next() ) {
            System.out.println("no data");
            return 0;
            }else{ 
         createNewuser();
          //  rs.next();
            String sqlA = "INSERT INTO user(id_user,Adr_id,nom,prenom,login,pwd,telephone,email,image,nbPoint)VALUES(?,?,?,?,?,?,?,?,?,?)";
            try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlA)) {         
            pstmt.setInt(1, rs.getInt("id"));
            pstmt.setInt(2, rs.getInt("Adr_id"));
            pstmt.setString(3, rs.getString("nom"));
            pstmt.setString(4, rs.getString("prenom"));
            pstmt.setString(5, rs.getString("telephone"));
            pstmt.setString(6, rs.getString("login"));
            pstmt.setString(7, rs.getString("motdepasse"));
            pstmt.setString(8, rs.getString("email"));
            pstmt.setString(9, rs.getString("image"));
            pstmt.setInt(10, rs.getInt("nbPoint"));
            pstmt.executeUpdate();
             return 1;
        } catch (SQLException e) {
            System.out.println("execute stement for insert ne marche pas ici");
            System.out.println(e.getMessage());
        }    
        }            
    
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    
          return 0;      
        
        
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
                + "	image varchar(255) ,\n"
                + "	nbPoint integer\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void ajouterutilisateur(User u1){
        
        try {
            Statement st = Conn.createStatement();                        
            String req ="insert into utilisateur (Adr_id,nom,prenom,login,pwd,telephone,email,image,nbPoint) values ( '"+u1.getAdresse()+"','"+ u1.getNom()+"', '"+u1.getPrenom()+"', '"+u1.getLogin()+"','"+u1.getPwd()+"','"+u1.getTelephone()+"','"+u1.getEmail()+"','"+u1.getImage()+"','"+u1.getNbPoint()+"')";
                        st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }                    
        
        
    }
      public void Updateutilisateur(User u1){
        
        try {
            Statement st = Conn.createStatement();                        
            String req ="insert into utilisateur (Adr_id,nom,prenom,login,pwd,telephone,email,image,nbPoint) values ( '"+u1.getAdresse()+"','"+ u1.getNom()+"', '"+u1.getPrenom()+"', '"+u1.getLogin()+"','"+u1.getPwd()+"','"+u1.getTelephone()+"','"+u1.getEmail()+"','"+u1.getImage()+"','"+u1.getNbPoint()+"')";
                        st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }                    
        
        
    } 
    
}
