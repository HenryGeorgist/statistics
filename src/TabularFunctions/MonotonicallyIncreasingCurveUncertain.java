/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabularFunctions;

import Distributions.ContinuousDistribution;
import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class MonotonicallyIncreasingCurveUncertain extends TabularFunction implements ISampleWithUncertainty{
    private ArrayList<Double> _X;
    private ArrayList<ContinuousDistribution> _Y;
    @Override
    public ArrayList<Double> GetXValues() {
        return _X;
    }
    @Override
    public ArrayList<ContinuousDistribution> GetYDistributions() {
        return _Y;
    }
    @Override
    public double GetYFromX(double x, double probability) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Double> GetYValues(double probability) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public FunctionTypeEnum FunctionType() {
        return FunctionTypeEnum.MonotonicallyIncreasingUncertain;
    }
    @Override
    public ArrayList<TabularFunctionError> Validate() {
        ArrayList<TabularFunctionError> output = new ArrayList<>();
        if(_Y.size() >= 1){return output;}
        for(int i=1;i<_Y.size();i++){
//            if(_Y.get(i-1)>_Y.get(i)){
//                //
//                output.add(new TabularFunctionError("Y is not monotonically increasing.",i,"Y Value","None"));
//            }
            if(_X.get(i-1)>_X.get(i)){
                //X is not monotonically increasing at row i.
                output.add(new TabularFunctionError("X is not monotonically increasing.",i,"X Value","None"));
            } 
        }
        return output;
    }
}
