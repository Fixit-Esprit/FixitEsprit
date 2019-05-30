/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Annonce;
import entity.Demande;
import entity.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilis.DaoConnection;

/**
 *
 * @author hphqlim
 */
public class AnnonceService {

    private Connection conx = DaoConnection.getInstance().getConnect();
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");

    public int ajouterAnnonce(Annonce annonce) {
        int result = 0;
        try {
            PreparedStatement st;
            st = conx.prepareStatement("insert into annonce(idclient,idservice,date,description,image,minprix,maxprix) values(?,?,?,?,?,?,?)");
            st.setInt(1, annonce.getIdclient());
            st.setInt(2, annonce.getIdservice());
            st.setString(3, Format.format(annonce.getDate()));
            st.setString(4, annonce.getDescription());
            st.setString(5, annonce.getImage());
            st.setInt(6, annonce.getMinprix());
            st.setInt(7, annonce.getMaxprix());
            result = st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DemandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Annonce> getAnnoncePublier(int idservice) {
        List<Annonce> annonces = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select a.id,a.idclient,a.idservice,a.description,a.image,a.minprix,a.maxprix,u.nom,u.prenom from annonce a INNER JOIN utilisateur u where u.id = a.idclient and idservice=" + idservice + " and publier = 1");
            while (resultat.next()) {
                Annonce annonce = new Annonce();
                annonce.setId(resultat.getInt("id"));
                annonce.setIdclient(resultat.getInt("idclient"));
                annonce.setIdservice(resultat.getInt("idservice"));

                annonce.setDescription(resultat.getString("description"));
                annonce.setImage(resultat.getString("image"));
                annonce.setMinprix(resultat.getInt("minprix"));
                annonce.setMaxprix(resultat.getInt("maxprix"));
                annonce.setNomclient(resultat.getString("nom") + " " + resultat.getString("prenom"));
                ServiceUser serviceUser = new ServiceUser();
                Position position = serviceUser.getPosition(resultat.getInt("idclient"));
                annonce.setAdresseclient(position.getPays() + " -> " + position.getRegion() + " -> " + position.getVille());
                System.out.println(position);

                annonces.add(annonce);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annonces;
    }

    public void ConfirmerAnnonce(int id, int prix) {
        System.out.println("prix: " + prix);
        try {
            Statement st = conx.createStatement();

            String req = "UPDATE demande SET acceptation_prestataire = 1 , prix = " + prix + " where id =" + id;
            st.executeUpdate(req);

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
