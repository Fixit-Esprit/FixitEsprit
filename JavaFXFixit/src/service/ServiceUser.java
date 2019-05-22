/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.BCrypt;
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
        String req ="Select * from `utilisateur`  INNER JOIN client ON client.id = utilisateur.id INNER JOIN adresse ON adresse.id = utilisateur.Adr_id where login='"+u1.getLogin()+"' and `validation` = 0   ";
        System.out.println("r111"+req);
        st.execute(req);        
        ResultSet rs = st.executeQuery(req);   
        if (!rs.next() ) {
        System.out.println("no data for normale user");            
        String req2 ="Select * from `utilisateur` INNER JOIN client ON client.id = utilisateur.id  INNER JOIN prestataire ON prestataire.Uti_id = utilisateur.id INNER JOIN adresse ON adresse.id = utilisateur.Adr_id where login='"+u1.getLogin()+"' and `validation` = 0    ";
        st.execute(req2);
        ResultSet rs2 = st.executeQuery(req2);
        if (!rs2.next() ) {
        System.out.println("no data for prestataire2"); 
        return 0;   
        }else{
           
            if (checkPassword(u1.getPwd(), rs2.getString("motdepasse"))) {
            createNewuser();
            String sqlA = "INSERT INTO user(id_user,Adr_id,nom,prenom,login,pwd,telephone,email,image,nbPoint,type,cin,Pay_id,Reg_id,Vil_id,description)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             
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
            pstmt.setString(12, rs2.getString("cin"));
            pstmt.setInt(13, rs2.getInt("Pay_id"));
            pstmt.setInt(14, rs2.getInt("Reg_id"));
            pstmt.setInt(15, rs2.getInt("Vil_id"));
            pstmt.setString(16, rs2.getString("description"));
            pstmt.executeUpdate();
            
             return 2;
        } catch (SQLException e) {
            System.out.println("execute stement for insert ne marche pas ici");
            System.out.println(e.getMessage());
        }  
            }else{  return 0;    }
            }
          
            }else{
             System.out.println("pwd/****"+ rs.getString("motdepasse"));
            if (checkPassword(u1.getPwd(), rs.getString("motdepasse"))) {
            createNewuser();
            String sqlA = "INSERT INTO user(id_user,Adr_id,nom,prenom,login,pwd,telephone,email,image,nbPoint,type,cin,Pay_id,Reg_id,Vil_id,description)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            pstmt.setString(12,rs.getString("cin"));
            pstmt.setInt(13, rs.getInt("Pay_id"));
            pstmt.setInt(14, rs.getInt("Reg_id"));
            pstmt.setInt(15, rs.getInt("Vil_id"));
            pstmt.setString(16, rs.getString("description"));
            pstmt.executeUpdate();
            System.out.println("requetet"+sqlA);
             return 1;
        } catch (SQLException e) {
            System.out.println("execute stement for insert ne marche pas ici");
            System.out.println(e.getMessage());
        }  
             }else{  return 0;    }
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
                + "	type integer,\n"
                + "	cin varchar(255),\n"
                + "	Pay_id integer,\n"
                + "	Reg_id integer,\n"
                + "	Vil_id integer,\n"
                + "	description varchar(255)\n"
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
public int ajouterutilisateur(User u1){
         try {
            
            Statement st = Conn.createStatement();   
                                
            String req ="insert into adresse (Pay_id,Reg_id,Vil_id,description) values (  '"+ u1.getPays()+"', '"+ u1.getRegion()+"','"+ u1.getVille()+"','"+ u1.getAdresse()+"')";
            // System.out.println("requette"+req);
            st.executeUpdate(req);
           
             ResultSet rs = st.executeQuery("select last_insert_id() as id from adresse");             
            if(rs.next())
            {
            int lastid = rs.getInt(1);
            try {      
            String req2 ="insert into utilisateur (Adr_id,nom,prenom,login,motdepasse,telephone,email,image,nbPoint,validation,code) values ( '"+lastid+"','"+ u1.getNom()+"', '"+u1.getPrenom()+"', '"+u1.getLogin()+"','"+u1.getPwd()+"','"+u1.getTelephone()+"','"+u1.getEmail()+"','"+u1.getImage()+"','"+u1.getNbPoint()+"',1,'"+u1.getCode()+"' )";
            st.executeUpdate(req2);
            ResultSet rs2 = st.executeQuery("select last_insert_id() as ids from utilisateur");           
            if(rs2.next())
            {
            int lastiduser = rs2.getInt(1);
            String req3 ="INSERT INTO `client` (`id`, `cin`) VALUES  ( '"+lastiduser+"','"+ u1.getCin()+"'  )";
            
            st.executeUpdate(req3);
            }
              return 1;
            } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
       
            } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
          return 0;                 
        
        
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
     
public int check_email(String email){  
      
  try {
        
        Statement   st = Conn.createStatement();    
        String req ="Select * from `utilisateur`  INNER JOIN client ON client.id = utilisateur.id where email  LIKE '"+email+"'   ";
        //System.out.println("rqPsw oublier:"+req);
        st.execute(req);
        ResultSet rs = st.executeQuery(req);   
        if (!rs.next() ) {
        System.out.println("no data for normale user");            
        String req2 ="Select * from `utilisateur`  INNER JOIN prestataire ON prestataire.Uti_id = utilisateur.id where email  LIKE '"+email+"'  ";
        st.execute(req2);
        ResultSet rs2 = st.executeQuery(req2);
        if (!rs2.next() ) {
        System.out.println("no data for prestataire2"); 
        return 0;   
        }else{
        return 2; 
        }       
            }else{        
             return 1;         
            }    
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }    
          return 0;       
        
    }
   public int check_login(String login){  
      
  try {
        
        Statement   st = Conn.createStatement();    
        String req ="Select * from `utilisateur`  INNER JOIN client ON client.id = utilisateur.id where login  LIKE '"+login+"'   ";
        //System.out.println("rqPsw oublier:"+req);
        st.execute(req);
        ResultSet rs = st.executeQuery(req);   
        if (!rs.next() ) {
        System.out.println("no data for normale user");            
        String req2 ="Select * from `utilisateur`  INNER JOIN prestataire ON prestataire.Uti_id = utilisateur.id where login  LIKE '"+login+"'  ";
        st.execute(req2);
        ResultSet rs2 = st.executeQuery(req2);
        if (!rs2.next() ) {
        System.out.println("no data for prestataire2"); 
        return 0;   
        }else{
        return 2; 
        }       
            }else{        
             return 1;         
            }    
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }    
          return 0;       
        
    }  
public String get_passe_Paremail(String mail) throws SQLException {
         String motdepasse="";
        PreparedStatement pst;
        ResultSet res;
        System.out.println(mail);
        pst = Conn.prepareStatement("select * from `utilisateur` INNER JOIN client ON client.id = utilisateur.id where `email`=?");
        pst.setString(1, mail);
        res = pst.executeQuery();
        System.out.println(res);
        
        if (res.next()) {
              motdepasse = res.getString("motdepasse");
        }else{
        pst = Conn.prepareStatement("select * from `utilisateur` INNER JOIN prestataire ON prestataire.id = utilisateur.id where `email`=?");
        pst.setString(1, mail);
        res = pst.executeQuery();
        System.out.println(res);
           motdepasse = res.getString("motdepasse");
        }
        return motdepasse;
    }
public String get_login_Paremail(String mail) throws SQLException {
         String login="" ;
        PreparedStatement pst;
        ResultSet res;
        System.out.println(mail);
        pst = Conn.prepareStatement("select * from `utilisateur` INNER JOIN client ON client.id = utilisateur.id where `email`=?");
        pst.setString(1, mail);
        res = pst.executeQuery();
        System.out.println(res);
        
        if (res.next()) {
              login = res.getString("login");
        }else{
        pst = Conn.prepareStatement("select * from `utilisateur` INNER JOIN prestataire ON prestataire.id = utilisateur.id where `email`=?");
        pst.setString(1, mail);
        res = pst.executeQuery();
        System.out.println(res);
           login = res.getString("login");
        }
        return login;
    }
public void update_passe_Paremail(String pwd,String email){       
 try { 
            Statement   st = Conn.createStatement(); 
            String req2 ="UPDATE `utilisateur` SET   `motdepasse` = '"+pwd+"'  WHERE `utilisateur`.`email` like '"+email+"' ";
            System.out.println("req"+req2);
            st.executeUpdate(req2);
            } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
             }   
}
             
                          
public static boolean checkPassword(String password_plaintext, String stored_hash) {
boolean password_verified = false;
         System.out.println(password_plaintext+"--"+stored_hash);
        if (null == stored_hash || !stored_hash.startsWith("$2y$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return (password_verified);
 }   
        
public int check_code(String code){        
  try {
         Statement   st = Conn.createStatement();    
        String req ="Select * from `utilisateur`  INNER JOIN client ON client.id = utilisateur.id where code  = '"+code+"'   ";
        System.out.println("rqPsw oublier:"+req);
        st.execute(req);
        ResultSet rs = st.executeQuery(req);   
        if (!rs.next() ) {
        System.out.println("no data for normale user");            
        String req2 ="Select * from `utilisateur`  INNER JOIN prestataire ON prestataire.Uti_id = utilisateur.id where  code  = '"+code+"'  ";
        st.execute(req2);
        ResultSet rs2 = st.executeQuery(req2);
        if (!rs2.next() ) {
        System.out.println("no data for prestataire2"); 
        return 0;   
        }else{
        return 2; 
        }       
            }else{        
             return 1;         
            }    
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }    
          return 0;       
        
    }

         public void update_validationUser(String code){       
                      
            try { 
            Statement   st = Conn.createStatement(); 
            String req2 ="UPDATE `utilisateur` SET   `validation` = 0  WHERE `utilisateur`.`code` = '"+code+"' ";
            System.out.println("req"+req2);
            st.executeUpdate(req2);
            } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
             }   
             }
}
