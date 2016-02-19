/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.exceptions;

/**
 *
 * @author gusztafszon
 */
public class OldPasswordMisMatchException extends Exception{
    public OldPasswordMisMatchException(String message) {
        super(message);
    }
}
