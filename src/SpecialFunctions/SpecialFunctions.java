/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecialFunctions;

/**
 *
 * @author Q0HECWPL
 */
public class SpecialFunctions {
    public int Factorial(int N){//could be improved with unsigned integers or the gamma function
        if(N==1){return 1;}
        return Factorial(N-1);
    }
    public int Factorial(int N, int k){//could be improved with unsigned integers or the gamma function
        if(N==1){return 1;}
        if(k+1>=N){return N;}
        int ret = 1;
        for(int i = k+1;i<N;i++){
            ret *=i;
        }
        return ret;
    }
    public int Choose(int n, int k){
        return Factorial(n,k)/Factorial(n-k);
    }
    public double Binomial(double probability, int n, int k){
        double value = 0;
        for(int i = 0; i<=k;i++){
            value = value + Choose(n,i)*(java.lang.Math.pow(probability, i))*(java.lang.Math.pow(1-probability, n-i));
        }
        return value +1;
    }
    public double InvBinomal(double probability, int n, int k){
        for(int i = 0; i<n; i++){//need to validate this one.
            if(Binomial(probability,n,i)>k){return i;}
        }
        return n;
    }
    public double MutualProbability(double[] probabilities){
        //http://lethalman.blogspot.com/2011/08/probability-of-union-of-independent.html
        double ret = 0;
        for(int i = 0; i<probabilities.length;i++){
            ret += probabilities[i]*(1-ret);
        }
        return ret;
    }
}
