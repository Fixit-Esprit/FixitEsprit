/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Pays;
import entity.Position;
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

    public List<Prestataire> getPrestatairByVilleAndService(int idville, int idService) {
        List<Prestataire> setprestataire = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat
                    = st.executeQuery("Select u.id,u.nom,u.prenom,u.telephone,u.email,u.image, p.description,p.numberPiont,s.description dservice from service s INNER JOIN  prestataire p INNER JOIN utilisateur u INNER JOIN adresse a where p.Uti_id = u.id and a.id = u.Adr_id  and a.Vil_id = " + idville + " and p.id = s.id and p.id = " + idService);
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
                p.setService(resultat.getString("dservice"));
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
            ResultSet resultat
                    = st.executeQuery("Select u.id,u.nom,u.prenom,u.telephone,u.email,u.image, p.description,p.numberPiont ,s.description dservice from service s INNER JOIN prestataire p INNER JOIN utilisateur u where p.Uti_id = u.id and p.id = s.id and p.id =" + idService);
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
                p.setService(resultat.getString("dservice"));
                setprestataire.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setprestataire;
    }

    public Position getPrestatairPosition(int id) {
        Position position = new Position();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat
                    = st.executeQuery("Select v.nom ville,r.nom region ,p.nom pays,v.latitude,v.longitude from utilisateur u INNER JOIN adresse a INNER JOIN ville v INNER JOIN region r INNER JOIN pays p where v.Reg_id = r.id and v.Pay_id = p.id and u.Adr_id = a.id and a.Vil_id = v.id and u.id =" + id);
            while (resultat.next()) {
                position.setVille(resultat.getString("ville"));
                position.setRegion(resultat.getString("region"));
                position.setPays(resultat.getString("pays"));
                position.setLatitude(resultat.getDouble("latitude"));
                position.setLongitude(resultat.getDouble("longitude"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return position;
    }
}
