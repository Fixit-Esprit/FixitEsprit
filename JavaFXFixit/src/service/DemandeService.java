/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Demande;
import entity.Pays;
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

    public int ajouterDemande(Demande demande) {
        int result = 0;
        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PreparedStatement st;
            st = conx.prepareStatement("insert into demande(Uti_id,Cli_id,description,image,dateDemande,dateFunction) values(?,?,?,?,?,?)");
            st.setInt(1, demande.getIdpristataire());
            st.setInt(2, demande.getIdclient());
            st.setString(3, demande.getDescription());
            st.setString(4, demande.getImage());
            st.setString(5, Format.format(demande.getDateDemande()));
            st.setString(6, demande.getDateFunction());
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
            ResultSet resultat = st.executeQuery("Select d.id,s.description,u.nom,u.prenom,d.dateFunction,d.prix from utilisateur u INNER JOIN prestataire p INNER JOIN demande d INNER JOIN service s where s.id = p.id and u.id = p.Uti_id and p.Uti_id = d.Uti_id and d.Cli_id = " + idclient + " and d.acceptation_prestataire = 1");
            while (resultat.next()) {
                Demande demande = new Demande();
                demande.setId(resultat.getInt("id"));
                demande.setService(resultat.getString("description"));
                demande.setNomprestataire(resultat.getString("nom")+" "+resultat.getString("prenom"));
                demande.setDateFunction(resultat.getString("dateFunction"));
                demande.setPrix(resultat.getInt("prix"));
                demandes.add(demande);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return demandes;
    }
}
