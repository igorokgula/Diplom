/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks;

/**
 *
 * @author Igorok
 */
public class ConnectTwo {
    
    private Neuron neuron1;
    
    private Neuron neuron2;
    
    private double value = 0;

    public ConnectTwo(Neuron neuron1, Neuron neuron2, double value) {
        this.neuron1 = neuron1;
        this.neuron2 = neuron2;
        this.value = value;
    }        

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.neuron1.hashCode();
        hash = 59 * hash + this.neuron2.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConnectTwo other = (ConnectTwo) obj;
        if (!this.neuron1.equals(other.neuron1) && !this.neuron1.equals(other.neuron2)) {
            return false;
        }
        if (!this.neuron2.equals(other.neuron2) && !this.neuron2.equals(other.neuron1)) {
            return false;
        }        
        return true;
    }
}
