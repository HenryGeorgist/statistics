# statistics
statistics is a library, written in java, indended to allow data to be fitted to analytical distributions
using MOM or Linear moments.

The resulting distributions can then be accessed using GetPDF, GetCDF, and GetInvCDF.  This allows programmers 
to create simple monte carlo programs without having a math library that solves all of the problems in the world.

To produce MOM statistics there are a few options, BasicProductMoments utilizes an inline algorithm for calculating count, min,
max, mean, and variance.  BasicProductMomentsHistogram extends BasicProductMoments to include a histogram that is created based on
a bin width. This histogram is also developed using an inline algorithm.  ProductMoments requires storing the data or passing the
entire array into the object at one time, but gives the programmer access to skew and kurtosis.



