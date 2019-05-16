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
        String req ="Select * from `utilisateur`  INNER JOIN client ON client.id = utilisateur.id where login='"+u1.getLogin()+"' and  motdepasse='"+u1.getPwd()+"'   ";
        st.execute(req);
        ResultSet rs = st.executeQuery(req);   
        if (!rs.next() ) {
        System.out.println("no data for normale user");            
        String req2 ="Select * from `utilisateur`  INNER JOIN prestataire ON prestataire.id = utilisateur.id where login='"+u1.getLogin()+"' and  motdepasse='"+u1.getPwd()+"'   ";
        st.execute(req2);
        ResultSet rs2 = st.executeQuery(req2);
        if (!rs2.next() ) {
        System.out.println("no data for prestataire2"); 
        return 0;   
        }else{
             createNewuser();
            String sqlA = "INSERT INTO user(id_user,Adr_id,nom,prenom,login,pwd,telephone,email,image,nbPoint,type)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlA)) {         
            pstmt.setInt(1, rs2.getInt("id"));
            pstmt.setInt(2, rs2.getInt("Adr_id"));
            pstmt.setString(3, rs2.getString("nom"));
            pstmt.setString(4, rs2.getString("prenom"));
            pstmt.setString(5, rs2.getString("telephone"));
            pstmt.setString(6, rs2.getString("login"));
            pstmt.setString(7, rs2.getString("motdepasse"));
            pstmt.setString(8, rs2.getString("email"));
            pstmt.setString(9, rs2.getString("image"));
            pstmt.setInt(10, rs2.getInt("nbPoint"));
            pstmt.setInt(11,2);
            pstmt.executeUpdate();
             return 2;
        } catch (SQLException e) {
            System.out.println("execute stement for insert ne marche pas ici");
            System.out.println(e.getMessage());
        }  
            }
          
            }else{
             createNewuser();
            String sqlA = "INSERT INTO user(id_user,Adr_id,nom,prenom,login,pwd,telephone,email,image,nbPoint,type)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
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
            pstmt.setInt(11,2);
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
                + "	nbPoint integer,\n"
                + "	type integer\n"
                + ");";
        //Ajouter champs de test
        
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
                                
            String req ="insert into adresse (Pay_id,Reg_id,Vil_id,description) values (  '"+ u1.getPays()+"', '"+ u1.getRegion()+"','"+ u1.getVille()+"','"+ u1.getAdresse()+"')";
            st.executeUpdate(req);
             ResultSet rs = st.executeQuery("select last_insert_id() as id from adresse");             
            if(rs.next())
            {
            int lastid = rs.getInt(1);
            try {      
            String req2 ="insert into utilisateur (Adr_id,nom,prenom,login,motdepasse,telephone,email,image,nbPoint) values ( '"+lastid+"','"+ u1.getNom()+"', '"+u1.getPrenom()+"', '"+u1.getLogin()+"','"+u1.getPwd()+"','"+u1.getTelephone()+"','"+u1.getEmail()+"','"+u1.getImage()+"','"+u1.getNbPoint()+"' )";
             
            st.executeUpdate(req2);
            } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
             }
            }
            } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
                          
        
        
    }
 
      public void Updateutilisateur(User u1){
        
    try {
            
            Statement st = Conn.createStatement();  
                                
            String req ="UPDATE adresse SET `Pay_id` = '"+u1.getPays()+"', Reg_id = '"+u1.getRegion()+"',Vil_id  = '"+u1.getRegion()+"' ,description = '"+u1.getAdresse()+"' ";
            st.executeUpdate(req);          
             
            try {      
            String req2 ="UPDATE `utilisateur` SET `nom` = '"+u1.getNom()+"', `prenom` = '"+u1.getPrenom()+"', `login` = '"+u1.getLogin()+"', `motdepasse` = '"+u1.getPwd()+"', `telephone` = '"+u1.getTelephone()+"', `email` = '"+u1.getEmail()+"',`image` = '"+u1.getImage()+"' WHERE `utilisateur`.`id` = "+u1.getId()+" ";
            System.out.println("req"+req2);
            st.executeUpdate(req2);
            } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
             }
           
            } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
                          
        
        
    } 
     public int getIDVille(String ville){  
      
  try {
        
        Statement   st = Conn.createStatement();    
        String req ="Select * from `ville` where nom LIKE '"+ville+"'  ";
        st.execute(req);
        ResultSet rs = st.executeQuery(req);   
            if (!rs.next() )             
            return 0;
           
            return rs.getInt("id");
           
             } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }    
     return 0;
         }
     
     
     
}
