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
    private double TrapazoidalIntegration(double y1, double y2, double deltax){
        double deltay = 0;
        double rect = 0;
        if(y1>y2){
            deltay = y1-y2;
            rect = java.lang.Math.abs(y2*deltax);
        }else{
            deltay = y2-y1;
            rect = java.lang.Math.abs(y1*deltax);
        }
        double tri = (1/2)*(deltax*deltay);
        return rect + java.lang.Math.abs(tri);
    }
    private double FindArea(double a, double inc, double x){
        double x1 = GetInvCDF(a);
        double x2 = GetInvCDF(a+inc);
        while(x2>=x){
           x1 = x2;
           a += inc;
           x2 = GetInvCDF(a+inc);
        }
        double y1 = GetPDF(x1);
        double y2 = GetPDF(x2);
        double deltax = java.lang.Math.abs(x1-x2);
        double area = TrapazoidalIntegration(y1,y2,deltax);
        double interpvalue = (x-x1)/(x2-x1);
        a+=area*interpvalue;
        return a;
    }
    @Override
    public double GetCDF(double value) {
        //decide which method i want to use.  errfunction, the method i came up with in vb, or something else.
        if(value == _Mean){return .5;}
        double dist = value - _Mean;
        int stdevs = (int)java.lang.Math.floor(java.lang.Math.abs(dist/_StDev));
        double inc = 1/250;
        double a = 0.5;
        double a1 = 0.682689492137/2;
        double a2 = 0.954499736104/2;
        double a3 = 0.997300203937/2;
        double a4 = 0.999936657516/2;
        double a5 = 0.999999426687/2;
        double a6 = 0.999999998027/2;
        double a7 = (a-a6)/2;
        switch(stdevs){
            case 0:
                if(dist<0){a+=-a1;}
                return FindArea(a,inc,value);
            case 1:
                if(dist<0){
                    a-=a2;
                }else{
                    a+=a1;
                }
                return FindArea(a,inc,value);
            case 2:
                if(dist<0){
                    a-=a3;
                }else{
                    a+=a2;
                }
                return FindArea(a,inc,value);
            case 3:
                if(dist<0){
                    a-=a4;
                }else{
                    a+=a3;
                }
                return FindArea(a,inc,value);
            case 4:
                if(dist<0){
                    a-=a5;
                }else{
                    a+=a4;
                }
                return FindArea(a,inc,value);
            case 5:
                if(dist<0){
                    a-=a6;
                }else{
                    a+=a5;
                }
                return FindArea(a,inc,value);
            case 6:
                if(dist<0){
                    a-=a7;
                }else{
                    a+=a6;
                }
                return FindArea(a,inc,value);
            default:
                if(dist<0){
                    return 0;
                }else{
                    return 1;
                }
        }
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
