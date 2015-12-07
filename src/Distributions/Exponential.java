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
public class Exponential extends ContinuousDistribution{
    private double _Lambda;
    public Exponential(double lambda){
        _Lambda = lambda;
    }
    public Exponential(double[] data){
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(data);
        _Lambda = 1/BPM.GetMean();
    }
    @Override
    public double GetInvCDF(double probability) {
        return java.lang.Math.log(probability)/_Lambda;
    }
    @Override
    public double GetCDF(double value) {
        return 1- java.lang.Math.exp(-_Lambda*value);
    }
    @Override
    public double GetPDF(double value) {
        if(value<0){
            return 0;
        }else{
            return _Lambda * java.lang.Math.exp(-_Lambda*value);
        }
    }
    
}
