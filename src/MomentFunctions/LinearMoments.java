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
public class LinearMoments {
    private double _L1;
    private double _L2;
    private double _L3;
    private double _L4;
    private int _Count;
    private double _Max;
    private double _Min;
    public double GetL1(){return _L1;}
    public double GetL2(){return _L2;}
    public double GetL3(){return _L3;}
    public double GetL4(){return _L4;}
    public double GetT1(){return _L2/_L1;}
    public double GetT3(){return _L3/_L2;}
    public double GetT4(){return _L4/_L2;}
    public double GetMax(){return _Max;}
    public double GetMin(){return _Min;}
    public int GetSampleSize(){return _Count;}
}
