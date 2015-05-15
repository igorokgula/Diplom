/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DispersionAnalysis;

/**
 *
 * @author Igorok
 */
public class DispersionTwoDimension {
    
    int n;
    int m;
    private double[][] x;
    private double alpha;
    
    private double sumOfSquares;
    private double squareOfSum;
    private double fullSum;

    public DispersionTwoDimension(double[][] x, double alpha) {
        this.x = x;
        m = this.x.length;
        n = this.x[0].length;
        this.alpha = alpha;
    }
    
    public boolean isZeroHypothesisTrue() {
        boolean result = false;
        findF();
        //todo
        return result;
    }
    
    private double findF() {
        double result = (findQ1() / (m - 1)) / (findQ2() / (m*n - m));
        System.out.println(result);
        return result;
    }
    
    private double findQ() {
        return findQ1() + findQ2();
    }
    
    private double findQ1() {
        double result = 0;
        squareOfSum = squareOfSum();
        fullSum = fullSum();
        result = squareOfSum/n - (fullSum*fullSum)/(n*m);
        System.out.println(result);
        return result;
    }
    
    private double findQ2() {
        double result = 0;                
        sumOfSquares = sumOfSquare();
        result = sumOfSquares - squareOfSum/n;
        System.out.println(result);
        return result;        
    }
    
    private double fullSum() {
        double sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum += x[i][j];
            }
        }
        System.out.println(" full" + sum);
        return sum;
    }
    
    private double sumOfSquare() {
        double sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum += x[i][j]*x[i][j];
            }
        }
        System.out.println( "sumOfSq" + sum);
        return sum;
    }
    
    private double squareOfSum() {
        double sum = 0;
        for (int i = 0; i < m; i++) {
            double temp = 0;
            for (int j = 0; j < n; j++) {
                temp += x[i][j];
            }
            sum += temp*temp;
            temp = 0;
        }
        System.out.println("square" + sum);
        return sum;
    }        
    
    public static void main(String[] args) {
        double[][] x = {{200, 140, 170, 145, 165}, {190, 150, 210, 150, 150},
            {230, 190, 200, 190, 200}, {150, 170, 150, 170, 180}};
        DispersionTwoDimension d = new DispersionTwoDimension(x, 0.05);
        d.isZeroHypothesisTrue();
    }
    
}
