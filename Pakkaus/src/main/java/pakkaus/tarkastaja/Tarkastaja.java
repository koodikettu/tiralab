/**
 * Tarkastaja-luokan avulla voidaan tarkastaa, onko pakattu ja purettu tiedosto yhtenevä alkuperäisen
 * tiedoston kanssa.
 * 
 */
package pakkaus.tarkastaja;

import java.io.BufferedInputStream;
import pakkaus.tiedostonhallinta.Lukija;

/**
 *
 * @author Markku
 */
public class Tarkastaja {
    
    /**
     * Vertaa-metodi vertaa parametreinä saatuja Lukija-rajapinnan toteuttavia olioita. Tätä käytetään
     * sen tutkimiseen, onko pakkauksen ja purkamisen läpikäynyt data yhtenevä alkuperäisen datan kanssa.
     * @param ensimmainen Olio, jonka avulla päästään tutkimaan alkuperäistä dataa
     * @param toinen Olio, jonka avulla tutkitaan pakattua ja purettua dataa
     * @return palauttaa 0, jos parametreinä annettujen olioiden sisältämä data on yhtenevää.
     * @throws Exception 
     */
    
    public static int vertaa(Lukija ensimmainen, Lukija toinen) throws Exception {
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
