/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1;

/**
 *
 * @author Igorok
 */
public abstract class Layer {

    public Layer(double[] lastOutput, Neuron[] neurons, int InputDimension) {
        this.lastOutput = lastOutput;
        this.neurons = neurons;
        this.InputDimension = InputDimension;
    }    
    
    /// <summary>
    /// Compute output of the layer
    /// </summary>
    /// <param name="inputVector">Input vector</param>
    /// <returns>Output vector</returns>
    abstract double[] compute(double[] inputVector);

    /// <summary>
    /// Get last output of the layer
    /// </summary>
    private double[] lastOutput;

    /// <summary>
    /// Get neurons of the layer
    /// </summary>
    private Neuron[] neurons;

    /// <summary>
    /// Get input dimension of neurons
    /// </summary>
    private int InputDimension;

    public double[] getLastOutput() {
        return lastOutput;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public int getInputDimension() {
        return InputDimension;
    }
    
}
