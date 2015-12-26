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
    private static final double MACHEP =  1.11022302462515654042E-16;
    private static final double MAXLOG =  7.09782712893383996732E2;
    private static final double MINLOG = -7.451332191019412076235E2;
    private static final double MAXGAM = 171.624376956302725;
    public static int Factorial(int N){//could be improved with unsigned integers or the gamma function
        if(N==1){return 1;}
        return Factorial(N-1);
    }
    public static int Factorial(int N, int k){//could be improved with unsigned integers or the gamma function
        if(N==1){return 1;}
        if(k+1>=N){return N;}
        int ret = 1;
        for(int i = k+1;i<N;i++){
            ret *=i;
        }
        return ret;
    }
    public static int Choose(int n, int k){
        return Factorial(n,k)/Factorial(n-k);
    }
    public static double Binomial(double probability, int n, int k){
        double value = 0;
        for(int i = 0; i<=k;i++){
            value = value + Choose(n,i)*(java.lang.Math.pow(probability, i))*(java.lang.Math.pow(1-probability, n-i));
        }
        return value +1;
    }
    public static double InvBinomal(double probability, int n, int k){
        for(int i = 0; i<n; i++){//need to validate this one.
            if(Binomial(probability,n,i)>k){return i;}
        }
        return n;
    }
    public static double MutualProbability(double[] probabilities){
        //http://lethalman.blogspot.com/2011/08/probability-of-union-of-independent.html
        double ret = 0;
        for(int i = 0; i<probabilities.length;i++){
            ret += probabilities[i]*(1-ret);
        }
        return ret;
    }
    public static double BetaFunction(double a, double b){
        return java.lang.Math.exp(gammaln(a)+gammaln(b)-gammaln(a-b));
    }
    public static double RegularizedIncompleteBetaFunction( double aa, double bb, double xx ) throws ArithmeticException {
        double a, b, t, x, xc, w, y;
        boolean flag;

        if( aa <= 0.0 || bb <= 0.0 ) throw new 
                          ArithmeticException("ibeta: Domain error!");

        if( (xx <= 0.0) || ( xx >= 1.0) ) {
  	       if( xx == 0.0 ) return 0.0;
   	       if( xx == 1.0 ) return 1.0;
           throw new ArithmeticException("ibeta: Domain error!");
	    }

        flag = false;
        if( (bb * xx) <= 1.0 && xx <= 0.95) {
	        t = pseries(aa, bb, xx);
		    return t;
	    }

        w = 1.0 - xx;

        /* Reverse a and b if x is greater than the mean. */
        if( xx > (aa/(aa+bb)) ) {
	       flag = true;
	       a = bb;
	       b = aa;
	       xc = xx;
	       x = w;
	    } else {
  	       a = aa;
	       b = bb;
	       xc = w;
	       x = xx;
	    }

        if( flag  && (b * x) <= 1.0 && x <= 0.95) {
 	       t = pseries(a, b, x);
	       if( t <= MACHEP ) 	t = 1.0 - MACHEP;
	       else  		        t = 1.0 - t;
           return t;
	    }

        /* Choose expansion for better convergence. */
        y = x * (a+b-2.0) - (a-1.0);
        if( y < 0.0 )
	                  w = incbcf( a, b, x );
        else
	                  w = incbd( a, b, x ) / xc;

        /* Multiply w by the factor
           a      b   _             _     _
          x  (1-x)   | (a+b) / ( a | (a) | (b) ) .   */

        y = a * Math.log(x);
        t = b * Math.log(xc);
        if( (a+b) < MAXGAM && Math.abs(y) < MAXLOG && Math.abs(t) < MAXLOG ) {
	        t = Math.pow(xc,b);
	        t *= Math.pow(x,a);
	        t /= a;
	        t *= w;
	        t *= gamma(a+b) / (gamma(a) * gamma(b));
            if( flag ) {
 	           if( t <= MACHEP ) 	t = 1.0 - MACHEP;
	           else  		        t = 1.0 - t;
	        }
            return t;
	    }
        /* Resort to logarithms.  */
        y += t + gammaln(a+b) - gammaln(a) - gammaln(b);
        y += Math.log(w/a);
        if( y < MINLOG )
	                    t = 0.0;
        else
	                    t = Math.exp(y);

        if( flag ) {
 	           if( t <= MACHEP ) 	t = 1.0 - MACHEP;
	           else  		        t = 1.0 - t;
	    }
        return t;
   }

