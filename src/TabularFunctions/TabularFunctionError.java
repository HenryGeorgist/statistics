/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabularFunctions;

/**
 *
 * @author Will_and_Sara
 */
public class TabularFunctionError {
    private final String _ErrorMessage;
    private final int _Row;
    private final String _ParameterName;
    private final String _DistributionType;
    public TabularFunctionError(String message, int row, String ParameterName, String DistributionType){
        _ErrorMessage = message;
        _Row = row;
        _ParameterName = ParameterName;
        _DistributionType = DistributionType;
    }
    public String GetErrorMessage(){return _ErrorMessage;}
    public int GetRow(){return _Row;}
    public String GetParameterName(){return _ParameterName;}
    public String GetDistributionType(){return _DistributionType;}
    public String GetFormattedError(){
        java.lang.StringBuilder SB = new java.lang.StringBuilder();
        SB.append("An error occured on row ");
        SB.append(_Row);
        SB.append(", for the parameter ");
        SB.append(_ParameterName);
        SB.append(" in a distribution of type ");
        SB.append(_DistributionType);
        SB.append(". The Error was: ");
        SB.append(_ErrorMessage);
        SB.append("\n");
        return SB.toString();
    }
}
