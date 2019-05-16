/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Pays;
import entity.Region;
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
public class RegionService {
     private Connection conx = DaoConnection.getInstance().getConnect();

    public List<Region> getRegionByPays(int payid) {
        List<Region> setregion = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();

            ResultSet resultat = st.executeQuery("Select * from region where Pay_id ="+payid+" order by nom");

            while (resultat.next()) {
                Region region = new Region();
                region.setId(resultat.getInt("id"));
                region.setNom(resultat.getString("nom"));
                region.setCode(resultat.getString("code"));
                System.out.print(region);
                setregion.add(region);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setregion;
    }

}