/* Continued fraction expansion #1
 * for incomplete beta integral
 */

    private static double incbcf( double a, double b, double x ) throws ArithmeticException {
       double xk, pk, pkm1, pkm2, qk, qkm1, qkm2;
       double k1, k2, k3, k4, k5, k6, k7, k8;
       double r, t, ans, thresh;
       int n;
       double big = 4.503599627370496e15;
       double biginv =  2.22044604925031308085e-16;
       k1 = a;
       k2 = a + b;
       k3 = a;
       k4 = a + 1.0;
       k5 = 1.0;
       k6 = b - 1.0;
       k7 = k4;
       k8 = a + 2.0;
       pkm2 = 0.0;
       qkm2 = 1.0;
       pkm1 = 1.0;
       qkm1 = 1.0;
       ans = 1.0;
       r = 1.0;
       n = 0;
       thresh = 3.0 * MACHEP;
       do {
	      xk = -( x * k1 * k2 )/( k3 * k4 );
	      pk = pkm1 +  pkm2 * xk;
	      qk = qkm1 +  qkm2 * xk;
	      pkm2 = pkm1;
	      pkm1 = pk;
	      qkm2 = qkm1;
	      qkm1 = qk;
	      xk = ( x * k5 * k6 )/( k7 * k8 );
	      pk = pkm1 +  pkm2 * xk;
	      qk = qkm1 +  qkm2 * xk;
	      pkm2 = pkm1;
	      pkm1 = pk;
	      qkm2 = qkm1;
	      qkm1 = qk;
	      if( qk != 0 ){
                  r = pk/qk;
              }
	      if( r != 0 ) {
		       t = Math.abs( (ans - r)/r );
		       ans = r;
              }else{
		       t = 1.0;
              }
	      if( t < thresh ){
                  return ans;
              }
	      k1 += 1.0;
          k2 += 1.0;
      	  k3 += 2.0;
      	  k4 += 2.0;
      	  k5 += 1.0;
      	  k6 -= 1.0;
      	  k7 += 2.0;
      	  k8 += 2.0;
      	  if( (Math.abs(qk) + Math.abs(pk)) > big ) {
      		pkm2 *= biginv;
      		pkm1 *= biginv;
      		qkm2 *= biginv;
      		qkm1 *= biginv;
		  }
      	  if( (Math.abs(qk) < biginv) || (Math.abs(pk) < biginv) ) {
      		pkm2 *= big;
      		pkm1 *= big;
      		qkm2 *= big;
      		qkm1 *= big;
		  }
	   } while( ++n < 300 );
    return ans;
   }
/* Continued fraction expansion #2
 * for incomplete beta integral
 */

    static private double incbd( double a, double b, double x ) throws ArithmeticException {
         double xk, pk, pkm1, pkm2, qk, qkm1, qkm2;
         double k1, k2, k3, k4, k5, k6, k7, k8;
         double r, t, ans, z, thresh;
         int n;
         double big = 4.503599627370496e15;
         double biginv =  2.22044604925031308085e-16;

         k1 = a;
         k2 = b - 1.0;
         k3 = a;
         k4 = a + 1.0;
         k5 = 1.0;
         k6 = a + b;
         k7 = a + 1.0;;
         k8 = a + 2.0;

         pkm2 = 0.0;
         qkm2 = 1.0;
         pkm1 = 1.0;
         qkm1 = 1.0;
         z = x / (1.0-x);
         ans = 1.0;
         r = 1.0;
         n = 0;
         thresh = 3.0 * MACHEP;
         do {
	         xk = -( z * k1 * k2 )/( k3 * k4 );
	         pk = pkm1 +  pkm2 * xk;
	         qk = qkm1 +  qkm2 * xk;
	         pkm2 = pkm1;
	         pkm1 = pk;
	         qkm2 = qkm1;
	         qkm1 = qk;

	         xk = ( z * k5 * k6 )/( k7 * k8 );
	         pk = pkm1 +  pkm2 * xk;
	         qk = qkm1 +  qkm2 * xk;
	         pkm2 = pkm1;
	         pkm1 = pk;
	         qkm2 = qkm1;
	         qkm1 = qk;

	         if( qk != 0 )  r = pk/qk;
	         if( r != 0 ) {
		         t = Math.abs( (ans - r)/r );
		         ans = r;
		     } else
		         t = 1.0;

	         if( t < thresh ) return ans;

	         k1 += 1.0;
	         k2 -= 1.0;
	         k3 += 2.0;
	         k4 += 2.0;
	         k5 += 1.0;
	         k6 += 1.0;
	         k7 += 2.0;
	         k8 += 2.0;

	         if( (Math.abs(qk) + Math.abs(pk)) > big ) {
		        pkm2 *= biginv;
		        pkm1 *= biginv;
		        qkm2 *= biginv;
		        qkm1 *= biginv;
		     }
	         if( (Math.abs(qk) < biginv) || (Math.abs(pk) < biginv) ) {
		        pkm2 *= big;
		        pkm1 *= big;
		        qkm2 *= big;
		        qkm1 *= big;
		     }
	    } while( ++n < 300 );

        return ans;
     }
