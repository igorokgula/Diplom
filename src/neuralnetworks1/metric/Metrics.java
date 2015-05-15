/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1.metric;

/**
 *
 * @author Igorok
 */
public interface Metrics {

    double calculate(double[] v1, double[] v2);
   
    double calculatePartialDerivaitveByV2Index(double[] v1, double[] v2, int v2Index);
}
