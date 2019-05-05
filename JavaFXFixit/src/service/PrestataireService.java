/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Pays;
import entity.Prestataire;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilis.DaoConnection;

/**
 *
 * @author hphqlim
 */
public class PrestataireService {
    private Connection conx = DaoConnection.getInstance().getConnect();

    public List<Prestataire> getPrestatairByVilleAndService(int idville,int idService) {
        List<Prestataire> setprestataire = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = 
                    st.executeQuery("Select u.id,u.nom,u.prenom,u.telephone,u.email,u.image, p.description,p.numberPiont from prestataire p INNER JOIN utilisateur u INNER JOIN adresse a where p.Uti_id = u.id  and a.Vil_id = "+idville+" and p.id = "+idService);
            while (resultat.next()) {
                Prestataire p = new Prestataire();
                p.setId(resultat.getInt("id"));
                p.setNom(resultat.getString("nom"));
                p.setDescription(resultat.getString("description"));
                setprestataire.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setprestataire;
    }
    
        public List<Prestataire> getPrestatairByService(int idService) {
        List<Prestataire> setprestataire = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = 
                    st.executeQuery("Select u.id,u.nom,u.prenom,u.telephone,u.email,u.image, p.description,p.numberPiont from prestataire p INNER JOIN utilisateur u where p.Uti_id = u.id and p.id ="+idService);
            while (resultat.next()) {
                Prestataire p = new Prestataire();
                p.setId(resultat.getInt("id"));
                p.setNom(resultat.getString("nom"));
                p.setPrenom(resultat.getString("prenom"));
                p.setTel(resultat.getString("telephone"));
                p.setEmail(resultat.getString("email"));
                
                p.setImage(resultat.getString("image"));
                p.setDescription(resultat.getString("description"));
                p.setNbpiont(resultat.getInt("numberPiont"));
                setprestataire.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setprestataire;
    }
}
