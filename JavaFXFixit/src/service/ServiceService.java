/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Region;
import entity.Service;
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
public class ServiceService {
         private Connection conx = DaoConnection.getInstance().getConnect();

    public List<Service> getAllService() {
        List<Service> setservice = new ArrayList();
        try {
            Statement st;
            st = conx.createStatement();
            ResultSet resultat = st.executeQuery("Select * from service");
            while (resultat.next()) {
                Service service = new Service();
                service.setId(resultat.getInt("id"));
                service.setDescription(resultat.getString("description"));
                System.out.print(service);
                setservice.add(service);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaysService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setservice;
    }
}
