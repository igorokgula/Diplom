/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks.function;

/**
 *
 * @author Igorok
 */
public class HyperbolicTangensFunction implements Function{

    private double alpha = 1;

    public HyperbolicTangensFunction(double alpha) {
        this.alpha = alpha;
    }        
    
    @Override
    public double compute(double x) {
        return (Math.tan(alpha * x));
    }

    @Override
    public double computeFirstDerivative(double x) {
        double t = Math.tan(alpha*x);
        return alpha*(1 - t*t);
    }
    
}
