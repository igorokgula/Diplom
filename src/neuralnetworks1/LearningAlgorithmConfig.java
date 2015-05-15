/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1;

import neuralnetworks1.metric.Metrics;

/**
 *
 * @author Igorok
 */
public class LearningAlgorithmConfig {
    
    private double learningRate;

    /// <summary>
    /// Size of the butch. -1 means fullbutch size. 
    /// </summary>
    private int batchSize;

    private double regularizationFactor;

    private int maxEpoches;

    /// <summary>
    /// If cumulative error for all training examples is less then MinError, then algorithm stops 
    /// </summary>
    private double minError;

    /// <summary>
    /// If cumulative error change for all training examples is less then MinErrorChange, then algorithm stops 
    /// </summary>
    private double minErrorChange;

    /// <summary>
    /// Function to minimize
    /// </summary>
    private Metrics errorFunction;

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public double getRegularizationFactor() {
        return regularizationFactor;
    }

    public void setRegularizationFactor(double regularizationFactor) {
        this.regularizationFactor = regularizationFactor;
    }

    public int getMaxEpoches() {
        return maxEpoches;
    }

    public void setMaxEpoches(int maxEpoches) {
        this.maxEpoches = maxEpoches;
    }

    public double getMinError() {
        return minError;
    }

    public void setMinError(double minError) {
        this.minError = minError;
    }

    public double getMinErrorChange() {
        return minErrorChange;
    }

    public void setMinErrorChange(double minErrorChange) {
        this.minErrorChange = minErrorChange;
    }

    public Metrics getErrorFunction() {
        return errorFunction;
    }

    public void setErrorFunction(Metrics errorFunction) {
        this.errorFunction = errorFunction;
    }
    
}
