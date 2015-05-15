/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1;

import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import neuralnetworks.function.SigmoidFunction;
import neuralnetworks1.networks.ConcreteMultilayerNeuralNetwork;

/**
 *
 * @author Igorok
 */
public class Main {

    public static void main(String[] args) {
        
        List<Double> input = null;
        List<Double> output = null;
        
        Scanner in = null;
        Scanner out = null;
        try {
            in = new Scanner(new File("input.txt"));
            out = new Scanner(new File("output.txt"));
            input = new ArrayList<Double>();
            output = new ArrayList<Double>();            
            while (in.hasNext()) {
                input.add(in.nextDouble());                
            }
            while(out.hasNext()) {
                output.add(out.nextDouble());
            }
        } catch (IOException ex) {
            System.err.println("IOExeption !!!");
        } finally {
            in.close();
            out.close();
        }      
        
        double[] inputData = new double[input.size()];
        double[] outputData = new double[output.size()];
        for (int i = 0; i < input.size(); i++) {
            inputData[i] = input.get(i);            
        }        
        for (int i = 0; i < output.size(); i++) {
            outputData[i] = output.get(i);         
        }                
        
        DataItem data = new DataItem(inputData, outputData); 
        DataItem data1 = data;
        List<DataItem> allData = new ArrayList<>();
        allData.add(data);
        allData.add(data1);
                
        Neuron[] neuronsLayer1 = new Neuron[inputData.length];                
        for (int i = 0; i < inputData.length; i++) {                                                            
            neuronsLayer1[i] = new Neuron(null, 0, 0, 0, null, null, null, i) {
                @Override
                double NET(double[] inputVector) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
                @Override
                double activate(double[] inputVector) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
        
        double[] weightsLayer2 = {1.0/3, 1.0/3, 1.0/3};
        Neuron[] neuronsLayer2 = new Neuron[inputData.length];                
        
        for (int i = 0; i < 6; i++) {
            neuronsLayer2[i] = neuronsLayer1[i];
        }
        
        for (int i = 6; i < inputData.length; i++) {                
            List<Neuron> parents = new ArrayList<>();
            parents.add(neuronsLayer1[i - 5]);
            parents.add(neuronsLayer1[i - 4]);
            parents.add(neuronsLayer1[i - 3]);
                        
            neuronsLayer2[i] = new Neuron(weightsLayer2, 0, 0, 0, null, parents, new SigmoidFunction(1), i) {
                @Override
                double NET(double[] inputVector) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
                @Override
                double activate(double[] inputVector) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
        
        Layer[] layers = new Layer[2];
        layers[0] = new Layer(outputData, neuronsLayer1, inputData.length) {
            @Override
            double[] compute(double[] inputVector) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        layers[1] = new Layer(outputData, neuronsLayer2, inputData.length) {
            @Override
            double[] compute(double[] inputVector) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        ConcreteMultilayerNeuralNetwork c = new ConcreteMultilayerNeuralNetwork(layers);
        c.train(allData);
        
    }
}
