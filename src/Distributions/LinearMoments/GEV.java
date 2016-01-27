/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.LinearMoments;

/**
 *
 * @author Will_and_Sara and Micheal Wright
 */
public class GEV extends Distributions.ContinuousDistribution{
    private double _K;
    private double _Alpha;
    private double _Xi;
    public GEV(){
        //for reflection
        _K = 0;
        _Alpha = 0;
        _Xi = 0;
    }
    public GEV(double[] data){
        MomentFunctions.LinearMoments LM = new MomentFunctions.LinearMoments(data);
        SetPeriodOfRecord(LM.GetSampleSize());
        //different formulae finding _k for positive and negative t3 - for very low t3 _k is refined through newton-raphson iteration
        if(LM.GetT3() <= 0){ //following works for -0.8 to 0
            _K = (0.2837753 + LM.GetT3() * (-1.21096399 + LM.GetT3() * (-2.50728214 + LM.GetT3() * (-1.13455566 + LM.GetT3() * -0.07138022)))) / (1 + LM.GetT3() * (2.06189696 + LM.GetT3() * (1.31912239 + LM.GetT3() * 0.25077104)));
            if( LM.GetT3() < -0.8 ){//use above _k as starting point for newton-raphson iteration to converge to answer
                if(LM.GetT3() <= -0.97){
                    _K = 1 - Math.log(1 + LM.GetT3()) / Math.log(2);
                }else{//...unless t3 is below -0.97 in which case start from this formula
                double t0 = (LM.GetT3() + 3) / 2;
                    for(int i = 1; i< 20;i++){
                        double x2 = Math.pow(2 , -_K);
                        double x3 = Math.pow(3 , -_K);
                        double xx2 = 1 - x2;
                        double xx3 = 1 - x3;
                        double deriv = (xx2 * x3 * Math.log(3) - xx3 * x2 * Math.log(2)) / Math.pow(xx2 , 2);
                        double kold = _K;
                        _K = _K - (xx3 / xx2 - t0) / deriv;
                        if (Math.abs(_K - kold) <= _K * 0.000001){ i = 20;}
                    }
                }
            }else{
                //use the above k, without any newton-raphson
            }
        }else{ //positive t3 always uses the below k
            double z = 1 - LM.GetT3();
            _K = (-1 + z * (1.59921491 + z * (-0.48832213 + z * 0.01573152))) / (1 + z * (-0.64363929 + z * 0.08985247));
        }
        //calculate alpha and xi from k, or if k = 0 calculate them in a different way
        if( Math.abs(_K) < 0.000001){
            _K = 0;
            _Alpha = LM.GetL2() / Math.log(2);
            _Xi = LM.GetL1() - _Alpha * 0.57721566; //euler's constant
        }else{
            double gam = Math.exp(SpecialFunctions.SpecialFunctions.gammaln(1 + _K));
            _Alpha = LM.GetL2() * _K / (gam * (1 - Math.pow(2 ,-_K)));
            _Xi = LM.GetL1() - _Alpha * (1 - gam) / _K;
        }
    }
    public GEV(double K, double Alpha, double Xi){
        _K = K;
        _Alpha = Alpha;
        _Xi = Xi;
    }
    @Override
    public double GetInvCDF(double probability) {
        if(_K != 0){
            return _Xi + (_Alpha / _K) * ((1 - Math.pow((-Math.log(probability)),_K)));
        }else{
            return _Xi - _Alpha * Math.log(-Math.log(probability));
        }
    }
    @Override
    public double GetCDF(double value) {
        return Math.exp(-Math.exp(-Y(value)));
    }
    @Override
    public double GetPDF(double value) {
        return (1/_Alpha) * Math.exp(-(1 - _K) * (Y(value) - Math.exp(-Y(value))));
    }
    private double Y(double value){
        if (_K != 0){
            return (-1/_K) * Math.log(1 - _K * (value - _Xi) / _Alpha);
        }else{
            return (value - _Xi) / _Alpha;
        }
    }
}
