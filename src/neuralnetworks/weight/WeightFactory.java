/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks.weight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Igor
 */
public class WeightFactory {
    
    private Connection conn;
    private static final WeightFactory factory = new WeightFactory();

    private WeightFactory() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            Locale.setDefault(new Locale("es", "ES"));
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "diplomadmin", "1");
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            System.out.println(ex);     
        }
    }
    
    public Connection getConnection () {
        return conn;
    }
    
    public static WeightFactory getFactory() {
        return factory;
    }
}
