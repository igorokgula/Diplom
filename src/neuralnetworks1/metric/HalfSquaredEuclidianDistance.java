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
public class HalfSquaredEuclidianDistance implements Metrics {

    @Override
    public double calculate(double[] v1, double[] v2) {
        double d = 0;
        for (int i = 0; i < v1.length; i++)
        {
            d += (v1[i] - v2[i]) * (v1[i] - v2[i]);
        }
        return 0.5 * d;
    }

    @Override
    public double calculatePartialDerivaitveByV2Index(double[] v1, double[] v2, int v2Index) {
        return v2[v2Index] - v1[v2Index];
    }
    
}
