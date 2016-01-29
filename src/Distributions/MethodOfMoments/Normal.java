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
public class Normal extends ContinuousDistribution {
    public double _Mean;
    public double _StDev;
    public double GetMean(){return _Mean;}
    public double GetStDev(){return _StDev;}
    /**
     *Creates a standard normal distribution
     */
    public Normal(){
        _Mean = 0;
        _StDev = 1;
    }
    /**
     *Creates a normal distribution based on the user defined mean and standard deviation
     * @param m the mean of the distribution
     * @param sd the standard deviation of the distribution
     */
    public Normal(double m, double sd){
        _Mean = m;
        _StDev = sd;
    }
    /**
     *Creates a normal distribution based on input data using the standard method of moments.
     * @param data an array of double data.
     */
    public Normal(double[] data){
        MomentFunctions.BasicProductMoments bpm = new MomentFunctions.BasicProductMoments(data);
        _Mean = bpm.GetMean();
        _StDev = bpm.GetStDev();
        SetPeriodOfRecord(bpm.GetSampleSize());
    }
    @Override
    public double GetInvCDF(double probability) {
        int i;
        double x;
        double c0 = 2.515517;
        double c1 = .802853;
        double c2 = .010328;
        double d1 = 1.432788;
        double d2 = .189269;
        double d3 = .001308;
        double q;
        q = probability;
        if(q==.5){return _Mean;}
        if(q<=0){q=.000000000000001;}
        if(q>=1){q=.999999999999999;}
        if(q<.5){i=-1;}else{
            i=1;
            q = 1-q;
        }
        double t = Math.sqrt(Math.log(1/Math.pow(q, 2)));
        x = t-(c0+c1*t+c2*(Math.pow(t,2)))/(1+d1*t+d2*Math.pow(t,2)+d3*Math.pow(t,3));
        x = i*x;
        return (x*_StDev)+_Mean;
    }
    @Override
    public double GetCDF(double value) {
        //decide which method i want to use.  errfunction, the method i came up with in vb, or something else.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public double GetPDF(double value) {
        return (1/Math.sqrt(2*Math.PI)*Math.pow(_StDev,2.0))*Math.exp((-(Math.pow(value-_Mean, 2)/(2*Math.pow(_StDev, 2)))));
    }

    @Override
    public ArrayList<ContinuousDistributionError> Validate() {
        ArrayList<ContinuousDistributionError> errors = new ArrayList<>();
        if(_StDev<=0){errors.add(new ContinuousDistributionError("Standard of Deviation must be greater than 0"));}
        return errors;
    }
}
