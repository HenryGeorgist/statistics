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
public class Gumbel extends ContinuousDistribution{
    private double _Mu;
    private double _Beta;
    public Gumbel(){
        _Mu = 0;
        _Beta = 0;
    }
    public Gumbel(double mu, double beta){
        _Mu = mu;
        _Beta = beta;
    }
    public Gumbel(double[] data){
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(data);
        _Beta = java.lang.Math.PI/(BPM.GetStDev()*java.lang.Math.sqrt(6));
        _Mu = BPM.GetMean()-_Beta*0.57721566490153287;
    }
    @Override
    public double GetInvCDF(double probability) {
        return (_Mu-(_Beta*(java.lang.Math.log(java.lang.Math.log(probability)))));
    }
    @Override
    public double GetCDF(double value) {
        return java.lang.Math.exp(-java.lang.Math.exp(-(value-_Mu)/_Beta));
    }
    @Override
    public double GetPDF(double value) {
        double z = (value-_Mu)/_Beta;
        return (1/_Beta)*java.lang.Math.exp(-(z+java.lang.Math.exp(-z)));
    }
    
}
