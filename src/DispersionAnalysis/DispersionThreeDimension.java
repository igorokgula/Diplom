/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DispersionAnalysis;

/**
 *
 * @author Igorok
 */
public class DispersionThreeDimension {
    int n;    
    int l;
    int m;
    private double[][][] x;
    private double alpha;
    
    private double q, q1, q2, q3, q4;    

    public DispersionThreeDimension(double[][][] x, double alpha) {
        this.x = x;
        m = this.x.length;
        l = this.x[0].length;
        n = this.x[0][0].length;
        this.alpha = alpha;
    }
    
    private void findQ() {
        
        double result = 0;
        double res11 = 0;
        double res12 = 0;
        double res14 = 0;
        double res24 = 0;
        for (int i = 0; i < m; i++) {
            double temp = 0;
            for (int j = 0; j < l; j++) {                
                double temp1 = 0;
                for (int k = 0; k < n; k++) {
                    temp += x[i][j][k];
                    res12 += x[i][j][k];
                    res14 += x[i][j][k]*x[i][j][k];
                    temp1 += x[i][j][k];
                }
                res24 += temp1*temp1;
                temp1 = 0;
            }
            res11 += temp*temp;
            temp = 0;
        }
        q1 = res11/(l*n) - (res12*res12)/(m*l*n);        
        q4 = res14 - res24/n;
        q = res14 - (res12*res12)/(m*l*n);
        
        q2 = 0;
        for (int j = 0; j < l; j++) {
            double temp = 0;
            for (int i = 0; i < m; i++) {
                for (int k = 0; k < n; k++) {
                    temp += x[i][j][k];
                }
            }
            q2 += temp*temp;
            temp = 0;
        }
        q2 = q2/(m*n) - (res12*res12)/(m*l*n);
        q3 = q - q1 - q2 - q4;                                               
    }
    
   public void findF() {
       findQ();
//       findFA();
//       findFB();
//       findFAB();
       System.out.println(q1);
       System.out.println(q2);
       System.out.println(q3);
       System.out.println(q4);
       
       System.out.println(findFA());
       System.out.println(findFB());
       System.out.println(findFAB());
   }
    
   private double findFA() {
       return (q1 * (m*l*n - m*l))/ (q4 * (m - 1));
   }
   
   private double findFB() {
       return (q2 * (m*l*n - m*l))/ (q4 * (l - 1));
   }
   
   private double findFAB() {
       return (q3 * (m*l*n - m*l))/ (q4 * (l - 1)*(m - 1));
   }
    
   public static void main(String[] args) {
       double[][][] x = {{ {530, 540, 550}, {600, 620, 580} }, { {490, 510, 520}, {550, 540, 560} }, { {430, 420, 450}, {470, 460, 430} }};
       DispersionThreeDimension d = new DispersionThreeDimension(x, 0.05);
       d.findF();
   }
   
}
