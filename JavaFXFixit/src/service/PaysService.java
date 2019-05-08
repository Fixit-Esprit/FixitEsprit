/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Pays;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilis.DaoConnection;

/**
 *
 * @author hphqlim
 */
public class PaysService {

    private Connection conx = DaoConnection.getInstance().getConnect();

    public List<Pays> getAllPays() {
        List<Pays> setpays = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select * from pays");
            while (resultat.next()) {
                Pays p = new Pays();
                p.setId(resultat.getInt("id"));
                p.setNom(resultat.getString("nom"));
                p.setCode(resultat.getString("code"));
                setpays.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setpays;
    }
}
