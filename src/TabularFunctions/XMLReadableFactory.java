/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabularFunctions;

import org.w3c.dom.Element;

/**
 *
 * @author Will_and_Sara
 */
public class XMLReadableFactory {
    public static ISampleWithUncertainty ReadXMLElement(Element ele){
        switch(ele.getTagName()){
            case "MonotonicallyIncreasingCurveUncertain":
                return new MonotonicallyIncreasingCurveUncertain(ele);
            case "MonotonicallyIncreasingCurve":
                return new MonotonicallyIncreasingCurve(ele);
            default:
                return null;
        }
    }
}
