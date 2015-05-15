/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1;

import java.util.List;

/**
 *
 * @author Igorok
 */
public interface LearningStrategy<T> {
    /// <summary>
    /// Train neural network
    /// </summary>
    /// <param name="network">Neural network for training</param>
    /// <param name="inputs">Set of input vectors</param>
    /// <param name="outputs">Set of output vectors</param>
    void train(T network, List<DataItem> data);
}
