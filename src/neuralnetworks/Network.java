/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks;

import neuralnetworks.function.SigmoidFunction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import neuralnetworks.constants.Constants;
import neuralnetworks.function.Function;

/**
 *
 * @author Igorok
 */
public class Network implements Constants {

    private List<Layer> layers;
    private final int SIZE;
    private List<Neuron> neurons;
    private static double[] input;
    private static double[] realOutput;

    private static int currentOutput = 0;

    private Layer firstMiddleLayer;
    private Layer middleLastLayer;

    //for denorm of data
    private double maxInput;
    private double minInput;

    public static double getInputI(int i) {
        return input[i];
    }

    public static double getRealOutputI(int i) {
        return realOutput[i];
    }

    public Network(final int size) {
        this.SIZE = size;
        init(new SigmoidFunction(SIGMOID_ALPHA));
    }

    public Network(List<Neuron> neurons) {
        this.neurons = neurons;
        this.SIZE = neurons.size();
    }

    private void loadTrainData() {
        Scanner in = null;
        Scanner out = null;
        try {
            in = new Scanner(new File("input1000.txt"));
            out = new Scanner(new File("output1000.txt"));
            input = new double[SIZE];
            realOutput = new double[SIZE];
            int i = 0;
            while (in.hasNext()) {
                input[i++] = in.nextDouble();
            }
            i = 0;
            while (out.hasNext()) {
                realOutput[i++] = out.nextDouble();
            }
        } catch (IOException ex) {
            System.err.println("IOExeption !!!");
        } finally {
            in.close();
            out.close();
        }
    }

    private void getData() {
        Scanner in = null;
        Scanner out = null;
        try {
            in = new Scanner(new File("EURUSD_150215_150315_IN.txt"));
            out = new Scanner(new File("EURUSD_150215_150315_OUT.txt"));
            input = new double[SIZE];
            realOutput = new double[SIZE];
            int i = 0;
            int k = 0;
            while (in.hasNext()) {
                if (i++ % 8 == 4) {
                    input[k++] = in.nextDouble();
                } else {
                    in.next();
                }
            }
            i = 0;
            k = 0;
            while (out.hasNext()) {
                if (i++ % 8 == 4) {
                    realOutput[k++] = out.nextDouble();
                } else {
                    out.next();
                }
            }

        } catch (FileNotFoundException ex) {
            System.err.println("IOExeption !!!");
        } finally {
            in.close();
            out.close();
        }
    }

    private double maxInArray(double[] arr) {
        double res = arr[0];
        for (double d : arr) {
            if (res < d) {
                res = d;
            }
        }
        return res;
    }

    private double minInArray(double[] arr) {
        double res = arr[0];
        for (double d : arr) {
            if (res > d) {
                res = d;
            }
        }
        return res;
    }

    // all data >=0 and <= 1
    private double[] normData(double[] arr) {
        try {
            maxInput = maxInArray(arr);
            minInput = minInArray(arr);

            for (int i = 0; i < arr.length; i++) {
                arr[i] = (arr[i] - minInput) / (maxInput - minInput);
            }
        } catch (NullPointerException ex) {
            System.out.println("NullPointerEx class network");
        }
        return arr;
    }

