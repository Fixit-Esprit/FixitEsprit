/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Voiture;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilis.DaoConnection;

/**
 *
 * @author hphqlim
 */
public class ServiceVoiture {

    private Connection conx = DaoConnection.getInstance().getConnect();

    public void sauvegarder(Voiture voiture) {
        try {
            Statement st;
            st = conx.createStatement();
            st.executeUpdate("INSERT INTO voiture VALUES ('" + voiture.getMatricule() + "',' ',' ','" + voiture.getNom() + "','assets/img/" + voiture.getImage() + "'," + voiture.getNomch() + "," + voiture.getPrix() + ",'" + voiture.getInformation() + "','" + voiture.getType() + "')");
         
        } catch (SQLException ex) {
            Logger.getLogger(ServiceVoiture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Voiture voiture) {
        try {
            Statement st;
            st = conx.createStatement();
            st.executeUpdate("delete from voiture  where matricule='" + voiture.getMatricule() + "'");
           
        } catch (SQLException ex) {
            Logger.getLogger(ServiceVoiture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Voiture voiture) {
        try {
            PreparedStatement st;
            st = conx.prepareStatement("update voiture set nom=?,image=?,nomch=?,prix=?,information=?,type=? where matricule=?");
            st.setString(7, voiture.getMatricule());
            st.setString(1, voiture.getNom());
            st.setString(2, voiture.getImage());
            st.setString(3, voiture.getNomch() );
            st.setString(4, voiture.getPrix());
            st.setString(5, voiture.getInformation());
            st.setString(6, voiture.getType());
            st.executeUpdate();
   
        } catch (SQLException ex) {
            Logger.getLogger(ServiceVoiture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getAllVoiture() {
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select * from voiture");
            while (resultat.next()) {
                Voiture v = new Voiture();
                v.setMatricule(resultat.getString("matricule"));
                v.setNom(resultat.getString("nom"));
                v.setImage(resultat.getString("image"));
                v.setNomch(resultat.getString("nomch"));
                v.setPrix(resultat.getString("prix"));
                v.setInformation(resultat.getString("information"));
                v.setType(resultat.getString("type"));
                System.out.println(v);
            }


        } catch (SQLException ex) {
            Logger.getLogger(ServiceVoiture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
