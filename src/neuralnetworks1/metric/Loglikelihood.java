/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1.metric;

import neuralnetworks1.metric.Metrics;

/**
 *
 * @author Igorok
 */
public class Loglikelihood implements Metrics{

    @Override
    public double calculate(double[] v1, double[] v2) {
        double d = 0;
        for (int i = 0; i < v1.length; i++) {
            d += v1[i]*Math.log(v2[i]) + (1 - v1[i])*Math.log(1 - v2[i]);
        }
        return -d;
    }

    @Override
    public double calculatePartialDerivaitveByV2Index(double[] v1, double[] v2, int v2Index) {
        return -(v1[v2Index]/v2[v2Index] - (1 - v1[v2Index])/(1 - v2[v2Index]));
    }
    
}
