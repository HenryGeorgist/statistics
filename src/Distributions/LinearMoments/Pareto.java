/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.LinearMoments;

/**
 *
 * @author Will_and_Sara
 */
public class Pareto extends Distributions.ContinuousDistribution{
    private double _K;
    private double _Alpha;
    private double _Xi;
    public Pareto(){
        //for reflection
        _K = 0;
        _Alpha = 0;
        _Xi = 0;
    }
    public Pareto(double[] data){
        MomentFunctions.LinearMoments LM = new MomentFunctions.LinearMoments(data);
        if(LM.GetL2()==0){
            _K = (1 - 3 * LM.GetT3()) / (1 + LM.GetT3());
            _Alpha = (1 + _K) * (2 + _K) * LM.GetL2();
            _Xi = LM.GetL1() - (2 + _K) * LM.GetL2();
            SetPeriodOfRecord(LM.GetSampleSize());
        }else{
            //coefficient of variation cannot be zero.
        }
    }
    public Pareto(double K, double Alpha, double Xi){
        _K = K;
        _Alpha = Alpha;
        _Xi = Xi;
    }
    @Override
    public double GetInvCDF(double probability) {
        if (_K != 0){
            return _Xi + (_Alpha * (1 - java.lang.Math.pow(1 - probability,_K)) / _K);
        }else{
            return _Xi - _Alpha * java.lang.Math.log(1 - probability);
        }
    }
    @Override
    public double GetCDF(double value) {
        return 1 - java.lang.Math.exp(-Y(value));
    }
    @Override
    public double GetPDF(double value) {
        return (1/_Alpha) * java.lang.Math.exp(-(1 - _K) * Y(value));
    }
    public double Y(double value){
        if(_K != 0){
            return (1/-_K ) * java.lang.Math.log(1 - _K * (value - _Xi) / _Alpha);
        }else{
            return (value - _Xi) / _Alpha;
        }       
    }
}
