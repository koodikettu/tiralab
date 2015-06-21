/**
 * Pääohjelmalla voi tällä hetkellä testata tiedoston pakkaamista. Testiaineisto
 * määritellään muuttujassa tiedostonimi. Tähän mennessä havaitut bugit, joita
 * viime viikolla vielä oli, on pääsääntöisesti korjattu.
 *
 */
package pakkaus.pakkaus;

import pakkaus.tiedostonhallinta.Tiedostonlukija;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import pakkaus.huffmanLogiikka.HuffmanSolmu;
import pakkaus.huffmanLogiikka.HuffmanKoodaus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import pakkaus.LempelZiv.LempelZivKoodaus;
import pakkaus.tarkastaja.Tarkastaja;
import pakkaus.tiedostonhallinta.Tiedostonkirjoittaja;

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

        String tiedostonimi;
        Scanner lukija;
        int i;
        long alkuaika, loppuaika;
        int[] pituustaulu;
        int pakkaustapa;
        int alkuperainenPituus = 0;
        String[] kooditaulu = new String[256];
        String[] kanonisoituKoodi = new String[256];
        String ktmerkkijono;
        String[] kooditaulu2 = new String[256];
        HuffmanSolmu huffmanPuu;
        int pakattuPituus;
        long pakkausaika, purkuaika;
        File purettutiedosto;
        Tiedostonlukija alkuperainen, pakattudata, purettudata;
        Tiedostonkirjoittaja tk;

        lukija = new Scanner(System.in);
        System.out.println("Anna pakattavan tiedoston nimi: ");
        tiedostonimi = lukija.nextLine();
        System.out.println("Valitse pakkaustapa: ");
        System.out.println("1. Huffman");
        System.out.println("2. Lempel-Ziv");
        pakkaustapa = Integer.parseInt(lukija.next());

        alkuperainen = new Tiedostonlukija(tiedostonimi);

        tk = new Tiedostonkirjoittaja("pakattu.dat");

        alkuperainen.mark(5 * 1024 * 1024);
        alkuperainenPituus = alkuperainen.available();

        if (pakkaustapa == 1) {
            int[] tiheystaulu = new int[256];

            alkuaika = System.currentTimeMillis();
            tiheystaulu = HuffmanKoodaus.muodostaTiheystaulu(alkuperainen);
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

            alkuperainen.reset(); /* Palataan lukemaan lähdetiedostoa alusta */

            /* jaannosbitit kertovat viimeisen tallennettavan tavun "tehollisten" bittien määrän */
            int jaannosbitit = HuffmanKoodaus.koodaaBittijonoksi(alkuperainen, tk, kooditaulu);
            loppuaika = System.currentTimeMillis();

            /* suljetaan tiedostot */
            alkuperainen.close();
            tk.close();
            System.out.println("Pakattu!");
            System.out.println("Jäännösbitit: " + jaannosbitit);

            pakkausaika = loppuaika - alkuaika;

            /* avataan pakattu tiedosto lukemista varten sen purkamiseksi */
            pakattudata = new Tiedostonlukija("pakattu.dat");
            pakattuPituus = pakattudata.available();

            /* avataan uusi tiedosto puretun datan kirjoittamista varten */
            tk = new Tiedostonkirjoittaja("purettu.dat");

            System.out.println("Puretaan...");
            alkuaika = System.currentTimeMillis();
            HuffmanKoodaus.puraMerkkijonoksi(pakattudata, tk, jaannosbitit, kooditaulu);
            System.out.println("Purettu!");
            loppuaika = System.currentTimeMillis();
            pakattudata.close();
            tk.close();
            purkuaika = loppuaika - alkuaika;

        } else {
            alkuaika = System.currentTimeMillis();
            LempelZivKoodaus.pakkaa(alkuperainen, tk);

            alkuperainen.close();
            tk.close();
            loppuaika = System.currentTimeMillis();
            pakkausaika = loppuaika - alkuaika;
            /* avataan pakattu tiedosto lukemista varten sen purkamiseksi */
            pakattudata = new Tiedostonlukija("pakattu.dat");
            pakattuPituus = pakattudata.available();

            /* avataan uusi tiedosto puretun datan kirjoittamista varten */
            tk = new Tiedostonkirjoittaja("purettu.dat");
            alkuaika = System.currentTimeMillis();
            LempelZivKoodaus.pura(pakattudata, tk);
            loppuaika = System.currentTimeMillis();
            purkuaika = loppuaika - alkuaika;

            pakattudata.close();
            tk.close();

        }
        System.out.println("Alkuperäisen tiedoston koko: " + alkuperainenPituus);
        System.out.println("Pakatun tiedoston koko: " + pakattuPituus);
        System.out.println("Pakkaamiseen kulunut aika " + pakkausaika + "  ms.");
        System.out.println("Purkamiseen kulunut aika " + purkuaika + "  ms.");
        System.out.println("Pakkaussuhde: " + (double) pakattuPituus / alkuperainenPituus);

        /* Lähdetiedosto, joka on tarkoitus tiivistää: testidata.txt */
        alkuperainen = new Tiedostonlukija(tiedostonimi);
        /* avataan uusi tiedosto puretun datan kirjoittamista varten */
        purettudata = new Tiedostonlukija("purettu.dat");

        System.out.print("Tarkistuksen tulos: ");
        System.out.println(Tarkastaja.vertaa(alkuperainen, purettudata));
        alkuperainen.close();
        purettudata.close();

    }

}
