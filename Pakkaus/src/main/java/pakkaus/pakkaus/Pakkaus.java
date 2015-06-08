/**
 * Pääohjelmalla voi tällä hetkellä testata merkkijono-muuttujassa määriteltävän
 * merkkijonon pakkaamista. Ohjelma toimii periaatteessa, joskin bugeja on vielä
 * korjattavana.
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
        File lahdetiedosto = new File("testidata.txt");
        FileInputStream syote = new FileInputStream(lahdetiedosto);
        BufferedInputStream psyote = new BufferedInputStream(syote);

        File kohdetiedosto = new File("pakattu.dat");
        FileOutputStream fos = new FileOutputStream(kohdetiedosto);
        BufferedOutputStream ptuote = new BufferedOutputStream(fos);

        psyote.mark(1024*1024);
        int alkuperainenPituus = psyote.available();

        String merkkijono = "tämä on esimerkki Huffman-koodauksesta";
//        merkkijono = "kissa";
//        merkkijono = "aaddddbbb";
//        tiheystaulu = HuffmanKoodaus.muodostaTiheystaulu(merkkijono);
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

        System.out.println("Kooditaulu: ");
        for (i = 0; i < kooditaulu.length; i++) {
            if (kooditaulu[i] != null) {
                System.out.println(i + " (" + ((char) i) + "): " + kooditaulu[i] + ", pituus: " +pituustaulu[i]);
            }
        }
//        String pakattuBittijono = HuffmanKoodaus.koodaaBittijonoksi(merkkijono, kooditaulu);
        psyote.reset();
        int jaannosbitit = HuffmanKoodaus.koodaaBittijonoksi(psyote, ptuote, kooditaulu);
        psyote.close();
        syote.close();
        ptuote.close();
        fos.close();
        System.out.println("Pakattu!");
        System.out.println("Jäännösbitit: " + jaannosbitit);

        File pakattutiedosto = new File("pakattu.dat");
        FileInputStream pakattu = new FileInputStream(pakattutiedosto);
        BufferedInputStream ppakattu = new BufferedInputStream(pakattu);

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
//        System.out.println(pakattuBittijono);
//        int bittipituus = pakattuBittijono.length();
//        System.out.println("Bittipituus: ");
//        System.out.println(bittipituus);
//        String pakattuMerkkijono = HuffmanKoodaus.bititMerkkijonoksi(pakattuBittijono);
//        System.out.println("Pakattu merkkijono: ");
//        System.out.println(pakattuMerkkijono);
//        String purettuBittijono = HuffmanKoodaus.merkitBittijonoksi(pakattuMerkkijono, bittipituus);
//        System.out.println(purettuBittijono);
//        if (pakattuBittijono.equals(purettuBittijono)) {
//            System.out.println("Bittijonot samat!");
//        }
//        tulosjono = HuffmanKoodaus.palautaMerkkijonoksi(purettuBittijono, kooditaulu);
//        System.out.println("tulosjono:");
//        System.out.println(tulosjono);
//        System.out.println("Alkuperäisen merkkijonon pituus: " + alkuperainenPituus);
//        System.out.println("Pakatun merkkijonon pituus ilman kooditaulua: " + pakattuMerkkijono.length());
//        System.out.println("Kooditaulun pituus: " + ktmerkkijono.length());
//        System.out.println("Pakkaussuhde: " + ((double) (pakattuMerkkijono.length())) / alkuperainenPituus);

    }

}
