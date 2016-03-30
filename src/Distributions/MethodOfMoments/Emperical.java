/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.MethodOfMoments;

import Distributions.ContinuousDistributionError;
import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class Emperical extends Distributions.ContinuousDistribution{
    private double[] _CumulativeProbabilities;
    private double[] _ExceedanceValues;
    @Override
    public double GetInvCDF(double probability) {
        int index = java.util.Arrays.binarySearch(_CumulativeProbabilities, probability);
        //interpolate or step?
        //check for array out of bounds...
        return _ExceedanceValues[index];
    }
    @Override
    public double GetCDF(double value) {
        int index = java.util.Arrays.binarySearch(_ExceedanceValues, value);
        //interpolate or step?
        return _CumulativeProbabilities[index];
    }
    @Override
    public double GetPDF(double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public ArrayList<ContinuousDistributionError> Validate() {
        ArrayList<ContinuousDistributionError> errors = new ArrayList<>();
        if(_CumulativeProbabilities.length != _ExceedanceValues.length){errors.add(new ContinuousDistributionError("Cumulative Probability values and Emperical Exceedance values are different lengths."));}
        return errors;
    }
    
}
