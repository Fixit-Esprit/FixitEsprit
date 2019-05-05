/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Region;
import entity.Ville;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import utilis.DaoConnection;

/**
 *
 * @author hphqlim
 */
public class VilleService {
         private Connection conx = DaoConnection.getInstance().getConnect();

    public List<Ville> getVilleByRegion(int regid) {
        List<Ville> setville = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select * from ville where Reg_id ="+regid);
            while (resultat.next()) {
                Ville ville = new Ville();
                ville.setId(resultat.getInt("id"));
                ville.setNom(resultat.getString("nom"));
                ville.setLatitude(resultat.getDouble("latitude"));
                ville.setLongitude(resultat.getDouble("longitude"));
                System.out.print(ville);
                setville.add(ville);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setville;
    }

}
