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
    public double GetMean(){return _Mean;}
    public double GetStDev(){return Math.sqrt(_SampleVariance);}
    public double GetMin(){return _Min;}
    public double GetMax(){return _Max;}
    public int GetCount(){return _Count;}
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
    }
    
}
