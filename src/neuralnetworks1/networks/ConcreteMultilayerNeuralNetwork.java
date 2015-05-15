/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1.networks;

import java.util.List;
import neuralnetworks1.BackpropagationFCNLearningAlgorithm;
import neuralnetworks1.DataItem;
import neuralnetworks1.Layer;
import neuralnetworks1.LearningStrategy;

/**
 *
 * @author Igorok
 */
public class ConcreteMultilayerNeuralNetwork extends MultilayerNeuralNetwork {
    
    private LearningStrategy<MultilayerNeuralNetwork> strategy = null;

    public ConcreteMultilayerNeuralNetwork(Layer[] layers) {
        super(layers);
    }
    
    @Override
    public double[] computeOutput(double[] inputVector) {
        return null;
    }

    @Override
    public void train(List<DataItem> data) {
        strategy = new BackpropagationFCNLearningAlgorithm();
        strategy.train(this, data);
    }
    
}
