/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabularFunctions;

import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public abstract class TabularFunction {
    public abstract ArrayList<Double> GetXValues();
    //public abstract ArrayList<Object> GetYValues();
    public abstract FunctionTypeEnum FunctionType();
    public abstract ArrayList<TabularFunctionError> Validate();
    //WriteToXElement
    //ReadFromXElement
}
