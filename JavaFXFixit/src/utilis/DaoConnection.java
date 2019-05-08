package utilis;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hp halim
 */
public class DaoConnection {

    private static Connection connect;
    private static DaoConnection daos;
    private final PreparedStatement pst = null;
    private final String url = "jdbc:mysql://localhost:3306/fixit";
    private final String user = "root";
    private final String pass = "";
    ResultSet resultat = null;

    private DaoConnection() {
        try {
            connect = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }

    public static DaoConnection getInstance() {
        if (daos == null) {
            daos = new DaoConnection();
        }
        return daos;
    }

    public static Connection getConnect() {
        return connect;
    }

}
