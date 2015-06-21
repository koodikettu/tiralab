/**
 * HuffmanKoodaus-luokka tarjoaa merkkijonon Huffman-koodauksessa tarvittavat
 * apumetodit.
 */
package pakkaus.huffmanLogiikka;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import pakkaus.omatTietorakenteet.Prioriteettijono;
import pakkaus.tiedostonhallinta.Lukija;

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
     * muodostaTiheystaulu-metodi muodostaa parametrinä annetun BufferedInputStreamin
     * pohjalta taulukon, josta käy ilmi jokaisen ASCII-merkin esiintymien
     * lukumäärä ko. merkkijonossa. edellisen metodin ylikuormitettu versio, joka ottaa
     * parametrinä merkkijonomuuttujan sijaan BufferedInputStream-olion
     *
     * @param syote BufferedInputStream-olio, jonka avulla luetaan dataa tiedostosta
     * @return metodi palauttaa eri merkkien esiintymiskerrat sisältävän
     * int-taulukon
     */


    public static long muodostaTiheystaulu(Lukija syote, int taulu[]) throws Exception {

        int i;
        int b;
        long laskuri=0;

        while (syote.available() > 0) {
            b = syote.read();
            laskuri++;
            taulu[b]++;

        }
        return laskuri;

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
        Prioriteettijono prjono = muodostaPrioriteettijono(tiheystaulu);

        /* Niin kauan kuin prioriteettijonossa on enemmän kuin yksi solmu, poistetaan jonosta
         kaksi pienimmän esiintymistiheyden solmua ja luodaan uusi solmu, josta tulee niiden
         vanhempi ja jonka esiintymistiheydeksi tulee lasten yhteenlaskettu esiintymistiheys.
         Lisätään uusi solmu prioriteettijonoon. Jatketaan tätä, kunnes jonossa on jäljellä
         enää yksi solmu.
         */
        while (prjono.koko() > 1) {
            vasen = prjono.delMin();
            oikea = prjono.delMin();
//            System.out.println("Yhdistetään: " + vasen.getTiheys() + ", " + oikea.getTiheys());
            prjono.insert(new HuffmanSolmu('\0', vasen.getTiheys() + oikea.getTiheys(), vasen, oikea));
        }
        return prjono.delMin();

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

    public static Prioriteettijono muodostaPrioriteettijono(int[] tiheystaulu) {
        int i;
        Prioriteettijono prjono = new Prioriteettijono();
        for (i = 0; i < tiheystaulu.length; i++) {
            if (tiheystaulu[i] > 0) {
                prjono.insert(new HuffmanSolmu((char) i, tiheystaulu[i], null, null));
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
     * Metodi muodostaa taulukon, joka sisältää tiedon siitä, kuinka monella
     * bitillä kukin merkki esitetään pakatussa datassa.
     *
     * @param kooditaulu taulukko, joka sisältää koodauksessa käytettävät
     * binäärikoodit
     * @return paluuarvona on taulukko, joka sisältää merkkien binääriesitysten
     * bittien määrät
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
     * @param kooditaulu Parametrinä annetaan taulukko, joka sisältää kunkin
     * merkin binääriesityksen.
     * @return Paluuarvona kooditaulu merkkijonomuodossa. Merkit erotetaan
     * toisistaan kaksoispisteellä.
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
        tulos += "::";
        return tulos;
    }

    /**
     * Metodi palauttaa edellisellä metodilla luodun kooditaulun
     * merkkijonoesitysen taulukon muotoon.
     *
     * @param mjono Kooditaulun merkkijonoesitys
     * @return kooditaulun taulukkoesitys
     */
    public static String[] muodostaKooditauluMerkkijonosta(String mjono) {
        String[] kooditaulu = new String[256];
        char c, ed;
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
            if (mjono.charAt(i) == ':' && mjono.charAt(i + 1) == ':') {
                break;
            }
        }

        return kooditaulu;
    }

/**
 * Metodi koodaa lähdetiedostosta luettavan datan jokaisen merkin sen kooditaulusta
 * löytyvällä binääriesityksellä ja tallettaa näin saadun datan toiseen tiedostoon.
 * @param syote BufferedInputStream-olio, jonka avulla luetaan dataa tiedostosta.
 * @param tuote BufferedOutputStream-olio, jonka avulla kirjoitetaan dataa tiedostoon.
 * @param kooditaulu jokaisen merkin binäärikoodin sisältävä taulukko
 * @return palauttaa viimeisen mahdollisesti vajaaksi jäävän tavun tehollisten bittien määrän
 * @throws Exception 
 */


    public static int koodaaBittijonoksi(Lukija syote, BufferedOutputStream tuote, String[] kooditaulu) throws Exception {
        String pakattuBjono = "";
        int tavu;
        int b;
        int jaannosbitit;

        while (syote.available() > 0) {
            b = syote.read();
//            System.out.println("Luettu merkki: " + (char) b + ": " + b);
            pakattuBjono += kooditaulu[b];
//            System.out.println("Käsittelyssä bittijono: " + pakattuBjono);
            while (pakattuBjono.length() > 7) {
                if (pakattuBjono.length() == 8) {
//                    System.out.println("Kirjoitetaan bittijono: " + pakattuBjono);
                    tavu = bittijonoTavuksi(pakattuBjono);
                    pakattuBjono = "";
                } else {
//                    System.out.println("Kirj. bittijono: " + pakattuBjono.substring(0, 8));
                    tavu = bittijonoTavuksi(pakattuBjono.substring(0, 8));
                    pakattuBjono = pakattuBjono.substring(8, pakattuBjono.length());
                }

                tuote.write(tavu);
            }

        }
        jaannosbitit = pakattuBjono.length();
        if (jaannosbitit > 0) {
            tavu = taydennaVajaaTavu(pakattuBjono);
            tuote.write(tavu);
        }

        return jaannosbitit;
    }

    /**
     * Metodi lukee dataa pakatusta tiedostosta ja etsii vastaavia bittijaksoja kooditaulusta. Kun
     * vastaava koodi löytyy, kirjoitetaan vastaava tavu kohdetiedostoon.
     * @param pakattu BufferedInputStream-olio, jonka avulla luetaan dataa tiedostosta
     * @param purettu BufferedOutputStream-olio, jonka avulla kirjoitetaan dataa tiedostoon
     * @param jaannosbitit viimeisessä (vajaassa) tavussa olevien tehollisten bittien määrä
     * @param kooditaulu kunkin merkin binäärikoodauksen sisältävä taulukko
     * @throws Exception 
     */

    public static void puraMerkkijonoksi(Lukija pakattu, BufferedOutputStream purettu, int jaannosbitit, String[] kooditaulu) throws Exception {
        String tulosjono = "";
        String verrattava = "";
        String puskuri = "";
        BufferedBitStream bbs = new BufferedBitStream(pakattu, jaannosbitit);

        while (bbs.available() == true) {
            verrattava += bbs.seuraava();
            for (int a = 0; a < kooditaulu.length; a++) {
                if (verrattava.equals(kooditaulu[a])) {
//                    System.out.println("Löytyi: " + verrattava);
                    purettu.write(a);
                    verrattava = "";
                    break;
                }
            }

        }
//        System.out.println(bbs.available());

    }

    



    
    
    /**
     * 
     * Metodi muodostaa merkkijonomuuttujassa annetun bittijonon perusteella 8-bittisen
     * tavun tallennettavaksi binääritiedostoon.
     * 
     * @param jono merkkijonona talletettu binääriluku
     * @return parametrin perusteella muodostettu 8-bittinen tavu
     */

    public static int bittijonoTavuksi(String jono) {
        int tavu;
        if (jono.length() == 8) {
            tavu = Integer.parseInt(jono, 2);
            return tavu;
        } else {
            tavu = Integer.parseInt(jono.substring(0, 8), 2);
            return tavu;
        }
    }
    
    /**
     * Metodi täydentää viimeisen pakattavan tavun, jos siihen ei tule täyttä 8 bittiä.
     * perään lisätään nollia, kunnes tavussa on 8 bittiä.
     * 
     * @param jono viimeisen vajaan tavun "teholliset" bitit
     * @return nollilla 8-bittiseksi täydennetty viimeinen tavu
     */

    public static int taydennaVajaaTavu(String jono) {
        while (jono.length() < 8) {
            jono = jono + "0";
        }
        return Integer.parseInt(jono, 2);
    }


}
