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
public class LogPearsonIII extends ContinuousDistribution{
    private double _Mean;
    private double _StDev;
    private double _Skew;
    public LogPearsonIII(double mean, double stdev, double skew){
        _Mean = mean;
        _StDev = stdev;
        _Skew = skew;
    }
    public LogPearsonIII(double[] data){
        for(int i = 0; i<data.length;i++){
            data[i]= java.lang.Math.log10(data[i]);
        }
        MomentFunctions.ProductMoments PM = new MomentFunctions.ProductMoments(data);
        _Mean = PM.GetMean();
        _StDev = PM.GetStDev();
        _Skew = PM.GetSkew();
    }
    @Override
    double GetInvCDF(double probability) {
        Normal sn = new Normal();
        double z = sn.GetInvCDF(probability);
        double k = (2/_Skew)*(java.lang.Math.pow(((z-_Skew)/6)* _Skew/6+1,3)-1);
        double logflow = _Mean + (k*_StDev);
        return java.lang.Math.pow(10, logflow);
    }
    @Override
    double GetCDF(double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    double GetPDF(double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