/* Power series for incomplete beta integral.
   Use when b*x is small and x not too close to 1.  */

     static private  double pseries( double a, double b, double x )throws ArithmeticException {
        double s, t, u, v, n, t1, z, ai;

        ai = 1.0 / a;
        u = (1.0 - b) * x;
        v = u / (a + 1.0);
        t1 = v;
        t = u;
        n = 2.0;
        s = 0.0;
        z = MACHEP * ai;
        while( Math.abs(v) > z ) {
	       u = (n - b) * x / n;
	       t *= u;
	       v = t / (a + n);
	       s += v; 
	       n += 1.0;
	    }
        s += t1;
        s += ai;

        u = a * Math.log(x);
        if( (a+b) < MAXGAM && Math.abs(u) < MAXLOG ) {
	        t = gamma(a+b)/(gamma(a)*gamma(b));
	        s = s * t * Math.pow(x,a);
	    } else {
	       t = gammaln(a+b) - gammaln(a) - gammaln(b) + u + Math.log(s);
	       if( t < MINLOG ) 	s = 0.0;
	       else  	            s = Math.exp(t);
	    }
        return s;
     }
    //https://www.ncnr.nist.gov/resources/sansmodels/SpecialFunction.java //previous name was stirf
    private static double StirlingsFormula(double x){
        double[] stir = {0.00078731139579309368, -0.00022954996161337813, -0.0026813261780578124, 0.0034722222160545866, 0.08333333333334822};
        double MaxStir = 143.01608;
        double w = 1/x;
        double y = java.lang.Math.exp(x);
        w = 1+w*EvaluatePolynomial(w,stir);
        if(x>MaxStir){
            double v = java.lang.Math.pow(x, 0.5*x-0.25);
            y = v*(v/y);
        }else{
            y = java.lang.Math.pow(x,x-0.5)/y;
        }
        return java.lang.Math.sqrt(java.lang.Math.PI)*y*w;
    }
    //https://www.ncnr.nist.gov/resources/sansmodels/SpecialFunction.java //previous name was polevl
    private static double EvaluatePolynomial(double x, double[] coefficients){
        double answer = coefficients[0];
        for(int i = 1; i<coefficients.length;i++){
            answer = answer*x+coefficients[i];
        }
        return answer;
    }
    //https://www.ncnr.nist.gov/resources/sansmodels/SpecialFunction.java //previous name was igamc
     static public double IncompleteGammaComplement( double a, double x ){
        double big    = 4.503599627370496e15;
        double biginv =  2.22044604925031308085e-16;
        double ans, ax, c, yc, r, t, y, z;
        double pk, pkm1, pkm2, qk, qkm1, qkm2;
        if( x <= 0 || a <= 0 ) return 1.0;
        if( x < 1.0 || x < a ) return 1.0 - IncompleteGamma(a,x);
        ax = a * Math.log(x) - x - gammaln(a);
        if( ax < -MAXLOG ) return 0.0;
        ax = Math.exp(ax);
        y = 1.0 - a;
        z = x + y + 1.0;
        c = 0.0;
        pkm2 = 1.0;
        qkm2 = x;
        pkm1 = x + 1.0;
        qkm1 = z * x;
        ans = pkm1/qkm1;
        do {
  	    c += 1.0;
	    y += 1.0;
	    z += 2.0;
	    yc = y * c;
	    pk = pkm1 * z  -  pkm2 * yc;
	    qk = qkm1 * z  -  qkm2 * yc;
	    if( qk != 0 ) {
		r = pk/qk;
		t = Math.abs( (ans - r)/r );
		ans = r;
	    } else
		t = 1.0;
	    pkm2 = pkm1;
	    pkm1 = pk;
	    qkm2 = qkm1;
	    qkm1 = qk;
	    if( Math.abs(pk) > big ) {
		pkm2 *= biginv;
		pkm1 *= biginv;
		qkm2 *= biginv;
		qkm1 *= biginv;
	    }
	} while( t > MACHEP );
        return ans * ax;
     }
     //https://www.ncnr.nist.gov/resources/sansmodels/SpecialFunction.java //previous name was igam
     static public double IncompleteGamma(double a, double x) {
        double ans, ax, c, r;
        if( x <= 0 || a <= 0 ) return 0.0;
        if( x > 1.0 && x > a ) return 1.0 - IncompleteGammaComplement(a,x);
        ax = a * Math.log(x) - x - gammaln(a);
        if( ax < -MAXLOG ) return( 0.0 );
        ax = Math.exp(ax);
        r = a;
        c = 1.0;
        ans = 1.0;
        do {
  	    r += 1.0;
	    c *= x/r;
	    ans += c;
	}
        while( c/ans > MACHEP );
        return( ans * ax/a );
     }
    public static double gammaln(double x){//testing showed Ben's code and this code were roughly equivalent (also to excel) however, Ben's code executed faster in the time trials.
        if(x<=0){return Double.NaN;}
        double[] c = new double[6];
        c[0] = 76.180091729471457;
        c[1] = -86.505320329416776;
        c[2] = 24.014098240830911;
        c[3] = -1.231739572450155;
        c[4] = 0.001208650973866179;
        c[5] = -0.000005395239384953;
        double tmp = x+5.5;
        tmp = (x+0.5)*java.lang.Math.log(tmp)-tmp;
        double err = 1.0000000001900149;
        for(int i = 0; i<5;i++){
            err += c[i]/(x+i+1);
        }
        return tmp + java.lang.Math.log(java.lang.Math.sqrt(java.lang.Math.PI*2) * err / x);
    }
    //https://www.ncnr.nist.gov/resources/sansmodels/SpecialFunction.java
    public static double gamma(double x){//testing showed Ben's code and this code were roughly equivalent (also to excel) however, Ben's code executed faster in the time trials.
        double lp;
        double lq = java.lang.Math.abs(x);
        double lz;
        if(lq > 33.0){
            if(x<0){
                lp = java.lang.Math.floor(lq);
                if(lp==lq){//gammaoverflow 
                    return Double.NaN;
                }
                lz = lq-lp;
                if(lz>0.5){
                    lp+=1;
                    lz = lq-lp;
                }
                lz = lq * java.lang.Math.sin(java.lang.Math.PI * lz);
                if(lz==0){return Double.NaN;}//gamma overflow
                lz = java.lang.Math.abs(lz);
                lz = java.lang.Math.PI/(lz * StirlingsFormula(lq));
                return -lz;
            }else{
                return StirlingsFormula(x);
            }
        }
        lz = 1.0;
        while(x>=3.0){
            x -=1.0;
            lz *= x;
        }
        while(x<0.0){
            if(x==0.0){return Double.NaN;}//gamma singular.
            if(x>-0.000000001){return lz/((1.0 + 0.57721566490153287*x)*x);}
            lz /=x;
            x+=1.0;
        }
        while(x<2.0){
            if(x==0){return Double.NaN;}//gamma singular
            if(x<0.000000001){return lz / ((1.0 + 0.57721566490153287 * x) * x);}
            lz/=x;
            x+=1.0;
        }
        if(x==2.0||x==3.0){return lz;}
        double[] p = {0.00016011952247675185, 0.0011913514700658638, 0.010421379756176158, 0.047636780045713721, 0.20744822764843598, 0.49421482680149709, 1.0};
        double[] q = {-0.000023158187332412014, 0.00053960558049330335, -0.0044564191385179728, 0.011813978522206043, 0.035823639860549865, -0.23459179571824335, 0.0714304917030273, 1.0};
        x -=2.0;
        lp = EvaluatePolynomial(x,p);
        lq = EvaluatePolynomial(x,q);
        return lz * lp/lq;
    }
}
