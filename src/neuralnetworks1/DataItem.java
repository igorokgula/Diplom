/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1;

/**
 *
 * @author Igorok
 */
public class DataItem implements Cloneable{
    
    private double[] input = null;
    private double[] output = null;

    public DataItem(){
        
    }

    public DataItem(double[] input, double[] output){
        this.input = input;
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public void setInput(double[] input) {
        this.input = input;
    }

    public double[] getOutput() {
        return output;
    }

    public void setOutput(double[] output) {
        this.output = output;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
        
}
