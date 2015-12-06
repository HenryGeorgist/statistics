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
public class ProductMoments {
    private double _Min;
    private double _Max;
    private double _Mean;
    private double _StandardDeviation;
    //private double _Median;
    private double _Skew;
    private double _Kurtosis;
    private int _Count;
    public ProductMoments(double[] data){
        _Count = data.length;
        BasicProductMoments BPM = new BasicProductMoments(data);
        _Min = BPM.GetMin();
        _Max = BPM.GetMax();
        _Mean = BPM.GetMean();
        _StandardDeviation = BPM.GetStDev();
        double skewsums = 0;
        double ksums = 0;
        for(int i = 0; i<data.length;i++){
            skewsums += java.lang.Math.pow((data[i]-_Mean),3);
            ksums += java.lang.Math.pow(((data[i]-_Mean)/_StandardDeviation),4);
        }
        //just alittle more math...
        ksums *= (_Count*(_Count+1))/((_Count-1)*(_Count-2)*(_Count-3));
        _Skew = (_Count*skewsums)/((_Count-1)*(_Count-2)*java.lang.Math.pow(_StandardDeviation,3));
        _Kurtosis = ksums - ((3*(java.lang.Math.pow(_Count-1, 2)))/((_Count-2)*(_Count-3)));
        //figure out an efficent algorithm for median...
    }
    public double GetSkew(){return _Skew;}
    public double GetKurtosis(){return _Kurtosis;}
    public double GetMin(){return _Min;}
    public double GetMax(){return _Max;}
    public double GetMean(){return _Mean;}
    public double GetStDev(){return _StandardDeviation;}
    public int GetSampleSize(){return _Count;}
    
}
