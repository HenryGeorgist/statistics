/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.MethodOfMoments;

import Distributions.ContinuousDistribution;
import Distributions.ContinuousDistributionError;
import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class LogPearsonIII extends ContinuousDistribution{
    private double _Mean;
    private double _StDev;
    private double _Skew;//cannot be zero
    public LogPearsonIII(){
        //for reflection
        _Mean = 0;
        _StDev = 1;
        _Skew = 0;
    }
    public LogPearsonIII(double mean, double stdev, double skew,int sampleSize){
        _Mean = mean;
        _StDev = stdev;
        _Skew = skew;
        SetPeriodOfRecord(sampleSize);
    }
    public LogPearsonIII(double[] data){
        for(int i = 0; i<data.length;i++){
            data[i]= java.lang.Math.log10(data[i]);
        }
        MomentFunctions.ProductMoments PM = new MomentFunctions.ProductMoments(data);
        _Mean = PM.GetMean();
        _StDev = PM.GetStDev();
        _Skew = PM.GetSkew();
        SetPeriodOfRecord(PM.GetSampleSize());
    }
    @Override
    public double GetInvCDF(double probability) {
        if(_Skew == 0 ){
            Normal zeroSkewNorm = new Normal(_Mean,_StDev);
            double logflow = zeroSkewNorm.GetInvCDF(probability);
            return java.lang.Math.pow(10,logflow);
        }else{
            Normal sn = new Normal();
            double z = sn.GetInvCDF(probability);
            double k = (2/_Skew)*(java.lang.Math.pow((z-_Skew/6.0)* _Skew/6.0+1,3)-1);
            double logflow = _Mean + (k*_StDev);
            return java.lang.Math.pow(10, logflow); 
        }
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
        double z1 =sn.GetInvCDF(probability);
        double k = 0; 
        if(_Skew == 0){
            k = z1;
        }else{
            k = (2/_Skew)*(java.lang.Math.pow((z1-_Skew/6.0)* _Skew/6.0+1,3)-1);
        }
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
    @Override
    public ArrayList<ContinuousDistributionError> Validate() {
        ArrayList<ContinuousDistributionError> errors = new ArrayList<>();
        if(_StDev<=0){errors.add(new ContinuousDistributionError("Standard of Deviation must be greater than 0"));}
        return errors;
    }
}
