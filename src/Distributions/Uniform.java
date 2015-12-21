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
public class Uniform extends ContinuousDistribution{
    private double _Min;
    private double _Max;
    public double GetMin(){return _Min;}
    public double GetMax(){return _Max;}
    public Uniform(){
        _Min = 0;
        _Max = 0;
    }
    public Uniform(double min, double max){
        _Min = min;
        _Max = max;
    }
    public Uniform(double[] data){
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(data);
        _Min = BPM.GetMin();
        _Max = BPM.GetMax();
        //alternative method
        //double dist = java.lang.Math.sqrt(3*BPM.GetSampleVariance());
        //_Min = BPM.GetMean() - dist;
        //_Max = BPM.GetMean() + dist;
    }
    @Override
    public double GetInvCDF(double probability) {
        return _Min + ((_Max - _Min)* probability);
    }
    @Override
    public double GetCDF(double value) {
        if(value<_Min){
            return 0;
        }else if(value <=_Max){
            return (value - _Min)/(_Min - _Max);
        }else{
            return 1;
        }
    }
    @Override
    public double GetPDF(double value) {
        if(value < _Min){
            return 0;
        }else if(value <= _Max){
            return 1/(_Min-_Max);
        }else{
            return 0;
        }
    }
}
