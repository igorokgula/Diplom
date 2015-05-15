/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks.function;

/**
 *
 * @author Igorok
 */
public interface Function {
    double compute(double x);
    double computeFirstDerivative(double x);
}
