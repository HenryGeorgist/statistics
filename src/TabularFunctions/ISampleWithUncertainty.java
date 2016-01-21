/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabularFunctions;
import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public interface ISampleWithUncertainty {
    public double GetYFromX(double x, double probability);
    public ArrayList<Double> GetYValues(double probability);
    public ArrayList<Distributions.ContinuousDistribution> GetYDistributions();
}
