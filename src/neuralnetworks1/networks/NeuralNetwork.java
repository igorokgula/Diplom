/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1.networks;

import java.util.List;
import neuralnetworks1.DataItem;

/**
 *
 * @author Igorok
 */
public interface NeuralNetwork {
    /// <summary>
    /// Compute output vector by input vector
    /// </summary>
    /// <param name="inputVector">Input vector (double[])</param>
    /// <returns>Output vector (double[])</returns>
    double[] computeOutput(double[] inputVector);

    //Stream save();

    /// <summary>
    /// Train network with given inputs and outputs
    /// </summary>
    /// <param name="inputs">Set of input vectors</param>
    /// <param name="outputs">Set if output vectors</param>
    void train(List<DataItem> data);
}
