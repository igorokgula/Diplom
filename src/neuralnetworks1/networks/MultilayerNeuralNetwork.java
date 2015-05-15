/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1.networks;

import neuralnetworks1.Layer;
import neuralnetworks1.networks.NeuralNetwork;

/**
 *
 * @author Igorok
 */
public abstract class MultilayerNeuralNetwork  implements NeuralNetwork{

    private Layer[] layers;
    
    public MultilayerNeuralNetwork(Layer[] layers) {
        this.layers = layers;
    }
        
    public Layer[] getLayers() {
        return layers;
    }
}
