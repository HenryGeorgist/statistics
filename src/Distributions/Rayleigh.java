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
public class Rayleigh extends ContinuousDistribution{
    private double _Sigma;
    public Rayleigh(double sigma){
        _Sigma = sigma;
    }
    public Rayleigh(double[] data){
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(data);
        _Sigma = BPM.GetStDev();
    }
    @Override
    public double GetInvCDF(double probability) {
        return _Sigma * java.lang.Math.sqrt(-2*java.lang.Math.log(probability));
    }
    @Override
    public double GetCDF(double value) {
        return 1-(java.lang.Math.exp(-(java.lang.Math.pow(value, 2))/(2*(java.lang.Math.pow(_Sigma,2)))));
    }
    @Override
    public double GetPDF(double value) {
        return (value/(java.lang.Math.pow(_Sigma, 2)))* java.lang.Math.exp(-(java.lang.Math.pow(value, 2))/(2*(java.lang.Math.pow(_Sigma,2))));
    }
    
}
