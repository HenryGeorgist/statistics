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
        //_SampleSize = 0;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public double GetPDF(double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
