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
public class Gumbel extends Distributions.ContinuousDistribution{
    private double _Alpha;
    private double _Xi;
    public Gumbel(double[] data){
        MomentFunctions.LinearMoments LM = new MomentFunctions.LinearMoments(data);
        _Alpha = LM.GetL2() / java.lang.Math.log(2);
        _Xi = LM.GetL1() - 0.57721566490153287 * _Alpha;
        SetPeriodOfRecord(LM.GetSampleSize());
    }
    @Override
    public double GetInvCDF(double probability) {
        return _Xi - _Alpha * java.lang.Math.log(-java.lang.Math.log(probability));
    }
    @Override
    public double GetCDF(double value) {
        return java.lang.Math.exp(-java.lang.Math.exp(-(value - _Xi) / _Alpha));
    }
    @Override
    public double GetPDF(double value) {
        return (1/_Alpha) * java.lang.Math.exp(-(value - _Xi) / _Alpha) * java.lang.Math.exp(-java.lang.Math.exp(-(value - _Xi) / _Alpha));
    }
}
