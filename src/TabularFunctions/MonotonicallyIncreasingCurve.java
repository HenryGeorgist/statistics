/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabularFunctions;

import Distributions.ContinuousDistribution;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Will_and_Sara
 */
public class MonotonicallyIncreasingCurve extends TabularFunction implements ISampleWithUncertainty, ISampleDeterministically{
    private ArrayList<Double> _X;
    private ArrayList<Double> _Y;
    @Override
    public ArrayList<Double> GetXValues() {
        return _X;
    }
    @Override
    public ArrayList<Double> GetYValues() {
        return _Y;
    }
    @Override
    public FunctionTypeEnum FunctionType() {
        return FunctionTypeEnum.MonotonicallyIncreasing;
    }
    @Override
    public double GetYFromX(double x) {
        //determine how to implement a binary search.
        int index = Collections.binarySearch(_X,x);
        //if index is negative, it should be (-(index)-1);
        if(index>0){
            return _Y.get(index);
        }else{
            //interpolate. make sure the index is correctly determined.
            index *=-1;
            if(index>=_Y.size()){return _Y.get(_Y.size() -1);}
            double delta = _X.get(index) - _X.get(index-1);
            double distance = x-_X.get(index)/delta;
            double ydelta = _Y.get(index)- _Y.get(index-1);
            return _Y.get(index-1) + ydelta*distance;
        }
    }
    @Override
    public ArrayList<TabularFunctionError> Validate() {
        ArrayList<TabularFunctionError> output = new ArrayList<>();
        if(_Y.size() >= 1){return output;}
        for(int i=1;i<_Y.size();i++){
            if(_Y.get(i-1)>_Y.get(i)){
                //Y is not monotonically increasing at row i.
                output.add(new TabularFunctionError("Y is not monotonically increasing.",i,"Y Value","None"));
            }
            if(_X.get(i-1)>_X.get(i)){
                //X is not monotonically increasing at row i.
                output.add(new TabularFunctionError("X is not monotonically increasing.",i,"X Value","None"));
            } 
        }
        return output;
    }
    @Override
    public double GetYFromX(double x, double probability) {
        //Basic functionality will return GetYFromX(double x).  MonotonicallyIncreasingCurveUncertain will override this method.
        return GetYFromX(x);
    }
    @Override
    public ArrayList<Double> GetYValues(double probability) {
        //Basic functionality will return _Y.  MonotonicallyIncreasingCurveUncertain will override this method.
        return _Y;
    }
    @Override
    public ArrayList<ContinuousDistribution> GetYDistributions() {
        ArrayList<ContinuousDistribution> output = new ArrayList<>();
        for(int i=0;i<_Y.size();i++){
            output.add(new Distributions.MethodOfMoments.Uniform(_Y.get(i),_Y.get(i)));
        }
        return output;
    }
}
