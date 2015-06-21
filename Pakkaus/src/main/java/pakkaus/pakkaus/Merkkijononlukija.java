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
public class Merkkijononlukija implements Lukija {
    
    String merkkijono;
    int index;
    
    public Merkkijononlukija(String s) {
        merkkijono=s;
        int index=0;
    }

    @Override
    public int read() {
        return (int) merkkijono.charAt(index++);
    }

    @Override
    public int available() {
        return merkkijono.length()-index;
    }
    
}
