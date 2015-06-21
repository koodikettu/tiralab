/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.pakkaus;

/**
 *
 * @author Markku
 */
public interface Lukija {
    
    public int read() throws Exception;
    
    public int available() throws Exception;
    
}
