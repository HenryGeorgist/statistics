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
    public LinearMoments(double data[]){
        //sort the data..
        //i dont know how to sort arrays in java yet.
        _Count = data.length;
        _Min = data[0];
        _Max = data[_Count-1];
        long cl2;
        long cl3;
        long cl4;
        long cr1;
        long cr2;
        long cr3;
        double sl1 = 0;
        double sl2 = 0;
        double sl3 = 0;
        double sl4 = 0;
        for(int i = 0; i<data.length;i++){
            cl2 = (long) ((i*(i-1))/2);
            cl3 = (long) ((cl2*(i-2))/3);
            cr1 = _Count - (i+1);
            cr2 = (long) (cr1 * ((_Count-(i+2)))/2);
            cr3 = (long) (cr2 * ((_Count-(i+3)))/3);
            sl1 += data[i];
            sl2 += data[i] * (i - cr1);
            sl3 += data[i] * (cl2 - 2 * i * cr1 + cr2);
            sl4 += data[i] * (cl3 - 3 * cl2 * cr1 + 3 * i * cr2 - cr3);
        }
        cl2 = (long) _Count * (_Count-1)/2;// not sure order of operations is correct here..
        cl3 = (long) cl2 * (_Count - 2)/3;
        cl4 = (long) cl3 * (_Count - 3)/4;
        _L1 = sl1/_Count;
        _L2 = sl2 / cl2 / 2;
        _L3 = sl3 / cl3 / 3;
        _L4 = sl4 / cl4 / 4;
    }
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
