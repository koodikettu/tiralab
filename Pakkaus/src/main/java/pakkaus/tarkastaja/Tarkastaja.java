/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.tarkastaja;

import java.io.BufferedInputStream;

/**
 *
 * @author Markku
 */
public class Tarkastaja {
    
    public static int vertaa(BufferedInputStream ensimmainen, BufferedInputStream toinen) throws Exception {
        int i;
        int eka, toka;
        while(ensimmainen.available()!=0) {
            if(toinen.available()<1) {
                return 1; 
            }
            eka=ensimmainen.read();
            toka=toinen.read();
            if(eka!=toka)
                return 2;               
        }
        if (toinen.available()>0)
            return 3;
        return 0;
    }
    
}
