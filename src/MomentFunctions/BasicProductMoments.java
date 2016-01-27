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
    public int GetSampleSize(){return _Count;}

    /**
     *This function can be used to determine if enough samples have been added to determine convergence of the data stream.
     * @return this function will return false until the minimum number of observations have been added, and then will return the result of the convergence test after the most recent observation.
     */
    public boolean IsConverged(){return _Converged;}
    /**
     *This method allows the user to define a minimum number of observations before testing for convergence.
     * This would help to mitigate early convergence if similar observations are in close sequence early in the dataset.
     * @param numobservations the minimum number of observations to wait until testing for convergence.
     */
    public void SetMinValuesBeforeConvergenceTest(int numobservations){_MinValuesBeforeConvergenceTest = numobservations;}
    /**
     *This method sets the tolerance for convergence.  This tolerance is used as an epsilon neighborhood around the confidence defined in SetZalphaForConvergence.
     * @param tolerance the distance that is determined to be close enough to the alpha in question.
     */
    public void SetConvergenceTolerance(double tolerance){_ToleranceForConvergence = tolerance;}
    /**
     *This method defines the alpha value used to determine convergence.  This value is based on a two sided confidence interval.  It uses the upper Confidence Limit.
     * @param ConfidenceInterval The value that would be used to determine the normal alpha value.  The default is a .9 Confidence interval, which corresponds to 1.96 alpha value.
     */
    public void SetZAlphaForConvergence(double ConfidenceInterval){
        Distributions.MethodOfMoments.Normal sn = new Distributions.MethodOfMoments.Normal();
        _ZAlphaForConvergence = sn.GetInvCDF(ConfidenceInterval +((1-ConfidenceInterval)/2));
    }
    /**
     *This constructor allows one to create an instance without adding any data.
     */
    public BasicProductMoments(){
        _Mean = 0;
        _SampleVariance = 0;
        _Min = 0;
        _Max = 0;
        _Count = 0;
    }
    /**
     *This constructor allows one to create an instance with some initial data, observations can be added after the constructor through the "AddObservations(double observation) call.
     * @param data the dataset to calculate mean and standard deviation for.
     */
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
    /**
     *An inline algorithm for incrementing mean and standard of deviation. After this method call, the properties of this class should be updated to include this observation.
     * @param observation the observation to be added
     */
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
    public void AddObservations(double[] data){
        for(double d : data){
            AddObservation(d);
        }
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
