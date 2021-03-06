/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Annonce;
import entity.AnnonceAccepte;
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

public class AnnonceService {

    private Connection conx = DaoConnection.getInstance().getConnect();
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");

    public int ajouterAnnonce(Annonce annonce) {
        int result = 0;
        try {
            PreparedStatement st;
            st = conx.prepareStatement("insert into annonce(idclient,idservice,date,description,image,minprix,maxprix,title) values(?,?,?,?,?,?,?,?)");
            st.setInt(1, annonce.getIdclient());
            st.setInt(2, annonce.getIdservice());
            st.setString(3, Format.format(annonce.getDate()));
            st.setString(4, annonce.getDescription());
            st.setString(5, annonce.getImage());
            st.setInt(6, annonce.getMinprix());
            st.setInt(7, annonce.getMaxprix());
            st.setString(8, annonce.getTitle());
            result = st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DemandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Annonce> getAnnoncePublier(int idprestataire) {
        int idservice = getIdServiseByIdPrestataire(idprestataire);
        List<Annonce> annonces = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select a.id,a.idclient,a.idservice,a.title,a.description,a.image,a.minprix,a.maxprix,u.nom,u.prenom from annonce a INNER JOIN utilisateur u where u.id = a.idclient and idservice=" + idservice + " and publier = 1");
            while (resultat.next()) {
                Annonce annonce = new Annonce();
                annonce.setId(resultat.getInt("id"));
                annonce.setIdclient(resultat.getInt("idclient"));
                annonce.setIdservice(resultat.getInt("idservice"));
                annonce.setTitle(resultat.getString("title"));
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

    public List<Annonce> getAnnonceByIdClient(int idclient) {
        List<Annonce> annonces = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select a.id,a.idclient,a.idservice,a.title,a.description,a.image,a.minprix,a.maxprix,u.nom,u.prenom from annonce a INNER JOIN utilisateur u where u.id = a.idclient and a.idclient=" + idclient);
            while (resultat.next()) {
                Annonce annonce = new Annonce();
                annonce.setId(resultat.getInt("id"));
                annonce.setIdclient(resultat.getInt("idclient"));
                annonce.setIdservice(resultat.getInt("idservice"));
                annonce.setTitle(resultat.getString("title"));
                annonce.setDescription(resultat.getString("description"));
                annonce.setImage(resultat.getString("image"));
                annonce.setMinprix(resultat.getInt("minprix"));
                annonce.setMaxprix(resultat.getInt("maxprix"));
                annonce.setNomclient(resultat.getString("nom") + " " + resultat.getString("prenom"));
                ServiceUser serviceUser = new ServiceUser();

                annonces.add(annonce);
                System.out.println(annonce);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annonces;
    }

    public List<AnnonceAccepte> getAnnonceAccepte(int idannonce) {
        List<AnnonceAccepte> annonceAcceptes = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select acpt.idannonce,acpt.idprestataire,acpt.prix,acpt.date,u.nom,u.prenom from accepteannonce acpt INNER JOIN annonce a  INNER JOIN utilisateur u where acpt.idprestataire = u.id and acpt.idannonce = a.id and acpt.idannonce=" + idannonce);
            while (resultat.next()) {
                AnnonceAccepte annonceAccepte = new AnnonceAccepte();
                annonceAccepte.setIdannonce(resultat.getInt("idannonce"));
                annonceAccepte.setIdprestataire(resultat.getInt("idprestataire"));
                annonceAccepte.setPrix(resultat.getInt("prix"));
                annonceAccepte.setDate(Format.format(resultat.getDate("date")));
                annonceAccepte.setNomprestataire(resultat.getString("nom") + " " + resultat.getString("prenom"));
                annonceAcceptes.add(annonceAccepte);
                System.out.println(annonceAcceptes);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annonceAcceptes;
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

    public int getIdServiseByIdPrestataire(int idprestataire) {
        List<Annonce> annonces = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select id from prestataire where  Uti_id =" + idprestataire);
            while (resultat.next()) {
                return resultat.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int ajouterAnnonceAccepte(AnnonceAccepte annonceAccepte) {
        int result = 0;
        try {
            PreparedStatement st;
            st = conx.prepareStatement("insert into accepteannonce(idannonce,idprestataire,prix,date) values(?,?,?,?)");
            st.setInt(1, annonceAccepte.getIdannonce());
            st.setInt(2, annonceAccepte.getIdprestataire());
            st.setInt(3, annonceAccepte.getPrix());
            st.setString(4, annonceAccepte.getDate());
            result = st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DemandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
