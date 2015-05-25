/**
 *  HuffmanKoodaus-luokka tarjoaa merkkijonon Huffman-koodauksessa tarvittavat apumetodit.
 */
package pakkaus.huffmanLogiikka;

import java.util.PriorityQueue;


public class HuffmanKoodaus {
    
/**
 * muodostaTiheystaulu-metodi muodostaa parametrinä annetun merkkijonon pohjalta taulukon,
 * josta käy ilmi jokaisen ASCII-merkin esiintymien lukumäärä ko. merkkijonossa
 * @param merkkijono
 * @return metodi palauttaa eri merkkien esiintymiskerrat sisältävän int-taulukon
 */

    public static int[] muodostaTiheystaulu(String merkkijono) {

        int i;
        int[] taulukko = new int[256];

        for (i = 0; i < merkkijono.length(); i++) {
            taulukko[(int) (merkkijono.charAt(i))]++;

        }
        return taulukko;

    }
    
    /**
     * muodostaHuffmanPuu-metodi muodostaa parametrinä annetun taulukon perusteella
     * Huffman-puun. Taulukko sisältää merkkien esiintymistiheydet koodattavassa merkkijonossa.
     * muodostaHuffmanPuu-metodi kutsuu ensin apumetodia muodostaPrioriteettijono. Metodi poistaa
     * prioriteettijonosta aina kaksi alimman esiintymistiheyden omaavaa Huffman-solmua ja yhdistää
     * nämä uuden Huffman-solmun lapsiksi. Uusi solmu lisätään prioriteettijonoon. Näin jatketaan,
     * jonossa on jäljellä enää yksi solmu. Tällöin Huffman-puu on valmis ja jäljelläoleva solmu on
     * puun juuri.
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
     * muodostaPrioriteettijono-metodi luo jokaista merkkijonossa esiintyvää merkkiä vastaavan
     * Huffman-solmun ja lisää solmut prioriteettijonoon.
     * 
     * @param tiheystaulu; taulukko, joka sisältää merkkien esiintymistiheyden syötteenä olevassa
     * merkkijonossa
     * @return metodi palauttaa prioriteettijonon, joka sisältää kaikkia merkkijonossa esiintyneitä
     * merkkejä vastaavat HuffmanSolmu-oliot
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
     * muodostaKooditaulu-metodi käy läpi parametrinä annetun Huffman-puun esijärjestyksessä.
     * Läpikäynnin yhteydessä se myös muodostaa merkkijonon, joka muodostaa kussakin lehtisolmussa
     * olevaa merkkiä vastaavan binääriesityksen. Kun puussa edetään vasempaan haaraan (pienempi
     * esiintymistiheys), binääriesitykseen lisätään 0, kun taas oikeaan haaraan edettäessä
     * binääriesitykseen lisätään 1. Kun on saavutettu solmu, lisätään kyseisen merkin kohdalle
     * kooditauluun ko. merkin binääriesitys.
     * @param solmu Huffman-puun juurisolmu
     * @param koodi koodi-muttuja pitää sisällään rekursion aikana muodostettavan binääriesityksen
     * @param kooditaulu kooditaulu-taulukko sisältää kutakin merkkiä vastaavan binääriesityksen
     * merkkijonomuodossa
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

}
