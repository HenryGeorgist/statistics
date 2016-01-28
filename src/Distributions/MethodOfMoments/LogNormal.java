/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.MethodOfMoments;

import Distributions.ContinuousDistribution;

/**
 *
 * @author Will_and_Sara
 */
public class LogNormal extends ContinuousDistribution{
    private double _Mean;
    private double _StDev;
    public LogNormal(){
        //for reflection
        _Mean = 0;
        _StDev = 1;
    }
    public LogNormal(double mean, double stdev, int samplesize){
        _Mean = mean;
        _StDev = stdev;
        SetPeriodOfRecord(samplesize);
    }
    /**
     *This takes an input array of sample data, calculates the log base 10 of the data, then calculates the mean and standard deviation of the log data.
     * @param data the sampled data (in linear space)
     */
    public LogNormal(double[] data){
        for(int i = 0 ; i< data.length;i++){
            data[i] = Math.log10(data[i]);
        }
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(data);
        _Mean = BPM.GetMean();
        _StDev = BPM.GetStDev();
        SetPeriodOfRecord(BPM.GetSampleSize());
    }
    @Override
    public double GetInvCDF(double probability) {
        Normal z = new Normal(_Mean,_StDev);
        return java.lang.Math.pow(10,z.GetInvCDF(probability));
    }
    @Override
    public double GetCDF(double value) {
        Distributions.MethodOfMoments.Normal n = new Distributions.MethodOfMoments.Normal(_Mean,_StDev);
        return n.GetCDF(java.lang.Math.log10(value));
    }
    @Override
    public double GetPDF(double value) {
        Distributions.MethodOfMoments.Normal n = new Distributions.MethodOfMoments.Normal(_Mean,_StDev);
        return n.GetPDF(java.lang.Math.log10(value));
    }
    public double Bullentin17BConfidenceLimit(double probability, double alphaValue){
        Normal sn = new Normal(0,1);
        double k = sn.GetInvCDF(probability); 
        double z = sn.GetInvCDF(alphaValue);
        double zSquared = java.lang.Math.pow(z, 2);
        double kSquared = java.lang.Math.pow(k, 2);
        double Avalue = (1-(zSquared)/2/(GetPeriodOfRecord()-1));
        double Bvalue = (kSquared) - ((zSquared)/GetPeriodOfRecord());
        double RootValue = java.lang.Math.sqrt(kSquared-(Avalue*Bvalue));
        if(alphaValue>.5){
            return java.lang.Math.pow(10,_Mean + _StDev*(k + RootValue)/Avalue);
        }else{
            return java.lang.Math.pow(10,_Mean + _StDev*(k - RootValue)/Avalue);
        }
    }
}
