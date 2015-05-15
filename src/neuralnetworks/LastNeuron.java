/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks;

import neuralnetworks.function.Function;
import java.util.List;

/**
 *
 * @author Igorok
 */
public class LastNeuron extends Neuron {
    
    private double realOutput;
    
    public LastNeuron(Function func, List<Neuron> children, List<Neuron> parents, double realOutput) {
        super(func, children, parents);
        this.realOutput = realOutput;
    }

    public double getRealOutput() {
        return realOutput;
    }

    public void setRealOutput(double realOutput) {
        this.realOutput = realOutput;
    }

    @Override
    public void findDelta(double[] w) {
        setDelta(realOutput - super.getOutput());
    }        
    
}
