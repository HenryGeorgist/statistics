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
    abstract double GetInvCDF(double probability);
    abstract double GetCDF(double value);
    abstract double GetPDF(double value);
    public String[] GetParamNames(){
        Field[] flds = this.getClass().getDeclaredFields();
        String[] ParamNames = new String[flds.length];
        for(int i = 0; i< flds.length;i++){
            ParamNames[i] = flds[i].getName();
        }
        return ParamNames;
    }
    public double[] GetParamValues(){
        Field[] flds = this.getClass().getDeclaredFields();
        double[] ParamVals = new double[flds.length];
        for(int i = 0; i< flds.length;i++){
            try {
                ParamVals[i] = flds[i].getDouble(this);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ContinuousDistribution.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ParamVals;
    }
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
    // <editor-fold defaultstate="collapsed" desc="Reflection based utilities, Clone, equals, hash, ReadFromXML, WriteToXML">
    public ContinuousDistribution Clone(){
        //create a new continuousdistribution and populate it from this using reflection.
        ContinuousDistribution Dist = null;
        Class<?> c;
        try {
            c = Class.forName(this.getClass().getName());
            Dist=(ContinuousDistribution) c.getConstructor().newInstance();
            Field[] flds = c.getDeclaredFields();
            for (Field f : flds){
                f.set(Dist,f.getDouble(this));
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ContinuousDistribution.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Dist;
    }
    @Override
    public boolean equals(Object dist){
        if(dist.getClass().getName().equals(this.getClass().getName())){
            double[] thisParamValues = this.GetParamValues();
            ContinuousDistribution those = (ContinuousDistribution) dist;
            double[] thoseParamValues = those.GetParamValues();
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
        // how do i convert all double parameters into an integer representation?
        int hash = this.getClass().getName().hashCode();
        double[] vals = this.GetParamValues();
        Double d;
        for(int i = 0;i<vals.length; i++){
            d = vals[i];
            hash += d.hashCode();
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
                f.set(Dist,Double.parseDouble(ele.getAttribute(f.getName())));
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
                ele.setAttribute(f.getName(),Double.toString(f.getDouble(this)));
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
}
