/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

/**
 *
 * @author Will_and_Sara
 */
public class ExampleMonteCarlo {
    public static void main(String args[]){
        MonteCarlo();
    }
    public static void MonteCarlo(){
        //this is a very trivial example of creating a monte carlo using a
        //standard normal distribution
        Distributions.MethodOfMoments.Normal SN = new Distributions.MethodOfMoments.Normal();
        double[] output = new double[1000];
        java.util.Random r = new java.util.Random();
        int j = output.length;
        for(int i = 0; i < output.length; j++){
            output[j] =SN.GetInvCDF(r.nextDouble());
        }
        //output now contains 1000 random normally distributed values.
        
        //to evaluate the mean and standard deviation of the output
        //you can use Basic Product Moment Stats
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(output);
        System.out.println("Mean: " + BPM.GetMean());
        System.out.println("StDev:" + BPM.GetStDev());
        System.out.println("Sample Size: " + BPM.GetSampleSize());
        System.out.println("Minimum: " + BPM.GetMin());
        System.out.println("Maximum: " + BPM.GetMax());   
    }
}
