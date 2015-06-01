/**
 * Pääohjelma testaa tällä hetkellä Huffmankoodaus-luokan tarjoamia metodeita,
 * jotka ovat muodostaTiheystaulu, muodostaHuffmanpuu ja muodostaKooditaulu.
 * Ohjelma tulostaa merkkijono-muuttujassa määriteltyyn merkkijonoon pohjautuvan
 * merkkieh tiheys- taulukon. Sen pohjalta luodaan Huffman-puu, jonka avulla
 * luodaan kooditaulu, joka sisältää eri merkkien Huffman-koodatun
 * binääriesityksen merkkijonomuodossa.
 */
package pakkaus.pakkaus;

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
     * Pääohjelma, joka käyttää HuffmanKoodaus-luokan metodeja Huffman-koodaukseen tarvittavien
     * aputietorakenteiden muodostamiseen annettuun merkkijonoon pohjautuen.
     * 
     * @param args komentoriviargumentit. Ohjelma ei tällä hetkellä käytä komentoriviargumentteja.
     */
    public static void main(String[] args) throws Exception {
        int i;
        int[] tiheystaulu;
        int[] pituustaulu;
        String[] kooditaulu = new String[256];
        String[] kanonisoituKoodi = new String[256];
        String ktmerkkijono;
        String[] kooditaulu2 = new String[256];
        HuffmanSolmu huffmanPuu;
        String pakattujono;
        String tulosjono;

        
        String merkkijono = "tämä on esimerkki Huffman-koodauksesta";
//        merkkijono = "kissa";
//        merkkijono = "aaddddbbb";
        tiheystaulu = HuffmanKoodaus.muodostaTiheystaulu(merkkijono);
        System.out.println("Tiheystaulu: ");
        for (i = 0; i < tiheystaulu.length; i++) {
            if (tiheystaulu[i] > 0) {
                System.out.println(i + " (" + ((char) i) + "): " + tiheystaulu[i]);
            }
        }
        System.out.println("");
        huffmanPuu = HuffmanKoodaus.muodostaHuffmanPuu(tiheystaulu);
        HuffmanKoodaus.muodostaKooditaulu(huffmanPuu, "", kooditaulu);
        pituustaulu=HuffmanKoodaus.muodostaPituustaulu(kooditaulu);
        System.out.println("Kooditaulu: ");
        for (i = 0; i < kooditaulu.length; i++) {
            if (kooditaulu[i] != null) {
                System.out.println(i + " (" + ((char) i) + "): " + kooditaulu[i] + ", pituus: " +pituustaulu[i]);
            }
        }

        ktmerkkijono=HuffmanKoodaus.tallennaKooditaulu(kooditaulu);
        System.out.println(ktmerkkijono);
        System.out.println(ktmerkkijono.length());
        kooditaulu=HuffmanKoodaus.muodostaKooditauluMerkkijonosta(ktmerkkijono);
        
        System.out.println("Kooditaulu: ");
        for (i = 0; i < kooditaulu.length; i++) {
            if (kooditaulu[i] != null) {
                System.out.println(i + " (" + ((char) i) + "): " + kooditaulu[i] + ", pituus: " +pituustaulu[i]);
            }
        }
        
        String pakattuBittijono=HuffmanKoodaus.koodaaBittijonoksi(merkkijono, kooditaulu);
        System.out.println(pakattuBittijono);
        int bittipituus=pakattuBittijono.length();
        System.out.println(bittipituus);
        String pakattuMerkkijono=HuffmanKoodaus.bititMerkkijonoksi(pakattuBittijono);
        System.out.println(pakattuMerkkijono);
        String purettuBittijono=HuffmanKoodaus.merkitBittijonoksi(pakattuMerkkijono, bittipituus);
        System.out.println(purettuBittijono);
        if(pakattuBittijono.equals(purettuBittijono))
            System.out.println("Bittijonot samat!");
        tulosjono=HuffmanKoodaus.palautaMerkkijonoksi(purettuBittijono, kooditaulu);
        System.out.println("tulosjono:");
        System.out.println(tulosjono);
        System.out.println("Alkuperäisen merkkijonon pituus: " + merkkijono.length());
        System.out.println("Pakatun merkkijonon pituus ilman kooditaulua: " + pakattuMerkkijono.length());
        System.out.println("Kooditaulun pituus: " + ktmerkkijono.length());
        System.out.println("Pakkaussuhde: " + ((double)(pakattuMerkkijono.length()))/merkkijono.length());

        


    }

    

    

}
