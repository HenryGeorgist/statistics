/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

/**
 *
 * @author Q0HECWPL
 */
public class GEV extends ContinuousDistribution{
    private double _Mu;//https://en.wikipedia.org/wiki/Generalized_extreme_value_distribution
    private double _Sigma;
    private double _Xi;
    public GEV(double mu, double sigma, double xi){
        _Mu = mu;
        _Sigma = sigma;
        _Xi = xi;
    }
    @Override
    public double GetInvCDF(double probability) {
        if(probability<=0){
            if(_Xi>0){
                return _Mu-_Sigma/_Xi;
            }else{
                return Double.NEGATIVE_INFINITY;
            }
        }else if(probability>=1){
            if(_Xi<0){
                return _Mu-_Sigma/_Xi;
            }else{
                return Double.POSITIVE_INFINITY;
            }
        }
        return Tinv(probability);
    }
    @Override
    public double GetCDF(double value) {
        //check support.
        if(_Xi>0 && value <= _Mu-_Sigma/_Xi){return 0;}
        if(_Xi<0 && value >= _Mu-_Sigma/_Xi){return 1;}
        return java.lang.Math.exp(-T(value));
    }

    @Override
    public double GetPDF(double value) {
        if(_Xi>0 && value <= _Mu-_Sigma/_Xi){return 0;}
        if(_Xi<0 && value >= _Mu-_Sigma/_Xi){return 0;}
        double tx = T(value);
        return (1/_Sigma)*java.lang.Math.pow(tx, _Xi+1)*java.lang.Math.exp(-tx);
    }
    private double T(double x){
        if(_Xi!=0){
            return java.lang.Math.pow((1+((x-_Mu)/_Sigma)*_Xi),-1/_Xi);
        }else{
            return  java.lang.Math.exp(-(x-_Mu)/_Sigma);
        }
    }
    private double Tinv(double probability){
        if(_Xi!=0){
            return _Mu-_Sigma*(java.lang.Math.pow(java.lang.Math.log(1/probability), _Xi)-1)/_Xi;
        }else{
            return _Mu-_Sigma*java.lang.Math.log(java.lang.Math.log(1/probability));
        }
    }
}