    private double[] denormData(double[] arr, double max, double min) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (max - min) / (arr[i] - min);
        }
        return arr;
    }

    private double denormOneValue(double a, double max, double min) {
        return a = (max - min) / (a - min);
    }

    public final void init(Function f) {

        //loadTrainData();
        getData();
        normData(input);
        normData(realOutput);

        List<Neuron> firstLayer = new ArrayList<>();
        List<Neuron> middleLayer = new ArrayList<>();
        List<Neuron> lastLayer = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            firstLayer.add(new Neuron(f, null, null));
        }
        for (int i = 0; i < 3; i++) {
            middleLayer.add(new Neuron(f, null, null));
        }
        lastLayer.add(new LastNeuron(f, null, null,
                realOutput[5]));

        for (int i = 0; i < 3; i++) {
            // add children from first to middle layout and vice versa
            firstLayer.get(i).addChield(middleLayer.get(i));
            middleLayer.get(i).addParent(firstLayer.get(i));

            firstLayer.get(i + 1).addChield(middleLayer.get(i));
            middleLayer.get(i).addParent(firstLayer.get(i + 1));

            firstLayer.get(i + 2).addChield(middleLayer.get(i));
            middleLayer.get(i).addParent(firstLayer.get(i + 2));
        }
        // add children from middle to last layout and vice versa
        for (int i = 0; i < 3; i++) {
            middleLayer.get(i).addChield(lastLayer.get(0));
            lastLayer.get(0).addParent(middleLayer.get(i));
        }
        int j = 0;
        for (Neuron n : firstLayer) {
            n.setOutput(input[j++]);
        }

        firstMiddleLayer = new Layer(firstLayer, middleLayer);
        middleLastLayer = new Layer(middleLayer, lastLayer);

        setNextInputValues(0);
        setStartWeights();
    }

    //not checked
    private void setStartWeights() {
        //start weights
        Random r = new Random();
        firstMiddleLayer.setWeights(new double[SIZE][SIZE]);
        middleLastLayer.setWeights(new double[SIZE][SIZE]);
        for (int i = 0; i < firstMiddleLayer.getParenNeurons().size(); i++) {
            System.out.print("i = " + i);
            for (int j = 0; j < firstMiddleLayer.getChieldNeurons().size(); j++) {
                firstMiddleLayer.setWeight(i, j, r.nextDouble());
                System.out.print("   j = " + j);
            }
            System.out.println("");
        }
        for (int i = 0; i < middleLastLayer.getParenNeurons().size(); i++) {
            for (int j = 0; j < middleLastLayer.getChieldNeurons().size(); j++) {
                middleLastLayer.setWeight(i, j, r.nextDouble());
            }
        }
    }

    private void trainIteration(int start) {
        forwardMove();
        converseMove();
        recountWeights();
    }

    private void setNextOutputValue(int start) {
        ((LastNeuron) middleLastLayer.getChieldNeurons().get(0)).setRealOutput(realOutput[start]);
    }

    private void setNextInputValues(int start) {
        for (Neuron n : firstMiddleLayer.getParenNeurons()) {
            n.setOutput(input[start++]);
        }
    }

    private void forwardMove() {
        double[][] weights = new double[3][3];
        weights[0][0] = firstMiddleLayer.getWeights()[0][0];
        weights[0][1] = firstMiddleLayer.getWeights()[1][0];
        weights[0][2] = firstMiddleLayer.getWeights()[2][0];
        weights[1][0] = firstMiddleLayer.getWeights()[1][1];
        weights[1][1] = firstMiddleLayer.getWeights()[2][1];
        weights[1][2] = firstMiddleLayer.getWeights()[3][1];
        weights[2][0] = firstMiddleLayer.getWeights()[2][2];
        weights[2][1] = firstMiddleLayer.getWeights()[3][2];
        weights[2][2] = firstMiddleLayer.getWeights()[4][2];

        List<Neuron> nList = firstMiddleLayer.getChieldNeurons();
        for (int i = 0; i < nList.size(); i++) {
            nList.get(i).findOutput(weights[i]);
        }

        double[] bufWeight = new double[3];
        bufWeight[0] = middleLastLayer.getWeights()[0][0];
        bufWeight[1] = middleLastLayer.getWeights()[1][0];
        bufWeight[2] = middleLastLayer.getWeights()[2][0];

        nList = middleLastLayer.getChieldNeurons();
        for (Neuron n : nList) {
            n.findOutput(bufWeight);
        }

    }

    private void converseMove() {

        List<Neuron> nList = middleLastLayer.getChieldNeurons();
        double[] bufWeight = new double[3];
        bufWeight[0] = middleLastLayer.getWeights()[0][0];
        bufWeight[1] = middleLastLayer.getWeights()[1][0];
        bufWeight[2] = middleLastLayer.getWeights()[2][0];

        for (Neuron n : nList) {
            n.findDelta(bufWeight);
        }
        double[][] weights = new double[3][3];
        weights[0][0] = firstMiddleLayer.getWeights()[0][0];
        weights[0][1] = firstMiddleLayer.getWeights()[1][0];
        weights[0][2] = firstMiddleLayer.getWeights()[2][0];
        weights[1][0] = firstMiddleLayer.getWeights()[1][1];
        weights[1][1] = firstMiddleLayer.getWeights()[2][1];
        weights[1][2] = firstMiddleLayer.getWeights()[3][1];
        weights[2][0] = firstMiddleLayer.getWeights()[2][2];
        weights[2][1] = firstMiddleLayer.getWeights()[3][2];
        weights[2][2] = firstMiddleLayer.getWeights()[4][2];

        nList = firstMiddleLayer.getChieldNeurons();
        for (int i = 0; i < nList.size(); i++) {
            nList.get(i).findDelta(weights[i]);
        }

    }

    private void recountWeights() {
        double[][] weights = new double[3][3];
        weights[0][0] = firstMiddleLayer.getWeights()[0][0];
        weights[0][1] = firstMiddleLayer.getWeights()[1][0];
        weights[0][2] = firstMiddleLayer.getWeights()[2][0];
        weights[1][0] = firstMiddleLayer.getWeights()[1][1];
        weights[1][1] = firstMiddleLayer.getWeights()[2][1];
        weights[1][2] = firstMiddleLayer.getWeights()[3][1];
        weights[2][0] = firstMiddleLayer.getWeights()[2][2];
        weights[2][1] = firstMiddleLayer.getWeights()[3][2];
        weights[2][2] = firstMiddleLayer.getWeights()[4][2];

        List<Neuron> nList = firstMiddleLayer.getChieldNeurons();
        for (int i = 0; i < nList.size(); i++) {
            double[] recWeightd = nList.get(i).recountWeights(weights[i]);
            for (int j = i; j < 3 + i; j++) {
                firstMiddleLayer.setWeight(j, i, recWeightd[j - i]);
            }
        }

        double[] bufWeight = new double[3];
        bufWeight[0] = middleLastLayer.getWeights()[0][0];
        bufWeight[1] = middleLastLayer.getWeights()[1][0];
        bufWeight[2] = middleLastLayer.getWeights()[2][0];

        nList = middleLastLayer.getChieldNeurons();
        for (Neuron n : nList) {
            double[] recWeights = n.recountWeights(bufWeight);
            for (int j = 0; j < 3; j++) {
                middleLastLayer.setWeight(j, 0, recWeights[j]);
            }
        }
    }

    private double norma(double[] arr1, double[] arr2) {
        double buf = 0;
        for (int i = 0; i < arr1.length; i++) {
            buf += Math.abs(arr1[i] - arr2[i]) * Math.abs(arr1[i] - arr2[i]);
        }
        return Math.sqrt(buf);
    }

    public void train2() {
        double norm;

        double[] res = new double[SIZE - 5];
        res[0] = realOutput[0];
        do {
            norm = 0;
            for (int i = 1; i < SIZE - 6; i++) {
                trainIteration(i);                
                res[i] = middleLastLayer.getChieldNeurons().get(0).getOutput();

//                System.out.println("eps = " + Math.abs(middleLastLayer.getChieldNeurons().get(0).getDelta()));
                norm += Math.abs(middleLastLayer.getChieldNeurons().get(0).getDelta())
                        * Math.abs(middleLastLayer.getChieldNeurons().get(0).getDelta());
                setNextInputValues(i);
                setNextOutputValue(i + 6);
            }
            System.out.println("norma = " + Math.sqrt(norm));            
        } while (Math.sqrt(norm) > EPS);
    }

    public void train() {

        double norm = 0;
        double koefTeila = 0;
        double koefTeilaBuf1 = 0;
        double koefTeilaBuf2 = 0;

        double[] res = new double[SIZE - 5];
        res[0] = realOutput[0];
        for (int i = 1; i < SIZE - 6; i++) {
            do {
                trainIteration(i);
                middleLastLayer.getChieldNeurons().get(0).getDelta();
            } while (Math.abs(middleLastLayer.getChieldNeurons().get(0).getDelta()) > EPS);
            res[i] = middleLastLayer.getChieldNeurons().get(0).getOutput();
//            System.out.println("out = " + middleLastLayer.getChieldNeurons().get(0).getOutput()
//                    + " real = " + realOutput[i + 6]);            
//            System.out.println("out = " + (Math.abs(middleLastLayer.getChieldNeurons().get(0).getOutput()            
//                    - realOutput[i + 6])));
            System.out.println("eps = " + Math.abs(middleLastLayer.getChieldNeurons().get(0).getDelta()));
            norm += Math.abs(middleLastLayer.getChieldNeurons().get(0).getDelta())
                    * Math.abs(middleLastLayer.getChieldNeurons().get(0).getDelta());
            setNextInputValues(i);
            setNextOutputValue(i + 6);
        }
//        System.out.println("norma = " + norma(res, realOutput));
        System.out.println("norma = " + Math.sqrt(norm));

    }
}
