/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;
import org.w3c.dom.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
/**
 *
 * @author Will_and_Sara
 */
public abstract class ContinuousDistribution {
    private int _PeriodOfRecord = 0;
    /**
     *This function produces a value for a given probability, this value will represent the Non-Exceedance value for that probability.
     * @param probability a number between 0 and 1.
     * @return a value distributed by the distribution defined in the concrete implementation of this abstract class.
     */
    public abstract double GetInvCDF(double probability);
    /**
     *This function is the Cumulative Distribution Function. It returns a Non Exceedance probability for any value.  It will be implemented by all concrete implementations of this abstract class.
     * @param value the value that a probability will be produced for.
     * @return a probability that this value will be exceeded by any other value from the sample set.
     */
    public abstract double GetCDF(double value);
    /**
     *This is the Probability Density Function. It describes the likelihood any given value will occur within a dataset.
     * @param value the value that a likelihood will be returned for.
     * @return the likelihood (defined by the concrete distribution) the specified value will occur in any given sample dataset (assuming the value is from the underlying distribution).
     */
    public abstract double GetPDF(double value);
    public int GetPeriodOfRecord(){return _PeriodOfRecord;}
    public abstract ArrayList<ContinuousDistributionError> Validate();
    public final void SetPeriodOfRecord(int POR){_PeriodOfRecord = POR;}
    // <editor-fold defaultstate="collapsed" desc="Goodness of fit tests">
    public double Kolmogorov_SmirnovTest(){
        // need to create a good empirical distribution.
        return 0;
    }
    public double AndersonDarlingTest(){
        //still need a good emperical distribution.
        return 0;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Reflection based utilities, GetParamNames, GetParamValues, Clone, equals, hash, ReadFromXML, WriteToXML">

    /**
     *This function will return string representations of the parameter names for each distribution.
     * @return a String array of all of the declared fields composing the concrete implementation of this ContinuousDistribution
     */
    public String[] GetParamNames(){
        Field[] flds = this.getClass().getDeclaredFields();
        String[] ParamNames = new String[flds.length];
        for(int i = 0; i< flds.length;i++){
            ParamNames[i] = flds[i].getName();
        }
        return ParamNames;
    }
    /**
     *This function determines the current values for each parameter in this concrete implementation of the ContinuousDistribution
     * @return an array of object for each parameter in this class.
     */
    public Object[] GetParamValues(){
        Field[] flds = this.getClass().getDeclaredFields();
        Object[] ParamVals = new Object[flds.length];
        for(int i = 0; i< flds.length;i++){
            try {
                switch(flds[i].getType().getName()){
                    case "double":
                        ParamVals[i] = flds[i].getDouble(this);
                        break;
                    case "int":
                        ParamVals[i] = flds[i].getInt(this);
                    default:
                }   
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ContinuousDistribution.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ParamVals;
    }
    /**
     *Creates a clone of the current ContinuousDistribution.
     * @return A ContinuousDistribution of the same type as the one this function is called on.
     */
    public ContinuousDistribution Clone(){
        //create a new continuousdistribution and populate it from this using reflection.
        ContinuousDistribution Dist = null;
        Class<?> c;
        try {
            c = Class.forName(this.getClass().getName());
            Dist=(ContinuousDistribution) c.getConstructor().newInstance();
            Field[] flds = c.getDeclaredFields();
            for (Field f : flds){
                switch(f.getType().getName()){
                    case "double":
                        f.set(Dist,f.getDouble(this));
                        break;
                    case "int":
                        f.set(Dist,f.getInt(this));
                        break;
                    default:
                        break;
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ContinuousDistribution.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Dist;
    }
    @Override
    public boolean equals(Object dist){
        if(dist.getClass().getName().equals(this.getClass().getName())){
            Object[] thisParamValues = this.GetParamValues();
            ContinuousDistribution those = (ContinuousDistribution) dist;
            Object[] thoseParamValues = those.GetParamValues();
            if(thisParamValues.length == thoseParamValues.length){
                for(int i = 0; i<thisParamValues.length;i++){
                    if(thisParamValues[i] != thoseParamValues[i]){
                        return false;
                    }
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
    @Override
    public int hashCode() {
        int hash = this.getClass().getName().hashCode();
        Object[] vals = this.GetParamValues();
        for (Object val : vals) {
            hash += val.hashCode();
        }
        return hash;
    }
    public static ContinuousDistribution ReadFromXML(Element ele) {
            ContinuousDistribution Dist = null;
            Class<?> c;
        try {
            String DistName = ele.getTagName();
            if(DistName.equals("None")){
                // none is not supported.
                throw new IllegalArgumentException();
            }else if(DistName.contains(".")){
                //do nothing, this is probably from Statistics.jar.
            }else{
                if(DistName.charAt(0)== "L".toCharArray()[0] && DistName.charAt(1) != "o".toCharArray()[0]){//is l but isnt lo, so LNormal (which is how Statistics differntiates Linear moments.
                    DistName = "Distributions.LinearMoments." + DistName.substring(1,DistName.length()-1);//remove the L.
                }else{
                    DistName = "Distributions.MethodOfMoments." + DistName;
                }
            } 
            c = Class.forName(DistName);
            Dist=(ContinuousDistribution) c.getConstructor().newInstance();
            Field[] flds = c.getDeclaredFields();
            for (Field f : flds){
                switch(f.getType().getName()){
                    case "double":
                        f.set(Dist,Double.parseDouble(ele.getAttribute(f.getName())));
                        break;
                    case "int":
                        f.set(Dist,Integer.parseInt(ele.getAttribute(f.getName())));
                        break;
                    default:
                        //throw error?
                        break;
                }   
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ContinuousDistribution.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Dist;
    }
    public Element WriteToXML(){
       Field[] flds = this.getClass().getDeclaredFields();
        DocumentBuilderFactory d = DocumentBuilderFactory.newInstance();
        DocumentBuilder Db;
        try {
            Db = d.newDocumentBuilder();
            Document doc = Db.newDocument();
            Element ele = doc.createElement(this.getClass().getName());
            for (Field f : flds) {
            try {
                switch(f.getType().getName()){
                    case "double":
                        ele.setAttribute(f.getName(),Double.toString(f.getDouble(this)));
                        break;
                    case "int":
                        ele.setAttribute(f.getName(),Integer.toString(f.getInt(this)));
                        break;
                    default:
                        break;
                }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(ContinuousDistribution.class.getName()).log(Level.SEVERE, null, ex);
                }
	    }
               return ele;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ContinuousDistribution.class.getName()).log(Level.SEVERE,null, ex);
        }
        return null;
    }
    // </editor-fold>
    public double[] BootStrap(){
        double[] result = new double[_PeriodOfRecord];
        java.util.Random Random = new java.util.Random();
        for(int i = 0; i<_PeriodOfRecord;i++){
            result[i] = GetInvCDF(Random.nextDouble());
        }
        return result;
    }
    public double[] BootStrap(long seed){
        double[] result = new double[_PeriodOfRecord];
        java.util.Random Random = new java.util.Random(seed);
        for(int i = 0; i<_PeriodOfRecord;i++){
            result[i] = GetInvCDF(Random.nextDouble());
        }
        return result;
    }
}
