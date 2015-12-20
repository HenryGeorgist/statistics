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
    public int[] Bins(){return _Bins;}
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
            double binwidth = (_ExpectedMin - _ExpectedMin)/_Bins.length;
            int overdist = (int)java.lang.Math.ceil(-(observation-_ExpectedMin)/binwidth);
            _ExpectedMin = _ExpectedMin - overdist*binwidth;
            int[] tmparray = new int[_Bins.length + overdist-1];
            for(int i = overdist; i<_Bins.length;i++){
                tmparray[i] = _Bins[i];
            }
            _Bins = tmparray;
            }else if(observation>_ExpectedMax){
            //increase upper bound to include and add bins
                //there is no equivalent to redim preserve, so i must do something like the lower end.
        }
        int index = _Bins.length * (int)java.lang.Math.floor((observation-_ExpectedMin)/ (_ExpectedMax-_ExpectedMin));
        _Bins[index]+=1;
    }
    
}
