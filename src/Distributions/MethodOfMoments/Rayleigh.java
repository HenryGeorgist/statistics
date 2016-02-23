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
public class Rayleigh extends ContinuousDistribution{
    private double _Sigma;
    public double GetSigma(){return _Sigma;}
    public Rayleigh(){
        //for reflection
        _Sigma = 1;
    }
    public Rayleigh(double sigma){
        _Sigma = sigma;
    }
    public Rayleigh(double[] data){
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(data);
        _Sigma = BPM.GetStDev();
        SetPeriodOfRecord(BPM.GetSampleSize());
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

    @Override
    public ArrayList<ContinuousDistributionError> Validate() {
        ArrayList<ContinuousDistributionError> errs = new ArrayList<>();
        if(_Sigma<=0){errs.add(new ContinuousDistributionError("Sigma cannot be less than or equal to zero in the Rayleigh distribuiton."));}
        return errs;
    }
}
