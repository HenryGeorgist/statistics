/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;
import org.w3c.dom.*;
import java.lang.reflect.*;
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
    public abstract double GetInvCDF(double probability);
    public abstract double GetCDF(double value);
    public abstract double GetPDF(double value);
    public int GetPeriodOfRecord(){return _PeriodOfRecord;}
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
        public String[] GetParamNames(){
        Field[] flds = this.getClass().getDeclaredFields();
        String[] ParamNames = new String[flds.length];
        for(int i = 0; i< flds.length;i++){
            ParamNames[i] = flds[i].getName();
        }
        return ParamNames;
    }
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
        for(int i = 0;i<vals.length; i++){
            hash += vals[i].hashCode();
        }
        return hash;
    }
    public ContinuousDistribution ReadFromXML(Element ele) {
            ContinuousDistribution Dist = null;
            Class<?> c;
        try {
            c = Class.forName(ele.getTagName());
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
