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
public class LogPearsonIII extends Distributions.ContinuousDistribution{
    private double _Alpha;
    private double _Beta;
    private double _Xi;
    public LogPearsonIII(){
        //for reflection
        _Alpha = 0;
        _Beta = 0;
        _Xi = 0;
    }
    public LogPearsonIII(double[] data){
        MomentFunctions.LinearMoments LM = new MomentFunctions.LinearMoments(data);
        SetPeriodOfRecord(LM.GetSampleSize());
        double z = 0;
        double a = 0;
        double abst3 = java.lang.Math.abs(LM.GetT3());
        if(abst3>0 && abst3<(1/3)){
            z = 3 * java.lang.Math.PI * (LM.GetT3()*LM.GetT3());
            a = ((1+(0.2906*z))/(z+(0.1882*(z*z))+(0.0442*(z*z*z))));
        }else if(abst3<1){
            z = 1 - java.lang.Math.abs(LM.GetT3());
            a = ((0.36067 * z - (0.59567 * (z * z)) + (0.25361 * (z*z*z))) / (1 - 2.78861 * z + (2.56096 * (z*z)) - (0.77045 * (z*z*z))));
        }else{
            //no solution because t3 is greater than or equal to 1.
        }
        double gamma = (2/java.lang.Math.sqrt(a)) * java.lang.Math.signum(LM.GetT3());
        double sigma = (12*java.lang.Math.sqrt(java.lang.Math.PI) * java.lang.Math.sqrt(a) * java.lang.Math.exp(SpecialFunctions.SpecialFunctions.gammaln(a)))/java.lang.Math.exp(SpecialFunctions.SpecialFunctions.gammaln(a+0.5)); //need gammaln
        if(gamma!=0){
            _Alpha = 4/(gamma*gamma);
            _Xi = LM.GetL1() - ((2* sigma)/gamma);
            _Beta = 0.5*sigma*java.lang.Math.abs(gamma);
        }else{
            //normal distribution fits better.
        }
    }
    public LogPearsonIII(double alpha, double beta, double xi){
        _Alpha = alpha;
        _Beta = beta;
        _Xi = xi;
    }
    @Override
    public double GetInvCDF(double probability) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public double GetCDF(double value) {
        return 1 - (SpecialFunctions.SpecialFunctions.IncompleteGamma(_Alpha, ((_Xi - value) / _Beta)) / Math.exp(SpecialFunctions.SpecialFunctions.gammaln(_Alpha)));
//        If _gamma < 0 Then
//            Return 1 - (incompletegammalower(_alpha, ((_xi - value) / _beta)) / Math.Exp(gammaln(_alpha)))
//        Else
//            Return (incompletegammalower(_alpha, ((value - _xi) / _beta)) / Math.Exp(gammaln(_alpha)))
//        End If
    }
    @Override
    public double GetPDF(double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
