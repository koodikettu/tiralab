/**
 * Tämä luokka toteuttaa Lempel-Ziv -koodauksessa tarvittavat toiminnot.
 * 
 */
package pakkaus.LempelZiv;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import pakkaus.tiedostonhallinta.Kirjoittaja;
import pakkaus.tiedostonhallinta.Lukija;

/**
 *
 * @author Markku
 */
public class LempelZivKoodaus {
    
    /**
     * pakkaa-metodi pakkaa annetun BufferedInputStreamin avulla luettavan tiedoston sisällön 
     * Lempel-Ziv -koodauksella.
     * @param lahde alkuperäisen pakattavan tiedoston käsittelyyn käytettävä Lukija -olio
     * @param kohde pakatun datan tiedostoon tallentamisessa käytetty Kirjoittaja -olio
     * @throws Exception 
     */

    public static void pakkaa(Lukija lahde, Kirjoittaja kohde) throws Exception {
        String[] sanakirja = new String[4096];
        int sanakirjaosoitin;
        int i;
        String ljono = "";
        String ujono = "";
        String bittijonoesitys = "";
        String tulosbittijono = "";

//        int vajaabitit = 0;

        for (i = 0; i < 256; i++) {
            sanakirja[i] = "" + (char) i;
        }
        sanakirjaosoitin = 256;
        while (lahde.available() > 0) {
            ujono = ljono + (char) lahde.read();
//            System.out.println("Ujono: " + ujono);
            if (loytyySanakirjasta(ujono, sanakirja, sanakirjaosoitin)) {
//                System.out.println("Löytyy!");
                ljono = ujono;
            } else {
//                System.out.println("Ei löydy!");
                if (sanakirjaosoitin < 4095) {
                    bittijonoesitys = Integer.toBinaryString(haeSanakirjasta(ljono, sanakirja, sanakirjaosoitin));
                    bittijonoesitys = taydenna(bittijonoesitys);
                    tulosbittijono += bittijonoesitys;
                    bittijonoesitys = "";
//                    System.out.println("Lisätty sanakirjaan " + sanakirjaosoitin + ": " + ujono);
                    sanakirja[sanakirjaosoitin] = ujono;
                    sanakirjaosoitin++;
                    ljono = "" + ujono.charAt(ujono.length() - 1);
//                    System.out.println("Ljono: " + ljono);
                } else {
                    bittijonoesitys = Integer.toBinaryString(haeSanakirjasta(ljono, sanakirja, sanakirjaosoitin));
                    bittijonoesitys = taydenna(bittijonoesitys);
                    tulosbittijono += bittijonoesitys;
                    ljono = "" + ujono.charAt(ujono.length() - 1);
//                    System.out.println("Ljono: " + ljono);
                }

            }
//            System.out.println("Bittijonon pituus: " + tulosbittijono.length());
            if (tulosbittijono.length() == 24) {
                kirjoita(kohde, tulosbittijono);
                tulosbittijono = "";
            }

        }

        if (ljono.length() != 0) {
            bittijonoesitys = Integer.toBinaryString(haeSanakirjasta(ljono, sanakirja, sanakirjaosoitin));
            bittijonoesitys = taydenna(bittijonoesitys);
            tulosbittijono += bittijonoesitys;
//            vajaabitit = tulosbittijono.length();
            while (tulosbittijono.length() < 24) {
                tulosbittijono += "111111111111";
            }
        }
        kirjoita(kohde, tulosbittijono);
        tulostaSanakirja(sanakirja, sanakirjaosoitin);
//        return vajaabitit;
    }
    
    /**
     * Pakkaamisessa käytettävä apumetodi, joka kertoo, löytyykö parametrinä annettu merkkijono
     * käytettävästä sanakirjasta.
     * @param mjono Tarkasteltavana oleva merkkijono.
     * @param sanakirja Pakkauksessa käytettävä sanakirja.
     * @param sanakirjaosoitin Kertoo ensimmäisen vapaan paikan sanakirjassa, eli sanakirjan alkioiden
     * määrän.
     * @return true, jos annettu merkkijono löytyi sanakirjasta, muuten false 
     */

