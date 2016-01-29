/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

/**
 *
 * @author Will_and_Sara
 */
public class ContinuousDistributionError {
    private String _ErrorMessage;
    public String ErrorMessage(){return _ErrorMessage;}
    public ContinuousDistributionError(String Message){
        _ErrorMessage = Message;
    }
}
