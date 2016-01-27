/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.MethodOfMoments;

/**
 *
 * @author Will_and_Sara
 */
public class Beta extends Distributions.ContinuousDistribution {
    private double _Alpha;
    private double _Beta;
    public Beta(){
        //for relfection
        _Alpha = 0;
        _Beta = 0;
    }
    public Beta(double[] data){
        MomentFunctions.BasicProductMoments BPM = new MomentFunctions.BasicProductMoments(data);
        double x = BPM.GetMean();
        double v = BPM.GetStDev();
        if (v < (x * (1 - x))){
            _Alpha = x * (((x * (1 - x)) / v) - 1);
            _Beta = (1 - x) * (((x * (1 - x)) / v) - 1);
        }else{
            //Beta Fitting Error: variance is greater than mean*(1-mean), this data is not factorable to a beta distribution
        }
    }
    public Beta(double Alpha, double Beta){
        _Alpha = Alpha;
        _Beta = Beta;
    }
    @Override
    public double GetInvCDF(double probability) {
        //use bisection since the shape can be bimodal.
        double value = 0.5; //midpoint of the beta output range
        double testvalue = GetCDF(value);
        double inc = 0.5;
        int n = 0;
        do{
            if (testvalue > probability){
                //incriment a half incriment down
                inc = inc / 2;
                value -= inc;
                testvalue = GetCDF(value);
            }else{
                //incriment a half incriment up
                inc = inc / 2;
                value += inc;
                testvalue = GetCDF(value);
            }
            n += 1;
        } while (Math.abs(testvalue - probability) > 0.000000000000001 | n != 100);
        return value;
    }
    @Override
    public double GetCDF(double value) {//not sure this is right, technically it is the regularized incomplete beta.
        return SpecialFunctions.SpecialFunctions.RegularizedIncompleteBetaFunction(_Alpha, _Beta, value);
    }
    @Override
    public double GetPDF(double value) {
        return (Math.pow(value ,(_Alpha - 1)) * (Math.pow((1 - value) , (_Beta - 1)))) / SpecialFunctions.SpecialFunctions.BetaFunction(_Alpha, _Beta);
    }
}
