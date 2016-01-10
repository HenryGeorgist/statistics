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
        //histogram logic. Currently this is not designed to be as efficent as possible.  needs work (buffer block copy for instance)
        if(observation < _ExpectedMin){
            double binwidth = (_ExpectedMax - _ExpectedMin)/_Bins.length;
            int overdist = (int)java.lang.Math.ceil(-(observation-_ExpectedMin)/binwidth);
            _ExpectedMin = _ExpectedMin - overdist*binwidth;
            int[] tmparray = new int[_Bins.length + overdist-1];
            for(int i = overdist; i<_Bins.length;i++){
                tmparray[i] = _Bins[i];
            }
            _Bins = tmparray;
        }else if(observation>_ExpectedMax){
            double binwidth = (_ExpectedMax - _ExpectedMin)/_Bins.length;
            int overdist = (int)java.lang.Math.ceil((observation-_ExpectedMax)/binwidth);
            int[] tmparray = new int[_Bins.length + overdist-1];
            for(int i = 0; i<_Bins.length;i++){
                tmparray[i] = _Bins[i];
            }
            _Bins = tmparray;
        }
        int index = (int)java.lang.Math.floor(_Bins.length * (observation-_ExpectedMin)/ (_ExpectedMax-_ExpectedMin));
        _Bins[index]+=1;
    } 
}
