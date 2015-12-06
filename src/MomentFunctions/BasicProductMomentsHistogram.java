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
public class BasicProductMomentsHistogram extends BasicProductMoments{
    private int[] _Bins;
    private double _ExpectedMin;
    private double _ExpectedMax;
    public BasicProductMomentsHistogram(int NumBins, double Min, double Max){
        super();
        _Bins = new int[NumBins];
        _ExpectedMin = Min;
        _ExpectedMax = Max;
    }
//    public BasicProductMomentsHistogram(double binwidth){
//        //need to change the logic to be based on binwidth....
//        _Bins = new int[0];
//        
//    }
    @Override
    public void AddObservation(double observation){
        super.AddObservation(observation);
        //histogram logic.
        if(observation < _ExpectedMin){
            //increase lower bound to include, and add bins
        }else if(observation>_ExpectedMax){
            //increase upper bound to include and add bins
        }
        int index = _Bins.length * (int)java.lang.Math.floor((observation-_ExpectedMin)/ (_ExpectedMax-_ExpectedMin));
        _Bins[index]+=1;
    }
    
}
