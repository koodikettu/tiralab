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
        long alkuperainenPituus=0;
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

        /* Lähdetiedosto, joka on tarkoitus tiivistää: testidata.txt */
        File lahdetiedosto = new File(tiedostonimi);
        FileInputStream syote = new FileInputStream(lahdetiedosto);
        BufferedInputStream psyote = new BufferedInputStream(syote);
        alkuperainen = new Tiedostonlukija(psyote);

        /* Tiivistetty lähdetiedosto tallennetaan pakattu.dat -tiedostoon. */
        File kohdetiedosto = new File("pakattu.dat");
        FileOutputStream fos = new FileOutputStream(kohdetiedosto);
        BufferedOutputStream ptuote = new BufferedOutputStream(fos);
        tk=new Tiedostonkirjoittaja(ptuote);
        

        psyote.mark(10 * 1024 * 1024);

        if (pakkaustapa == 1) {
            int[] tiheystaulu = new int[256];
            alkuaika = System.currentTimeMillis();
            alkuperainenPituus = HuffmanKoodaus.muodostaTiheystaulu(alkuperainen, tiheystaulu);
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
            int jaannosbitit = HuffmanKoodaus.koodaaBittijonoksi(alkuperainen, ptuote, kooditaulu);
            loppuaika = System.currentTimeMillis();

            /* suljetaan tiedostot */
            psyote.close();
            syote.close();
            ptuote.close();
            fos.close();
            System.out.println("Pakattu!");
            System.out.println("Jäännösbitit: " + jaannosbitit);

            pakkausaika = loppuaika - alkuaika;

            /* avataan pakattu tiedosto lukemista varten sen purkamiseksi */
            File pakattutiedosto = new File("pakattu.dat");
            FileInputStream pakattu = new FileInputStream(pakattutiedosto);
            BufferedInputStream ppakattu = new BufferedInputStream(pakattu);
            pakattudata = new Tiedostonlukija(ppakattu);
            pakattuPituus = ppakattu.available();

            /* avataan uusi tiedosto puretun datan kirjoittamista varten */
            purettutiedosto = new File("purettu.dat");
            FileOutputStream pfos = new FileOutputStream(purettutiedosto);
            BufferedOutputStream purettu = new BufferedOutputStream(pfos);

            System.out.println("Puretaan...");
            alkuaika = System.currentTimeMillis();
            HuffmanKoodaus.puraMerkkijonoksi(pakattudata, purettu, jaannosbitit, kooditaulu);
            System.out.println("Purettu!");
            loppuaika = System.currentTimeMillis();
            ppakattu.close();
            pakattu.close();
            purettu.close();
            pfos.close();
            purkuaika = loppuaika - alkuaika;

        } else {
            alkuaika = System.currentTimeMillis();
            LempelZivKoodaus.pakkaa(alkuperainen, tk);

            psyote.close();
            syote.close();
            ptuote.close();
            fos.close();
            loppuaika = System.currentTimeMillis();
            pakkausaika = loppuaika - alkuaika;
            /* avataan pakattu tiedosto lukemista varten sen purkamiseksi */
            File pakattutiedosto = new File("pakattu.dat");
            FileInputStream pakattu = new FileInputStream(pakattutiedosto);
            BufferedInputStream ppakattu = new BufferedInputStream(pakattu);
            pakattudata = new Tiedostonlukija(ppakattu);
            pakattuPituus = ppakattu.available();

            /* avataan uusi tiedosto puretun datan kirjoittamista varten */
            purettutiedosto = new File("purettu.dat");
            FileOutputStream pfos = new FileOutputStream(purettutiedosto);
            BufferedOutputStream purettu = new BufferedOutputStream(pfos);
            tk = new Tiedostonkirjoittaja(purettu);
            alkuaika = System.currentTimeMillis();
            LempelZivKoodaus.pura(pakattudata, tk);
            loppuaika = System.currentTimeMillis();
            purkuaika = loppuaika - alkuaika;

            ppakattu.close();
            pakattu.close();
            purettu.close();
            pfos.close();

        }
        System.out.println("Alkuperäisen tiedoston koko: " + alkuperainenPituus);
        System.out.println("Pakatun tiedoston koko: " + pakattuPituus);
        System.out.println("Pakkaamiseen kulunut aika " + pakkausaika + "  ms.");
        System.out.println("Purkamiseen kulunut aika " + purkuaika + "  ms.");
        System.out.println("Pakkaussuhde: " + (double) pakattuPituus / alkuperainenPituus);

        /* Lähdetiedosto, joka on tarkoitus tiivistää: testidata.txt */
        lahdetiedosto = new File(tiedostonimi);
        syote = new FileInputStream(lahdetiedosto);
        psyote = new BufferedInputStream(syote);
        Tiedostonlukija a = new Tiedostonlukija(psyote);
        /* avataan uusi tiedosto puretun datan kirjoittamista varten */
        purettutiedosto = new File("purettu.dat");
        FileInputStream tulos = new FileInputStream(purettutiedosto);
        BufferedInputStream ptulos = new BufferedInputStream(tulos);
        Tiedostonlukija b = new Tiedostonlukija(ptulos);

        System.out.print("Tarkistuksen tulos: ");
        System.out.println(Tarkastaja.vertaa(a, b));
        psyote.close();
        syote.close();
        ptulos.close();
        tulos.close();

    }

}
