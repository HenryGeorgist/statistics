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
}
