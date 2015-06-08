/**
 * Pääohjelmalla voi tällä hetkellä testata tiedoston pakkaamista.
 * Testiaineisto määritellään muuttujassa tiedostonimi. Tähän mennessä
 * havaitut bugit, joita viime viikolla vielä oli, on pääsääntöisesti korjattu.
 *
 */
package pakkaus.pakkaus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import pakkaus.huffmanLogiikka.HuffmanSolmu;
import pakkaus.huffmanLogiikka.HuffmanKoodaus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 *
 * @author Markku
 */
public class Pakkaus {

    /**
     * Pääohjelma, joka käyttää HuffmanKoodaus-luokan metodeja
     * Huffman-koodaukseen tarvittavien aputietorakenteiden muodostamiseen
     * annettuun merkkijonoon pohjautuen.
     *
     * @param args komentoriviargumentit. Ohjelma ei tällä hetkellä käytä
     * komentoriviargumentteja.
     */
    public static void main(String[] args) throws Exception {
        
        final String tiedostonimi="testidata.txt";
        
        int i;
        int[] tiheystaulu;
        int[] pituustaulu;
        String[] kooditaulu = new String[256];
        String[] kanonisoituKoodi = new String[256];
        String ktmerkkijono;
        String[] kooditaulu2 = new String[256];
        HuffmanSolmu huffmanPuu;


        /* Lähdetiedosto, joka on tarkoitus tiivistää: testidata.txt */
        File lahdetiedosto = new File(tiedostonimi);
        FileInputStream syote = new FileInputStream(lahdetiedosto);
        BufferedInputStream psyote = new BufferedInputStream(syote);

        /* Tiivistetty lähdetiedosto tallennetaan pakattu.dat -tiedostoon. */
        File kohdetiedosto = new File("pakattu.dat");
        FileOutputStream fos = new FileOutputStream(kohdetiedosto);
        BufferedOutputStream ptuote = new BufferedOutputStream(fos);

        psyote.mark(1024 * 1024);
        int alkuperainenPituus = psyote.available();

        tiheystaulu = HuffmanKoodaus.muodostaTiheystaulu(psyote);
        System.out.println("Tiheystaulu: ");
        for (i = 0; i < tiheystaulu.length; i++) {
            if (tiheystaulu[i] > 0) {
                System.out.println(i + " (" + ((char) i) + "): " + tiheystaulu[i]);
            }
        }
        System.out.println("");
        huffmanPuu = HuffmanKoodaus.muodostaHuffmanPuu(tiheystaulu);
        HuffmanKoodaus.muodostaKooditaulu(huffmanPuu, "", kooditaulu);
        pituustaulu = HuffmanKoodaus.muodostaPituustaulu(kooditaulu);
        System.out.println("Kooditaulu: ");
        for (i = 0; i < kooditaulu.length; i++) {
            if (kooditaulu[i] != null) {
                System.out.println(i + " (" + ((char) i) + "): " + kooditaulu[i] + ", pituus: " + pituustaulu[i]);
            }
        }

        ktmerkkijono = HuffmanKoodaus.tallennaKooditaulu(kooditaulu);
        System.out.println("Kooditaulu merkkijonona:");
        System.out.println(ktmerkkijono);
        System.out.println("Kooditaulun merkkkijonoesityksen pituus:");
        System.out.println(ktmerkkijono.length());
        kooditaulu = HuffmanKoodaus.muodostaKooditauluMerkkijonosta(ktmerkkijono);

        System.out.println("Merkkijonosta purettu kooditaulu: ");
        for (i = 0; i < kooditaulu.length; i++) {
            if (kooditaulu[i] != null) {
                System.out.println(i + " (" + ((char) i) + "): " + kooditaulu[i] + ", pituus: " + pituustaulu[i]);
            }
        }

        psyote.reset(); /* Palataan lukemaan lähdetiedostoa alusta */

        /* jaannosbitit kertovat viimeisen tallennettavan tavun "tehollisten" bittien määrän */
        int jaannosbitit = HuffmanKoodaus.koodaaBittijonoksi(psyote, ptuote, kooditaulu);

        /* suljetaan tiedostot */
        psyote.close();
        syote.close();
        ptuote.close();
        fos.close();
        System.out.println("Pakattu!");
        System.out.println("Jäännösbitit: " + jaannosbitit);

        /* avataan pakattu tiedosto lukemista varten sen purkamiseksi */
        File pakattutiedosto = new File("pakattu.dat");
        FileInputStream pakattu = new FileInputStream(pakattutiedosto);
        BufferedInputStream ppakattu = new BufferedInputStream(pakattu);
        int pakattuPituus = ppakattu.available();

        /* avataan uusi tiedosto puretun datan kirjoittamista varten */
        File purettutiedosto = new File("purettu.dat");
        FileOutputStream pfos = new FileOutputStream(purettutiedosto);
        BufferedOutputStream purettu = new BufferedOutputStream(pfos);

        System.out.println("Puretaan...");
        HuffmanKoodaus.puraMerkkijonoksi(ppakattu, purettu, jaannosbitit, kooditaulu);
        System.out.println("Purettu!");

        ppakattu.close();
        pakattu.close();
        purettu.close();
        pfos.close();

        System.out.println("Alkuperäisen tiedoston koko: " + alkuperainenPituus);
        System.out.println("Pakatun tiedoston koko: " + pakattuPituus);
        System.out.println("Pakkaussuhde: " + (double) pakattuPituus / alkuperainenPituus);

    }

}
