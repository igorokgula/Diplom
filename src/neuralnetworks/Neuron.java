/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworks;

import neuralnetworks.function.Function;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Igorok
 */
public class Neuron {

    private final Function func;
    private double output;
    private List<Neuron> children;
    private List<Neuron> parents;
    private double delta;
    private static List<ConnectTwo> weights;
    private final static double koef = 1;
    //e = x1*w1 + x2*w2
    private double e;

    public Neuron(Function func, List<Neuron> children, List<Neuron> parents) {
        this.func = func;
        this.children = children;
        this.parents = parents;
    }

    public List<Neuron> getChildren() {
        return children;
    }

    public void addChield(Neuron n) {
        if (this.children != null) {
            this.children.add(n);
        } else {
            this.children = new ArrayList<>();
            this.children.add(n);
        }
    }

    public void addParent(Neuron n) {
        if (this.parents != null) {
            this.parents.add(n);
        } else {
            this.parents = new ArrayList<>();
            this.parents.add(n);
        }
    }

    public void setChildren(List<Neuron> children) {
        this.children = children;
    }

    public List<Neuron> getParents() {
        return parents;
    }

    public void setParents(List<Neuron> parents) {
        this.parents = parents;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public static List<ConnectTwo> getWeights() {
        return weights;
    }

    public static void setWeights(List<ConnectTwo> weights) {
        Neuron.weights = weights;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getOutput() {
        return output;
    }

    public void findOutput(double[] weights) {
        double buf = 0;
        if (parents != null) {
            int i = 0;
            for (Neuron n : parents) {
                buf += n.getOutput() * weights[i++];
            }
            this.e = buf;
            this.output = func.compute(buf);
        }
    }

    public void findDelta(double[] weights) {
        double buf = 0;
        if (children != null) {
            int i = 0;
            for (Neuron n : children) {
                buf += n.getDelta() * weights[i++];
            }
        } else {
            System.err.print("EXEPTION");
        }
        this.delta = buf;
    }

    public double[] recountWeights(double[] weights) {
        double buf;
        double[] res = new double[weights.length];
        if (parents != null) {
            int i = 0;
            for (Neuron n : parents) {
                buf = weights[i];
                buf += koef * this.delta * this.func.computeFirstDerivative(e) * n.getOutput();                
                res[i++] = buf;                
            }
        } else {
            System.err.print("EXEPTION");
        }
        return res;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.output);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Neuron other = (Neuron) obj;
        if (Double.doubleToLongBits(this.output) != Double.doubleToLongBits(other.output)) {
            return false;
        }
        return true;
    }
}
