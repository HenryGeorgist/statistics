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
public class Triangular extends ContinuousDistribution{
    private double _Min;
    private double _Max;
    private double _MostLikely;
    public double GetMin(){return _Min;}
    public double GetMax(){return _Max;}
    public double GetMostLikely(){return _MostLikely;}
    public Triangular(){
        //_SampleSize = 0; for reflection
        _Min = 0;
        _Max = 0;
        _MostLikely = 0;
    }
    public Triangular(double min, double max, double mostlikely){
        //_SampleSize = 0;
        _Min = min;
        _Max = max;
        _MostLikely = mostlikely;
    }
    public Triangular(double[] data){
        MomentFunctions.ProductMoments PM = new MomentFunctions.ProductMoments(data);
        //Simple Method:
//        _Min = PM.GetMin();
//        _Max = PM.GetMax();
//        _MostLikely = PM.GetMean();
        
        //Alternate Method from Ben Chaon
        double sqrt2 = java.lang.Math.sqrt(2);
        double sqrt3 = java.lang.Math.sqrt(3);
        double a3 = PM.GetSkew();
        double b3;
        double angle;
        double aa;
        double bb;
        if(8-a3*a3<0){
            a3 = java.lang.Math.sin(a3)*2*sqrt2;
            b3 = 0;
        }else{
            b3 = java.lang.Math.sqrt(8-a3*a3);
        }
        angle = java.lang.Math.atan2(b3, a3);
        aa = java.lang.Math.cos(angle/3.0);
        bb = java.lang.Math.sin(angle/3.0);
        _Min = (PM.GetMean()+sqrt2*PM.GetStDev()*(aa-sqrt3*bb));
        _MostLikely = (PM.GetMean()-2*sqrt2*PM.GetStDev()*aa);
        _Max = (PM.GetMean()+sqrt2*PM.GetStDev()*(aa+sqrt3*bb));
        SetPeriodOfRecord(PM.GetSampleSize());
    }
    @Override
    public double GetInvCDF(double probability) {
        double a = _MostLikely - _Min;
        double b = _Max - _MostLikely;
        if (probability <= 0){
            return _Min;
        }else if(probability < (a/(_Max - _Min))){
            return _Min + java.lang.Math.sqrt(probability * (_Max - _Min) * a);
        }else if(probability < 1){
            return _Max - java.lang.Math.sqrt((1-probability)*(_Max - _Min)* b);
        }else{
            return _Max;
        }
    }
    @Override
    public double GetCDF(double value) {
        if(value<_Min){return 0;}
        if(value<_MostLikely){return (java.lang.Math.pow((value-_Min),2)/(_Max-_Min)*(_MostLikely-_Min));}
        if(value<=_Max){return 1-(java.lang.Math.pow((_Max-value),2)/(_Max-_Min)*(_Max-_MostLikely));}
        return 1;
    }
    @Override
    public double GetPDF(double value) {
        if(value<_Min){return 0;}
        if(value<_MostLikely){return (2*(value-_Min)/(_Max-_Min)*(_MostLikely-_Min));}
        if(value<=_Max){return (2*(_Max-value)/(_Max-_Min)*(_Max-_MostLikely));}
        return 0;
    }
}
