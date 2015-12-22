/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.MethodOfMoments;

import Distributions.ContinuousDistribution;

/**
 *
 * @author Q0HECWPL
 */
public class LogNormal extends ContinuousDistribution{
    private double _Mean;
    private double _StDev;
    private int _SampleSize;
    public LogNormal(double mean, double stdev, int samplesize){
        _Mean = mean;
        _StDev = stdev;
        _SampleSize = samplesize;
    }
    @Override
    public double GetInvCDF(double probability) {
        Normal z = new Normal(_Mean,_StDev);
        return java.lang.Math.pow(10,z.GetInvCDF(probability));
    }
    @Override
    public double GetCDF(double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public double GetPDF(double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public double Bullentin17BConfidenceLimit(double probability, double alphaValue){
        Normal sn = new Normal(0,1);
        double k = sn.GetInvCDF(probability); 
        double z = sn.GetInvCDF(alphaValue);
        double zSquared = java.lang.Math.pow(z, 2);
        double kSquared = java.lang.Math.pow(k, 2);
        double Avalue = (1-(zSquared)/2/(_SampleSize-1));
        double Bvalue = (kSquared) - ((zSquared)/_SampleSize);
        double RootValue = java.lang.Math.sqrt(kSquared-(Avalue*Bvalue));
        if(alphaValue>.5){
            return java.lang.Math.pow(10,_Mean + _StDev*(k + RootValue)/Avalue);
        }else{
            return java.lang.Math.pow(10,_Mean + _StDev*(k - RootValue)/Avalue);
        }
    }
}