    public static boolean loytyySanakirjasta(String mjono, String[] sanakirja, int sanakirjaosoitin) {
        int i;
        for (i = 0; i < sanakirjaosoitin; i++) {
            if (sanakirja[i].equals(mjono)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metodi palauttaa parametrinä annetun merkkijonon indeksin sanakirjassa.
     * @param mjono Tarkasteltavana oleva merkkijono.
     * @param sanakirja Käytettävä sanakirja.
     * @param sanakirjaosoitin Sanakirjan alkioiden lukumäärä.
     * @return parametrinä annetun merkkijonon indeksi sanakirjassa. -1 jos ei löydy sanakirjasta.
     */

    public static int haeSanakirjasta(String mjono, String[] sanakirja, int sanakirjaosoitin) {
        int i;
        for (i = 0; i < sanakirjaosoitin; i++) {
            if (sanakirja[i].equals(mjono)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Apumetodi, joka täydentää bittijonon 12 merkin mittaiseksi lisäämällä alkuun nollia, jos
     * bittijono on aluksi lyhyempi kuin 12 bittiä.
     * @param s alkuperäinen bittijono merkkijonomuodossa
     * @return täydennetty 12-merkkinen bittijono
     */

    public static String taydenna(String s) {
        String tulos = s;
        while (tulos.length() < 12) {
            tulos = "0" + tulos;
        }
        return tulos;

    }
    
    /**
     * Metodi kirjoittaa kahdesta 12-bittisestä kokonaisluvusta muodostettavat kolme 8-bittistä
     * tavua kohdetiedostoon.
     * @param kohde Kohdetiedoston käsittelyssä käytetty Kirjoittaja-olio.
     * @param bittijono Kirjoitettava 24-bittinen bittijono (merkkijonomuodossa).
     * @throws Exception 
     */

    public static void kirjoita(Kirjoittaja kohde, String bittijono) throws Exception {
        int eka, toka, kolmas;
//        System.out.println("Kirjoitetaan eka: " + Integer.parseInt(bittijono.substring(0,12),2));
//        System.out.println("Kirjoitetaan toka: " + Integer.parseInt(bittijono.substring(12,24),2));
        String a = bittijono.substring(0, 8);
        String b = bittijono.substring(8, 16);
        String c = bittijono.substring(16, 24);
        eka = Integer.parseInt(a, 2);
        toka = Integer.parseInt(b, 2);
        kolmas = Integer.parseInt(c, 2);
        kohde.write(eka);
        kohde.write(toka);
        kohde.write(kolmas);
//        System.out.println("Kirjoitettu: " + eka + ", " + toka + ", " + kolmas);
    }
    
    /**
     * Metodi purkaa Lempel-Ziv -pakatun datan parametrinä annetusta tiedostosta ja kirjoittaa puretun
     * datan toiseen tiedostoon
     * @param pakattu pakatun tiedoston käsittelyyn käytettävä Lukija-olio
     * @param purettu puretun datan tiedostoon tallettamiseen käytettävä Kirjoittaja-olio
     * @throws Exception 
     */

    public static void pura(Lukija pakattu, Kirjoittaja purettu) throws Exception {
        String[] sanakirja = new String[4096];
        Buffered12bitStream syote = new Buffered12bitStream(pakattu);
        String tulosjono = "";
        String mjono;
        String apu;
        int sanakirjaosoitin = 256;
        int i;
        for (i = 0; i < 256; i++) {
            sanakirja[i] = "" + (char) i;
        }

        mjono = "" + (char) syote.next();
        tulosjono = mjono;
        while (syote.available()) {
            i = syote.next();
            if(i==4095)
                return;
//            System.out.println("Luettu: " + i);
            if (i < sanakirjaosoitin) {
                apu = sanakirja[i];
            } else {
//                System.out.println("Ei löydy: " + mjono);
                apu = mjono + mjono.charAt(0);

            }
            tulosjono += apu;
            if (sanakirjaosoitin < 4095) {
                sanakirja[sanakirjaosoitin++] = mjono + apu.charAt(0);
            }
            mjono = apu;
            while (tulosjono.length() > 0) {
//                System.out.print(tulosjono);
                purettu.write(tulosjono.charAt(0));
                tulosjono = tulosjono.substring(1, tulosjono.length());
            }
        }


    }
    
    /**
     * Testaamiseen käytetty metodi, joka tulostaa muodostetun sanakirjan.
     * @param sanakirja Tulostettava sanakirja.
     * @param sanakirjaosoitin Sanakirjan alkioiden lukumäärä.
     */

    public static void tulostaSanakirja(String[] sanakirja, int sanakirjaosoitin) {
        for (int i = 0; i < sanakirjaosoitin; i++) {
            System.out.println(i + ": " + sanakirja[i]);
        }
    }

}
