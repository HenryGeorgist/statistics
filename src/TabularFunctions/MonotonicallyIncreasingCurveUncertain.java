/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabularFunctions;

import Distributions.ContinuousDistribution;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Will_and_Sara
 */
public class MonotonicallyIncreasingCurveUncertain extends TabularFunction implements ISampleWithUncertainty{
    private ArrayList<Double> _X;
    private ArrayList<ContinuousDistribution> _Y;
    @Override
    public ArrayList<Double> GetXValues() {
        return _X;
    }
    @Override
    public ArrayList<ContinuousDistribution> GetYDistributions() {
        return _Y;
    }
    public MonotonicallyIncreasingCurveUncertain(ArrayList<Double> Xvalues, ArrayList<ContinuousDistribution> Yvalues){
        _X = Xvalues;
        _Y = Yvalues;
    }
    public MonotonicallyIncreasingCurveUncertain(Element ele){
        ReadFromXMLElement(ele);
    }
    @Override
    public double GetYFromX(double x, double probability) {
                //determine how to implement a binary search.
        int index = Collections.binarySearch(_X,x);
        //if index is negative, it should be (-(index)-1);
        if(index>0){
            return _Y.get(index).GetInvCDF(probability);
        }else{
            //interpolate. make sure the index is correctly determined.
            index *=-1;
            //check if index is larger than list
            if(index>=_Y.size()){return _Y.get(_Y.size() -1).GetInvCDF(probability);}
            double delta = _X.get(index) - _X.get(index-1);
            double distance = x-_X.get(index)/delta;
            double min =_Y.get(index-1).GetInvCDF(probability);
            double max =_Y.get(index).GetInvCDF(probability);
            double ydelta = max - min;//could use optimization to reduce number of samples.
            return _Y.get(index-1).GetInvCDF(probability) + ydelta*distance;
        }
    }
    @Override
    public ArrayList<Double> GetYValues(double probability) {
        ArrayList<Double> result = new ArrayList<>();
        for(int i =0; i <_Y.size();i++){
            result.add(_Y.get(i).GetInvCDF(probability));
        }
        return result;
    }
    @Override
    public FunctionTypeEnum FunctionType() {
        return FunctionTypeEnum.MonotonicallyIncreasingUncertain;
    }
    @Override
    public ArrayList<TabularFunctionError> Validate() {
        ArrayList<TabularFunctionError> output = new ArrayList<>();
        if(_Y.size() >= 1){return output;}
        String DistributionType = _Y.get(0).getClass().getName();
        double upper;
        double prevupper = _Y.get(0).GetInvCDF(.9999999999999);
        double lower;
        double prevlower = _Y.get(0).GetInvCDF(.0000000000001);
        double fifty;
        double prevfifty = _Y.get(0).GetInvCDF(.5);
        for(int i=1;i<_Y.size();i++){
            lower = _Y.get(i).GetInvCDF(.0000000000001);
            upper = _Y.get(i).GetInvCDF(.9999999999999);
            fifty = _Y.get(i).GetInvCDF(.5);
            if(prevlower>lower){
                output.add(new TabularFunctionError("Y is not monotonically increasing for the lower confidence limit.",i,"Y Value lower",DistributionType));
            }
            if(prevupper>upper){
                output.add(new TabularFunctionError("Y is not monotonically increasing for the upper confidence limit.",i,"Y Value upper",DistributionType));
            }
            if(prevfifty>fifty){
                output.add(new TabularFunctionError("Y is not monotonically increasing for the 50% value.",i,"Y Value",DistributionType));
            }
            prevlower = lower;
            prevupper = upper;
            prevfifty = fifty;
            if(_X.get(i-1)>_X.get(i)){
                //X is not monotonically increasing at row i.
                output.add(new TabularFunctionError("X is not monotonically increasing.",i,"X Value",DistributionType));
            } 
        }
        return output;
    }

    @Override
    public ISampleDeterministically CurveSample(double probability) {
        ArrayList<Double> samples = new ArrayList<>();
        for(int i = 0; i<_Y.size();i++){
            samples.add(_Y.get(i).GetInvCDF(probability));
        }
        return new MonotonicallyIncreasingCurve(_X,samples);
    }
    @Override
    public final void ReadFromXMLElement(Element ele) {
        _X = new ArrayList<>();
        _Y = new ArrayList<>();
        ContinuousDistribution Dist;
        Class<?> c;
        try {
            if(ele.hasAttribute("UncertaintyType")){
                if(ele.getAttribute("UncertaintyType").equals("None")){
                    // no distribution type called none, this should be a MonotonicallyIncreasingCurve
                }else{
                    c = Class.forName(ele.getAttribute("UncertaintyType"));
                    Dist=(ContinuousDistribution) c.getConstructor().newInstance();
                    Field[] flds = Dist.getClass().getDeclaredFields();
                    for(int i = 1; i < ele.getChildNodes().getLength();i++){
                        Dist=(ContinuousDistribution) c.getConstructor().newInstance();
                        Node N = ele.getChildNodes().item(i);
                        if(N.hasAttributes()){
                            Element ord = (Element)N;
                            _X.add(Double.parseDouble(ord.getAttribute("X")));
                            for(Field f : flds){
                                switch(f.getType().getName()){
                                    case "double":
                                        f.set(Dist,Double.parseDouble(ord.getAttribute(f.getName())));
                                        break;
            //                        case "int":
            //                            f.set(Dist,Integer.parseInt(ele.getAttribute(f.getName())));
            //                            break;
                                    default:
                                        //throw error?
                                        break;
                                }
                            }
                            _Y.add(Dist); 
                        }
                    }                    
                }
            }else{
                // no distribution type, this should be a MonotonicallyIncreasingCurve
            }

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MonotonicallyIncreasingCurveUncertain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public Element WriteToXMLElement() {
        try {
            DocumentBuilderFactory d = DocumentBuilderFactory.newInstance();
            DocumentBuilder Db;
            Db = d.newDocumentBuilder();
            Document doc = Db.newDocument();
            Element ele = doc.createElement(this.getClass().getName());
            ele.setAttribute("UncertaintyType", _Y.get(0).getClass().getName());
            Field[] flds = _Y.get(0).getClass().getDeclaredFields();
            Element ord;
            for(int i = 0; i<_Y.size();i++){
                ord = doc.createElement("Ordinate");
                ord.setAttribute("X", String.format("%.5f",_X.get(i)));
                for(Field f : flds){
                    try {
                        switch(f.getType().getName()){
                            case "double":
                                ord.setAttribute(f.getName(), Double.toString(f.getDouble(_Y.get(i))));
                                break;
                            default:
                                break; 
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(MonotonicallyIncreasingCurveUncertain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
            return ele;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MonotonicallyIncreasingCurveUncertain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
