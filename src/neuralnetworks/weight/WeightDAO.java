/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks.weight;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Igor
 */
public class WeightDAO extends Weight {

    @Override
    public double getValue() {
        double res = 0;
        String query = "select w.valuee\n"
                + "from weihgt w\n"
                + "where w.roww = ? and w.columnn = ? and w.iterationn = ?";
        try {
            PreparedStatement ps = WeightFactory.getFactory().getConnection().prepareStatement(query);
            ps.setInt(1, this.getI());
            ps.setInt(2, this.getJ());
            ps.setInt(3, this.getIteration());
            ResultSet rs = ps.executeQuery();
            rs.next();
            res = rs.getDouble("valuee");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return res;
    }

    @Override
    public void setValue(double value) {
        String query = "update weihgt w set w.valuee = ? \n"
                    + "where w.roww = ? and w.columnn = ?\n"
                    + "and w.iterationn = ?";
        super.setValue(value);
        try {            
            PreparedStatement ps = WeightFactory.getFactory().getConnection().prepareStatement(query);
            ps.setDouble(1, value);
            ps.setInt(2, this.getI());
            ps.setInt(3, this.getJ());
            ps.setInt(4, this.getIteration());
            ResultSet rs = ps.executeQuery();
            rs.next();            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public WeightDAO(int id, double value, int i, int j, int iteration) {
        super(id, value, i, j, iteration);
    }

    public static void main(String[] args) {                
        WeightDAO w = new WeightDAO(1, 1, 1, 1, 1);       
        System.out.println(w.getValue());
    }

}
