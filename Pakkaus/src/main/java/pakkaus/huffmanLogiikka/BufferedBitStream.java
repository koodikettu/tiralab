/**
 * Tämä luokka toteuttaa puskuroidun bittivirran, joka mahdollistaa datan
 * lukemisen tiedostosta bitti kerrallaan.
 *
 */
package pakkaus.huffmanLogiikka;

import java.io.BufferedInputStream;
import pakkaus.tiedostonhallinta.Lukija;

/**
 * Luokka lukee BufferedInputStreamistä dataa tavu kerrallaan ja tallettaa sen
 * binäärimerkkijonona muuttujaan tavuBitteinä. Metodi seuraava mahdollistaa
 * tavuBitteinä-muuttujan sisällön lukemisen bitti kerrallaan.
 *
 */
public class BufferedBitStream {

    private Lukija bis;
    String tavuBitteina;
    int b;
    int vajaatavu;

    public BufferedBitStream(Lukija bis, int vajaatavu) throws Exception {
        this.bis = bis;
        this.vajaatavu = vajaatavu;
        if (bis.available() > 0) {
//            System.out.println("Löytyy");
            tavuBitteina = Integer.toBinaryString(bis.read());
            taydenna();
        } else {
            System.out.println("Ei löydy mittään");
        }
    }

    /**
     * Metodi kertoo, onko bittejä vielä luettavissa tiedostosta.
     *
     * @return true, jos bittejä on luettavissa, muuten false
     */
    public boolean available() {
        if (tavuBitteina.length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * palauttaa seuraavan bitin
     *
     * @return joko '0' tai '1'
     * @throws Exception
     */
    public char seuraava() throws Exception {

        char a;
//        System.out.println("Seuraava pyydetty!");
//        System.out.println(this.tavuBitteina);
        if (tavuBitteina.length() == 1 && bis.available() == 1) {
            a = tavuBitteina.charAt(0);
            b = bis.read();
            tavuBitteina = Integer.toBinaryString(b);
            taydenna();
//            System.out.println("Käsittelyssä pakattu: " + b + ", " + tavuBitteina);
            if (vajaatavu > 0) {
                tavuBitteina = tavuBitteina.substring(0, vajaatavu);
            }
//            System.out.println("Käsitelty: " + tavuBitteina);
            return a;
        } else if (tavuBitteina.length() == 1 && bis.available() > 1) {
            a = tavuBitteina.charAt(0);
            tavuBitteina = Integer.toBinaryString(bis.read());
            taydenna();
            return a;
        } else if (tavuBitteina.length() == 1 && bis.available() == 0) {
            a = tavuBitteina.charAt(0);
            tavuBitteina = "";
            return a;
        } else {
            a = tavuBitteina.charAt(0);
            tavuBitteina = tavuBitteina.substring(1, tavuBitteina.length());
            return a;
        }
    }

    /**
     * Täydentää puuttuvat alkunollat, koska toBinaryString ei niitä huomioi.
     * Näin varmistetaan, että jokaisessa tavussa on täsmälleen 8 bittiä.
     *
     */
    public void taydenna() {
        while (tavuBitteina.length() < 8) {
            tavuBitteina = "0" + tavuBitteina;
        }
    }

    public String getTavuBitteina() {
        return this.tavuBitteina;
    }

}
