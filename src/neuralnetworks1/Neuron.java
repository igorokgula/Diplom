/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1;

import neuralnetworks.function.Function;
import java.util.List;

/**
 *
 * @author Igorok
 */
public abstract class Neuron {    
    /// <summary>
    /// Weights of the neuron
    /// </summary>
    private double[] weights;
    /// <summary>
    /// Offset/bias of neuron (default is 0)
    /// </summary>
    private double bias = 0;

    /// <summary>
    /// Compute NET of the neuron by input vector
    /// </summary>
    /// <param name="inputVector">Input vector (must be the same dimension as was set in SetDimension)</param>
    /// <returns>NET of neuron</returns>
    abstract double NET(double[] inputVector);

    /// <summary>
    /// Compute state of neuron
    /// </summary>
    /// <param name="inputVector">Input vector (must be the same dimension as was set in SetDimension)</param>
    /// <returns>State of neuron</returns>
    abstract double activate(double[] inputVector);

    /// <summary>
    /// Last calculated state in Activate
    /// </summary>
    private double lastState;

    /// <summary>
    /// Last calculated NET in NET
    /// </summary>
    private double lastNET;

    private List<Neuron> childs;

    private List<Neuron> parents;

    private Function activationFunction;

    private double dEdz;

    public Neuron(double[] weights, double bias, double lastState, double lastNET, List<Neuron> childs, List<Neuron> parents, Function activationFunction, double dEdz) {
        this.weights = weights;
        this.bias = bias;
        this.lastState = lastState;
        this.lastNET = lastNET;
        this.childs = childs;
        this.parents = parents;
        this.activationFunction = activationFunction;
        this.dEdz = dEdz;
    }    
    
    public double getBias() {
        return bias;
    }

    public double getLastState() {
        return lastState;
    }

    public double getLastNET() {
        return lastNET;
    }

    public Function getActivationFunction() {
        return activationFunction;
    }

    public double getdEdz() {
        return dEdz;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setLastState(double lastState) {
        this.lastState = lastState;
    }

    public void setLastNET(double lastNET) {
        this.lastNET = lastNET;
    }

    public void setActivationFunction(Function activationFunction) {
        this.activationFunction = activationFunction;
    }

    public void setdEdz(double dEdz) {
        this.dEdz = dEdz;
    }        

    public double[] getWeights() {
        return weights;
    }

    public List<Neuron> getChilds() {
        return childs;
    }

    public List<Neuron> getParents() {
        return parents;
    }
    
    public void setWeightI(double value, int index) {
        this.weights[index] = value;
    }
    
    
}
