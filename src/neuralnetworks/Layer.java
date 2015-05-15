/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks;

import java.util.List;

/**
 *
 * @author Igorok
 */
public class Layer {
    
    private List<Neuron> parentNeurons;
    private List<Neuron> chieldNeurons;
    private double[][] weights;     

    public Layer(List<Neuron> parenNeurons, List<Neuron> chieldNeurons) {
        this.parentNeurons = parenNeurons;
        this.chieldNeurons = chieldNeurons;        
    }

    public List<Neuron> getParenNeurons() {
        return parentNeurons;
    }

    public List<Neuron> getChieldNeurons() {
        return chieldNeurons;
    }

    public void setParenNeurons(List<Neuron> parenNeurons) {
        this.parentNeurons = parenNeurons;
    }

    public void setChieldNeurons(List<Neuron> chieldNeurons) {
        this.chieldNeurons = chieldNeurons;
    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }
    public void setWeight(int i, int j, double value) {
        this.weights[i][j] = value;
    }      
}
