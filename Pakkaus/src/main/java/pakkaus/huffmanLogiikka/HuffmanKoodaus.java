/**
 * HuffmanKoodaus-luokka tarjoaa merkkijonon Huffman-koodauksessa tarvittavat
 * apumetodit.
 */
package pakkaus.huffmanLogiikka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanKoodaus {

    /**
     * muodostaTiheystaulu-metodi muodostaa parametrinä annetun merkkijonon
     * pohjalta taulukon, josta käy ilmi jokaisen ASCII-merkin esiintymien
     * lukumäärä ko. merkkijonossa
     *
     * @param merkkijono
     * @return metodi palauttaa eri merkkien esiintymiskerrat sisältävän
     * int-taulukon
     */
    public static int[] muodostaTiheystaulu(String merkkijono) {

        int i;
        int[] taulukko = new int[256];

        for (i = 0; i < merkkijono.length(); i++) {
            taulukko[(merkkijono.charAt(i))]++;

        }
        return taulukko;

    }

    /**
     * muodostaHuffmanPuu-metodi muodostaa parametrinä annetun taulukon
     * perusteella Huffman-puun. Taulukko sisältää merkkien esiintymistiheydet
     * koodattavassa merkkijonossa. muodostaHuffmanPuu-metodi kutsuu ensin
     * apumetodia muodostaPrioriteettijono. Metodi poistaa prioriteettijonosta
     * aina kaksi alimman esiintymistiheyden omaavaa Huffman-solmua ja yhdistää
     * nämä uuden Huffman-solmun lapsiksi. Uusi solmu lisätään
     * prioriteettijonoon. Näin jatketaan, jonossa on jäljellä enää yksi solmu.
     * Tällöin Huffman-puu on valmis ja jäljelläoleva solmu on puun juuri.
     *
     * @param tiheystaulu; merkkien esiintymistiheydet sisältävä taulukko
     * @return metodi palauttaa muodostetun Huffman-puun juurisolmun
     */
    public static HuffmanSolmu muodostaHuffmanPuu(int[] tiheystaulu) {
        int i;
        HuffmanSolmu vasen, oikea;
        PriorityQueue<HuffmanSolmu> prjono = muodostaPrioriteettijono(tiheystaulu);

        /* Niin kauan kuin prioriteettijonossa on enemmän kuin yksi solmu, poistetaan jonosta
         kaksi pienimmän esiintymistiheyden solmua ja luodaan uusi solmu, josta tulee niiden
         vanhempi ja jonka esiintymistiheydeksi tulee lasten yhteenlaskettu esiintymistiheys.
         Lisätään uusi solmu prioriteettijonoon. Jatketaan tätä, kunnes jonossa on jäljellä
         enää yksi solmu.
         */
        while (prjono.size() > 1) {
            vasen = prjono.remove();
            oikea = prjono.remove();
//            System.out.println("Yhdistetään: " + vasen.getTiheys() + ", " + oikea.getTiheys());
            prjono.add(new HuffmanSolmu('\0', vasen.getTiheys() + oikea.getTiheys(), vasen, oikea));
        }
        return prjono.remove();

    }

    /**
     * muodostaPrioriteettijono-metodi luo jokaista merkkijonossa esiintyvää
     * merkkiä vastaavan Huffman-solmun ja lisää solmut prioriteettijonoon.
     *
     * @param tiheystaulu; taulukko, joka sisältää merkkien esiintymistiheyden
     * syötteenä olevassa merkkijonossa
     * @return metodi palauttaa prioriteettijonon, joka sisältää kaikkia
     * merkkijonossa esiintyneitä merkkejä vastaavat HuffmanSolmu-oliot
     */
    public static PriorityQueue<HuffmanSolmu> muodostaPrioriteettijono(int[] tiheystaulu) {
        int i;
        PriorityQueue<HuffmanSolmu> prjono = new PriorityQueue<HuffmanSolmu>();
        for (i = 0; i < tiheystaulu.length; i++) {
            if (tiheystaulu[i] > 0) {
                prjono.add(new HuffmanSolmu((char) i, tiheystaulu[i], null, null));
            }
        }
        return prjono;
    }

    /**
     * muodostaKooditaulu-metodi käy läpi parametrinä annetun Huffman-puun
     * esijärjestyksessä. Läpikäynnin yhteydessä se myös muodostaa merkkijonon,
     * joka muodostaa kussakin lehtisolmussa olevaa merkkiä vastaavan
     * binääriesityksen. Kun puussa edetään vasempaan haaraan (pienempi
     * esiintymistiheys), binääriesitykseen lisätään 0, kun taas oikeaan haaraan
     * edettäessä binääriesitykseen lisätään 1. Kun on saavutettu solmu,
     * lisätään kyseisen merkin kohdalle kooditauluun ko. merkin binääriesitys.
     *
     * @param solmu Huffman-puun juurisolmu
     * @param koodi koodi-muttuja pitää sisällään rekursion aikana
     * muodostettavan binääriesityksen
     * @param kooditaulu kooditaulu-taulukko sisältää kutakin merkkiä vastaavan
     * binääriesityksen merkkijonomuodossa
     */
    public static void muodostaKooditaulu(HuffmanSolmu solmu, String koodi, String[] kooditaulu) {
        /* Jos ollaan päädytty lehtisolmuun, lisätään sitä vastaava binääriesitys kooditauluun */
        if (solmu.onLehti()) {
            kooditaulu[(int) (solmu.getMerkki())] = koodi;
            return;
        }
        /* Edetään Huffman-puussa vasempaan haaraan */
        muodostaKooditaulu(solmu.getVasen(), koodi + "0", kooditaulu);
        /* Edetään Huffman-puussa oikeaan haaraan */
        muodostaKooditaulu(solmu.getOikea(), koodi + "1", kooditaulu);

    }
    
    /**
     * Metodi muodostaa taulukon, joka sisältää tiedon siitä, kuinka monella bitillä
     * kukin merkki esitetään pakatussa datassa.
     * 
     * @param kooditaulu taulukko, joka sisältää koodauksessa käytettävät binäärikoodit
     * @return paluuarvona on taulukko, joka sisältää merkkien binääriesitysten bittien määrät
     */

    public static int[] muodostaPituustaulu(String[] kooditaulu) {
        int i;
        int[] pituustaulu = new int[kooditaulu.length];
        for (i = 0; i < kooditaulu.length; i++) {
            if (kooditaulu[i] == null) {
                pituustaulu[i] = 0;
            } else {
                pituustaulu[i] = kooditaulu[i].length();
            }
        }
        return pituustaulu;

    }
    
    /**
     * Metodi muodostaa merkkijonoesityksen kooditaulusta.
     * 
     * @param kooditaulu Parametrinä annetaan taulukko, joka sisältää kunkin merkin binääriesityksen.
     * @return Paluuarvona kooditaulu merkkijonomuodossa. Merkit erotetaan toisistaan kaksoispisteellä.
     */

    public static String tallennaKooditaulu(String[] kooditaulu) {
        String tulos = "";
        int i;
        for (i = 0; i < kooditaulu.length; i++) {
            if (kooditaulu[i] != null) {
                tulos += "" + ((char) i);
                tulos += kooditaulu[i];
                tulos += ":";
            }
        }
        tulos += ":";
        return tulos;
    }
    
    /**
     * Metodi palauttaa edellisellä metodilla luodun kooditaulun merkkijonoesitysen taulukon muotoon.
     * 
     * @param mjono Kooditaulun merkkijonoesitys
     * @return kooditaulun taulukkoesitys
     */

    public static String[] muodostaKooditauluMerkkijonosta(String mjono) {
        String[] kooditaulu = new String[256];
        char c;
        int pituus;
        char koodi;
        String bittijono = "";
        int i = 0;
        while (i < mjono.length()) {
            c = mjono.charAt(i++);
            while (mjono.charAt(i) != ':') {
                bittijono += mjono.charAt(i++);
            }
            kooditaulu[c] = bittijono;
            bittijono = "";
            i++;
            if (mjono.charAt(i) == ':') {
                break;
            }

        }

        return kooditaulu;
    }
    
    /**
     * Metodi muodostaa merkkijonon, jossa alkuperäisen syötteen jokainen merkki on korvattu kooditaulun
     * mukaisella binääriesityksellä. Pakkausvaiheen metodi.
     * 
     * @param merkkijono alkuperäinen pakattava merkkijono
     * @param kooditaulu merkkien binäärikoodit sisältävä taulukko
     * @return merkkijono, joka sisältää alkuperäisen pakattavan merkkijonon binääriesityksen
     */

    public static String koodaaBittijonoksi(String merkkijono, String[] kooditaulu) {
        String pakattuBjono = "";

        for (int i = 0; i < merkkijono.length(); i++) {
            pakattuBjono += kooditaulu[merkkijono.charAt(i)];

        }
        return pakattuBjono;
    }
    
    /**
     * Purkamisen toisen vaiheen metodi, joka etsii bittijonomuodossa olevasta aineistosta sellaisia
     * bittisarjoja, joille löytyy kooditaulusta vastine. Paluuarvona on merkkijono, joka sisältää
     * kooditaulusta löytyneet bittisarjoja vastaavat merkit. Tämä on alkuperäinen syötteenä ohjelmalle
     * annettu merkkijono.
     * 
     * @param bittijono binääriesitys merkkijonomuodossa
     * @param kooditaulu taulukko, joka sisältää kunkin merkin binäärikoodauksen
     * @return palauttaa alkuperäiseen muotoonsa puretun merkkijonon
     */

    public static String palautaMerkkijonoksi(String bittijono, String[] kooditaulu) {
        String tulosjono = "";
        String verrattava = "";
        int i = 0;
        while (i < bittijono.length()) {
            verrattava += bittijono.charAt(i);
            for (int a = 0; a < kooditaulu.length; a++) {
                if (verrattava.equals(kooditaulu[a])) {
                    tulosjono += (char) a;
                    verrattava = "";
                    break;
                }
            }
            i++;
        }
        return tulosjono;

    }
    /**
     * Metodi toteuttaa purkamisen ensimmäisen vaiheen, eli muuttaa pakatun merkkijonon bittijonoksi.
     * 
     * @param merkkijono pakattu data
     * @param pituus pakkaamattoman bittijonon pituus, jota käytetään sen selvittämiseen, mitä tehdä
     * viimeisen, mahdollisesti epätäyden tavun kohdalla
     * @return bittiesitys merkkijonona
     */
    

    public static String merkitBittijonoksi(String merkkijono, int pituus) {
        int vajaatavu = pituus % 8;
        String apu;
        System.out.println(vajaatavu);
        String bittijono = "";
        int i;
        for (i = 0; i < merkkijono.length(); i++) {
            apu = Integer.toBinaryString(merkkijono.charAt(i));
            while (apu.length() < 8) {
                apu = "0" + apu;
            }
            bittijono += apu;
        }
//        apu = Integer.toBinaryString(merkkijono.charAt(i));
//        while (apu.length() < 8) {
//            apu = "0" + apu;
//        }
//        System.out.println("Viimeinen tavu: " + apu);
//        bittijono += apu.substring(0, vajaatavu);
        return bittijono;

    }
    
    /**
     * Metodi muuttaa alkuperäisen datan binääriesityksen jälleen merkkijonomuotoon jakamalla sen
     * kahdeksan bitin pätkiin ja korvaamalla kunkin pätkän vastaavalla merkillä. Pakkausvaiheen metodi.
     * 
     * @param bittijono Ykkösiä ja nollia sisältävä merkkijonoesitys.
     * @return merkkijono, bittijono on muutettu tavuittain merkeiksi
     */

    public static String bititMerkkijonoksi(String bittijono) {
        int i;
        char merkki;
        String tulosjono = "";
        String apujono = "";
        for (i = 0; i < bittijono.length(); i++) {
            apujono += bittijono.charAt(i);
            if (apujono.length() == 8) {
                merkki = (char) Integer.parseInt(apujono, 2);
                tulosjono += merkki;
                apujono = "";
            }

        }
        if (apujono.length() > 0) {
            System.out.println("Jäi ylimääräistä: " + apujono);
            while (apujono.length() < 8) {
                apujono += "0";
            }
            System.out.println("Täydennetty: " + apujono);
            merkki = (char) Integer.parseInt(apujono, 2);
            tulosjono += merkki;
        }

        return tulosjono;

    }

//    public static String[] kanonisoi(int[] pituustaulu, String kooditaulu[]) {
//        ArrayList<HuffmanPari> lista = new ArrayList<HuffmanPari>();
//        ArrayList<HuffmanBittijono> koodilista = new ArrayList<HuffmanBittijono>();
//        String[] kanonisoitu = new String[pituustaulu.length];
//        String bittijono = "";
//        int edPituus, uusi;
//        int i;
//        for (i = 0; i < pituustaulu.length; i++) {
//            if (pituustaulu[i] > 0) {
//                lista.add(new HuffmanPari(((char) i), pituustaulu[i]));
//                koodilista.add(new HuffmanBittijono(kooditaulu[i]));
//            }
//        }
//        Collections.sort(lista);
//        Collections.sort(koodilista);
//        for (i = 0; i < lista.size(); i++) {
//            kanonisoitu[lista.get(i).getMerkki()] = koodilista.get(i).get();
//        }
//        for (i = 0; i < lista.get(0).getPituus(); i++) {
//            bittijono += "0";
//        }
//        edPituus = bittijono.length();
//        uusi = Integer.parseInt(bittijono, 2);
//        kanonisoitu[lista.get(0).getMerkki()] = bittijono;
//
//        for (i = 1; i < lista.size(); i++) {
//            System.out.println(i + ": " + lista.get(i).getMerkki());
//            if (lista.get(i).getPituus() == edPituus) {
//                uusi++;
//                bittijono=Integer.toBinaryString(uusi);
//                kanonisoitu[lista.get(i).getMerkki()] = bittijono;
//                edPituus=bittijono.length();
//                continue;
//            }
//            if (lista.get(i).getPituus() > edPituus) {
//                uusi++;
//                bittijono = Integer.toBinaryString(uusi);
//                while(bittijono.length()<lista.get(i).getPituus())
//                    bittijono+="0";
//                kanonisoitu[lista.get(i).getMerkki()] = bittijono;
//                uusi = Integer.parseInt(bittijono, 2);
//                edPituus=bittijono.length();
//            }
//
//        }
//        return kanonisoitu;
//
//    }

}
