/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks.weight;

/**
 *
 * @author Igor
 */
public class Weight {    
    private int id;
    private double value;
    private int i;
    private int j;
    private int iteration;

    public Weight() {
    }

    public Weight(int id, double value, int i, int j, int iteration) {
        this.id = id;
        this.value = value;
        this.i = i;
        this.j = j;
        this.iteration = iteration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }
        
    
    
}
