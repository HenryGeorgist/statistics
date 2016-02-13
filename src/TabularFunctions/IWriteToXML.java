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
public interface IWriteToXML {
    abstract public void ReadFromXMLElement(Element ele);
    abstract public Element WriteToXMLElement();
}
