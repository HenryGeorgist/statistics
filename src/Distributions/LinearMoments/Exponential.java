/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.LinearMoments;

/**
 *
 * @author Q0HECWPL
 */
public class Exponential extends Distributions.ContinuousDistribution{
    private double _Alpha;
    private double _Xi;
    public Exponential(){
        //for reflection
        _Alpha = 0;
        _Xi = 0;
    }
    public Exponential(double[] data){
        MomentFunctions.LinearMoments LM = new MomentFunctions.LinearMoments(data);
        _Alpha = 2 * LM.GetL2();
        _Xi = LM.GetL1() - _Alpha;
        SetPeriodOfRecord(LM.GetSampleSize());
    }
    public Exponential(double Alpha, double Xi){
        _Alpha = Alpha;
        _Xi = Xi;
    }
    @Override
    public double GetInvCDF(double probability) {
        return _Xi - _Alpha * java.lang.Math.log(1 - probability);
    }
    @Override
    public double GetCDF(double value) {
        return 1 - java.lang.Math.exp(-(value - _Xi) / _Alpha);
    }
    @Override
    public double GetPDF(double value) {
        return (1/_Alpha) * java.lang.Math.exp(-(value - _Xi) / _Alpha);
    }
}
