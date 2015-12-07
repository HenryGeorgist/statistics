/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MomentFunctions;
/**
 *
 * @author Will_and_Sara
 */
public class BasicProductMoments {
    private double _Mean;
    private double _SampleVariance;
    private double _Min;
    private double _Max;
    protected int _Count;
    private boolean _Converged = false;
    private double _ZAlphaForConvergence = 1.96039491692453;
    private double _ToleranceForConvergence = 0.01;
    private int _MinValuesBeforeConvergenceTest = 100;
    public double GetMean(){return _Mean;}
    public double GetStDev(){return Math.sqrt(_SampleVariance);}
    public double GetMin(){return _Min;}
    public double GetMax(){return _Max;}
    public int GetCount(){return _Count;}
    public boolean IsConverged(){return _Converged;}
    public void SetMinValuesBeforeConvergenceTest(int numobservations){_MinValuesBeforeConvergenceTest = numobservations;}
    public void SetConvergenceTolerance(double tolerance){_ToleranceForConvergence = tolerance;}
    public void SetZAlphaForConvergence(double ConfidenceInterval){
        Distributions.Normal sn = new Distributions.Normal();
        _ZAlphaForConvergence = sn.GetInvCDF(ConfidenceInterval +((1-ConfidenceInterval)/2));
    }
    public BasicProductMoments(){
        _Mean = 0;
        _SampleVariance = 0;
        _Min = 0;
        _Max = 0;
        _Count = 0;
    }
    public BasicProductMoments(double[] data){
        _Mean = 0;
        _SampleVariance = 0;
        _Min = 0;
        _Max = 0;
        _Count = 0;
        for (double d : data){
            this.AddObservation(d);
        }        
    }
    public void AddObservation(double observation){
        if(_Count == 0){
            _Count = 1;
            _Min = observation;
            _Max = observation;
            _Mean = observation; 
        }else{
           //single pass algorithm. 
           if(observation> _Max){_Max = observation;}
           if(observation< _Min){_Min = observation;}
           _Count++;
           double newmean = _Mean +((observation-_Mean)/_Count);//check for integer rounding issues.
           _SampleVariance = ((((double)(_Count-2)/(double)(_Count-1))*_SampleVariance)+(Math.pow(observation-_Mean,2.0))/_Count);
           _Mean = newmean;
        }
        TestForConvergence();
    }
    private void TestForConvergence(){
        if(_Count>_MinValuesBeforeConvergenceTest){
            if(!_Converged){
                double var = (_ZAlphaForConvergence * this.GetStDev())/(this.GetMean()*java.lang.Math.abs(this.GetStDev()));
                _Converged = (java.lang.Math.abs(var)<=_ToleranceForConvergence);
            } 
        }
    }
}
