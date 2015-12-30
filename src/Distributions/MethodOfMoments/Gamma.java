/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.MethodOfMoments;

/**
 *
 * @author Q0HECWPL
 */
public class Gamma extends Distributions.ContinuousDistribution{
    private double _Alpha;
    private double _Beta;
    public Gamma(double[] data){
        //http://www.itl.nist.gov/div898/handbook/eda/section3/eda366b.htm
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(data);
        _Alpha = Math.pow((BPM.GetMean() / BPM.GetStDev()),2);
        _Beta = 1 / (BPM.GetStDev() / BPM.GetMean());
        SetPeriodOfRecord(BPM.GetSampleSize());
    }
    public Gamma(double Alpha, double Beta){
        _Alpha = Alpha;
        _Beta = Beta;
    }
    @Override
    public double GetInvCDF(double probability) {
        double xn = _Alpha/_Beta;
        double testvalue = GetCDF(xn);
        int i = 0;
        do{
            xn = xn - ((testvalue - probability)/GetPDF(xn));
            testvalue = GetCDF(xn);
            i+=1;
        }while(Math.abs(testvalue - probability)<=0.00000000000001 | i == 100);
        return xn;
    }
    @Override
    public double GetCDF(double value) {
        return SpecialFunctions.SpecialFunctions.IncompleteGamma(_Alpha, _Beta*value)/Math.exp(SpecialFunctions.SpecialFunctions.gammaln(_Alpha));
    }
    @Override
    public double GetPDF(double value) {
        return (((Math.pow(_Beta, _Alpha))*((Math.pow(value,_Alpha-1))*Math.exp(-_Beta*value))/Math.exp(SpecialFunctions.SpecialFunctions.gammaln(_Alpha))));
    }
}
