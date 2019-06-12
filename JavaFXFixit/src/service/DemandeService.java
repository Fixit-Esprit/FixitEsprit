/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Demande;
import entity.Pays;
import entity.Position;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static service.ServiceUser.createNewuser;
import utilis.DaoConnection;

/**
 *
 * @author hphqlim
 */
public class DemandeService {

    private Connection conx = DaoConnection.getInstance().getConnect();
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");

    public int ajouterDemande(Demande demande) {
        int result = 0;
        try {
            PreparedStatement st;
            st = conx.prepareStatement("insert into demande(Uti_id,Cli_id,title,description,image,dateDemande,dateFunction,acceptation_prestataire) values(?,?,?,?,?,?,?,?)");
            st.setInt(1, demande.getIdpristataire());
            st.setInt(2, demande.getIdclient());
            st.setString(3, demande.getTitle());
            st.setString(4, demande.getDescription());
            st.setString(5, demande.getImage());
            st.setString(6, Format.format(demande.getDateDemande()));
            st.setString(7, demande.getDateFunction());
            st.setInt(8, 0);
            result = st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DemandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Demande> getAllDemandeAccepter(int idclient) {
        List<Demande> demandes = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select d.id,d.title,d.description description,d.image, s.description sdescription,u.nom,u.prenom,d.dateFunction,d.prix from utilisateur u INNER JOIN prestataire p INNER JOIN demande d INNER JOIN service s where s.id = p.id and u.id = p.Uti_id and p.Uti_id = d.Uti_id and d.Cli_id = " + idclient + " and d.acceptation_prestataire = 1");
            while (resultat.next()) {
                Demande demande = new Demande();
                demande.setId(resultat.getInt("id"));
                demande.setTitle(resultat.getString("title"));
                demande.setImage(resultat.getString("image"));
                demande.setDescription(resultat.getString("description"));
                demande.setService(resultat.getString("sdescription"));
                demande.setNomprestataire(resultat.getString("nom") + " " + resultat.getString("prenom"));
                demande.setDateFunction(Format.format(resultat.getDate("dateFunction")));
                demande.setPrix(resultat.getInt("prix"));
                demandes.add(demande);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return demandes;
    }

    public List<Demande> getAllNewDemande(int idprestataire) {
        List<Demande> demandes = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select uc.id idclient,d.id,d.title,d.description description,uc.nom,uc.prenom,d.dateFunction,d.image,d.prix from utilisateur u INNER JOIN prestataire p INNER JOIN demande d INNER JOIN client c INNER JOIN utilisateur uc  where uc.id = c.id and c.id = d.Cli_id and p.Uti_id = " + idprestataire + " and u.id = p.Uti_id and d.Uti_id = p.Uti_id and  d.acceptation_prestataire = 0");
            while (resultat.next()) {
                Demande demande = new Demande();
                demande.setId(resultat.getInt("id"));
                demande.setTitle(resultat.getString("title"));
                demande.setImage(resultat.getString("image"));
                demande.setDescription(resultat.getString("description"));
                demande.setNomclient(resultat.getString("nom") + " " + resultat.getString("prenom"));
                demande.setDateFunction(Format.format(resultat.getDate("dateFunction")));
                demande.setPrix(resultat.getInt("prix"));

                ServiceUser serviceUser = new ServiceUser();
                Position position = serviceUser.getPosition(resultat.getInt("idclient"));
                demande.setAdresseclient(position.getPays() + " -> " + position.getRegion() + " -> " + position.getVille());
                System.out.println(position);

                demandes.add(demande);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return demandes;
    }

    public int ConfirmerDemande(int id, int prix) {
        System.out.println("prix: " + prix);
        try {
            Statement st = conx.createStatement();

            String req = "UPDATE demande SET acceptation_prestataire = 1 , prix = " + prix + " where id =" + id;
            return st.executeUpdate(req);

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }
}
