/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks.function;

/**
 *
 * @author Igorok
 */
public class SigmoidFunction implements Function{
    
    private double alpha = 1;

    public SigmoidFunction(double alpha) {
        this.alpha = alpha;
    }   
    
    @Override
    public double compute(double x) {                
        return (1 / (1 + Math.exp(-alpha * x)));
    }

    @Override
    public double computeFirstDerivative(double x) {
        return alpha * this.compute(x) * (1 - this.compute(x));
    }
    
}
