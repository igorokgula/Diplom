/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks1;

import neuralnetworks1.networks.MultilayerNeuralNetwork;
import java.util.List;

/**
 *
 * @author Igorok
 */
public class BackpropagationFCNLearningAlgorithm implements LearningStrategy<MultilayerNeuralNetwork> {

    private double EPS = 0.00001;

    @Override
    public void train(MultilayerNeuralNetwork network, List<DataItem> data) {

        //init

        LearningAlgorithmConfig config = new LearningAlgorithmConfig();
        if (config.getBatchSize() < 1 || config.getBatchSize() > data.size()) {
            config.setBatchSize(data.size());
        }
        double currentError = Double.MAX_VALUE;
        double lastError = 0;
        int epochNumber = 0;
        long dtStart;


        int[] trainingIndices;
        do {
            lastError = currentError;
            dtStart = System.currentTimeMillis();

            //preparation for epoche
            trainingIndices = new int[data.size()];
            for (int i = 0; i < data.size(); i++) {
                trainingIndices[i] = i;
            }
            if (config.getBatchSize() > 0) {
                trainingIndices = Shuffle.shuffleArray(trainingIndices);
            }
        } while (epochNumber < config.getMaxEpoches()
                && currentError > config.getMinError()
                && Math.abs(currentError - lastError) > config.getMinErrorChange());

        int currentIndex = 0;
        do {
            double[][][] nablaWeights = new double[network.getLayers().length][][];
            double[][] nablaBiases = new double[network.getLayers().length][];
            for (int i = 0; i < network.getLayers().length; i++) {
                nablaBiases[i] = new double[network.getLayers()[i].getNeurons().length];
                nablaWeights[i] = new double[network.getLayers()[i].getNeurons().length][];
                for (int j = 0; j < network.getLayers()[i].getNeurons().length; j++) {
                    nablaBiases[i][j] = 0;
                    nablaWeights[i][j] = new double[network.getLayers()[i].getNeurons()[j].getWeights().length];
                    for (int k = 0; k < network.getLayers()[i].getNeurons()[j].getWeights().length; k++) {
                        nablaWeights[i][j][k] = 0;
                    }
                }
            }

            //process one batch
            for (int inBatchIndex = currentIndex; inBatchIndex < currentIndex + config.getBatchSize() && inBatchIndex < data.size(); inBatchIndex++) {
                //forward pass
                double[] realOutput = network.computeOutput(data.get(trainingIndices[inBatchIndex]).getInput());

                //backward pass, error propagation
                //last layer
                for (int j = 0; j < network.getLayers()[network.getLayers().length - 1].getNeurons().length; j++) {

                    network.getLayers()[network.getLayers().length - 1].getNeurons()[j].setdEdz(
                            config.getErrorFunction().calculatePartialDerivaitveByV2Index(data.get(inBatchIndex).getOutput(),
                            realOutput, j)
                            * network.getLayers()[network.getLayers().length - 1].getNeurons()[j].getActivationFunction().computeFirstDerivative(network.getLayers()[network.getLayers().length - 1].getNeurons()[j].getLastNET()));

                    nablaBiases[network.getLayers().length - 1][j] += config.getLearningRate()
                            * network.getLayers()[network.getLayers().length - 1].getNeurons()[j].getdEdz();
                    for (int i = 0; i < network.getLayers()[network.getLayers().length - 1].getNeurons()[j].getWeights().length; i++) {
                        nablaWeights[network.getLayers().length - 1][j][i] +=
                                config.getLearningRate() * (network.getLayers()[network.getLayers().length - 1].getNeurons()[j].getdEdz()
                                * (network.getLayers().length > 1
                                ? network.getLayers()[network.getLayers().length - 1 - 1].getNeurons()[i].getLastState()
                                : (double) data.get(inBatchIndex).getInput()[i])
                                + config.getRegularizationFactor()
                                * network.getLayers()[network.getLayers().length - 1].getNeurons()[j].getWeights()[i]
                                / data.size());
                    }
                }
                //.......................................ОБРАБОТКА ПОСЛЕДНЕГО СЛОЯ

                //hidden layers

                for (int hiddenLayerIndex = network.getLayers().length - 2; hiddenLayerIndex >= 0; hiddenLayerIndex--) {
                    for (int j = 0; j < network.getLayers()[hiddenLayerIndex].getNeurons().length; j++) {
                        network.getLayers()[hiddenLayerIndex].getNeurons()[j].setdEdz(0);
                        for (int k = 0; k < network.getLayers()[hiddenLayerIndex + 1].getNeurons().length; k++) {
                            double buf = network.getLayers()[hiddenLayerIndex].getNeurons()[j].getdEdz();
                            network.getLayers()[hiddenLayerIndex].getNeurons()[j].
                                    setdEdz(buf + network.getLayers()[hiddenLayerIndex + 1].getNeurons()[k].getWeights()[j]
                                    * network.getLayers()[hiddenLayerIndex + 1].getNeurons()[k].getdEdz());
                        }
                        double buf = network.getLayers()[hiddenLayerIndex].getNeurons()[j].getdEdz();
                        network.getLayers()[hiddenLayerIndex].getNeurons()[j].setdEdz(buf
                                * network.getLayers()[hiddenLayerIndex].getNeurons()[j].getActivationFunction().
                                computeFirstDerivative(
                                network.getLayers()[hiddenLayerIndex].getNeurons()[j].getLastNET()));

                        nablaBiases[hiddenLayerIndex][j] += config.getLearningRate()
                                * network.getLayers()[hiddenLayerIndex].getNeurons()[j].getdEdz();

                        for (int i = 0; i < network.getLayers()[hiddenLayerIndex].getNeurons()[j].getWeights().length; i++) {
                            nablaWeights[hiddenLayerIndex][j][i] += config.getLearningRate() * (network.getLayers()[hiddenLayerIndex].getNeurons()[j].getdEdz()
                                    * (hiddenLayerIndex > 0 ? network.getLayers()[hiddenLayerIndex - 1].getNeurons()[i].getLastState() : data.get(inBatchIndex).getInput()[i])
                                    + config.getRegularizationFactor() * network.getLayers()[hiddenLayerIndex].getNeurons()[j].getWeights()[i] / data.size());
                        }
                    }
                }

                //.......................................ОБРАБОТКА СКРЫТЫХ СЛОЕВ
            }

            //update weights and bias
            for (int layerIndex = 0; layerIndex < network.getLayers().length; layerIndex++) {
                for (int neuronIndex = 0; neuronIndex < network.getLayers()[layerIndex].getNeurons().length; neuronIndex++) {
                    network.getLayers()[layerIndex].getNeurons()[neuronIndex].
                            setBias(network.getLayers()[layerIndex].getNeurons()[neuronIndex].getBias() - nablaBiases[layerIndex][neuronIndex]);
                    for (int weightIndex = 0; weightIndex < network.getLayers()[layerIndex].getNeurons()[neuronIndex].getWeights().length; weightIndex++) {
                        double buf = network.getLayers()[layerIndex].
                                getNeurons()[neuronIndex].getWeights()[weightIndex];
                        network.getLayers()[layerIndex].getNeurons()[neuronIndex].
                                setWeightI(buf - nablaWeights[layerIndex][neuronIndex][weightIndex], weightIndex);
                    }
                }
            }

            currentIndex += config.getBatchSize();


            //recalculating error on all data
            //real error
            currentError = 0;
            for (int i = 0; i < data.size(); i++) {
                double[] realOutput = network.computeOutput(data.get(i).getInput());
                currentError += config.getErrorFunction().calculate(data.get(i).getOutput(), realOutput);
            }
            currentError *= 1d / data.size();
            //regularization term
            if (Math.abs(config.getRegularizationFactor() - 0d) > EPS) {
                double reg = 0;
                for (int layerIndex = 0; layerIndex < network.getLayers().length; layerIndex++) {
                    for (int neuronIndex = 0; neuronIndex < network.getLayers()[layerIndex].getNeurons().length; neuronIndex++) {
                        for (int weightIndex = 0; weightIndex < network.getLayers()[layerIndex].getNeurons()[neuronIndex].getWeights().length; weightIndex++) {
                            reg += network.getLayers()[layerIndex].getNeurons()[neuronIndex].getWeights()[weightIndex]
                                    * network.getLayers()[layerIndex].getNeurons()[neuronIndex].getWeights()[weightIndex];
                        }
                    }
                }
                currentError += config.getRegularizationFactor() * reg / (2 * data.size());
            }
            epochNumber++;
            System.out.println("Eposh #" + epochNumber
                    + " finished; current error is " + currentError
                    + "; it takes: "
                    + (System.currentTimeMillis() - dtStart));

        } while (currentIndex < data.size());

    }
}
