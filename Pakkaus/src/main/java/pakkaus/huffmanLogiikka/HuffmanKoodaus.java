/**
 * HuffmanKoodaus-luokka tarjoaa merkkijonon Huffman-koodauksessa tarvittavat
 * apumetodit.
 */
package pakkaus.huffmanLogiikka;

import pakkaus.omatTietorakenteet.Prioriteettijono;
import pakkaus.tiedostonhallinta.Kirjoittaja;
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
     * muodostaTiheystaulu-metodi muodostaa parametrinä annetun
     * BufferedInputStreamin pohjalta taulukon, josta käy ilmi jokaisen
     * ASCII-merkin esiintymien lukumäärä ko. merkkijonossa. edellisen metodin
     * ylikuormitettu versio, joka ottaa parametrinä merkkijonomuuttujan sijaan
     * BufferedInputStream-olion
     *
     * @param syote Lukija-olio, jonka avulla luetaan dataa
     * tiedostosta
     * @return metodi palauttaa eri merkkien esiintymiskerrat sisältävän
     * int-taulukon
     */
    public static int[] muodostaTiheystaulu(Lukija syote) throws Exception {

        int i;
        int b;
        int[] taulu = new int[256];

        while (syote.available() > 0) {
            b = syote.read();
            taulu[b]++;

        }
        return taulu;

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
     * Metodi muodostaa headerin pakatulle tiedostolle, joka sisältää alkuperäisen datan
     * pituuden tavuina sekä Huffman-koodauksen purkamisessa tarvittavan kooditaulun.
     *
     * @param kooditaulu Parametrinä annetaan taulukko, joka sisältää kunkin
     * merkin binääriesityksen.
     * @param pituustaulu Taulukko, joka sisältää kunkin merkin binääriesityksen pituuden bitteinä.
     * @param alkuperainenPituus alkuperäisen, pakattavan tiedoston pituus tavuina
     * @return Paluuarvona kooditaulu merkkijonomuodossa. Merkit erotetaan
     * toisistaan kaksoispisteellä.
     */
    public static String tallennaHeader(String[] kooditaulu, int[] pituustaulu, int alkuperainenPituus) {
        String tulos = "" + alkuperainenPituus + ":";
        String apu;
        int i;
        for (i = 0; i < kooditaulu.length; i++) {
            if (kooditaulu[i] != null) {
                tulos += "" + ((char) i);
                tulos += (char) pituustaulu[i];
                apu = taydennaBittijono(kooditaulu[i], 32);
                tulos += (char) Integer.parseInt(apu.substring(0, 8),2);
                tulos += (char) Integer.parseInt(apu.substring(8, 16),2);
                tulos += (char) Integer.parseInt(apu.substring(16, 24),2);
                tulos += (char) Integer.parseInt(apu.substring(24, 32),2);
            }
        }
        tulos += "::";
        return tulos;
    }
    
    /**
     * Metodi lukee Lukija-olion kautta saatavan datan alusta headerin ja palauttaa
     * sen merkkijonomuodossa.
     * @param lukija Tiedoston lukemiseen käytetty olio.
     * @return Pakatun tiedoston header.
     * @throws Exception 
     */

    public static String lueHeader(Lukija lukija) throws Exception {
        String header = "";
        char c, prev;
        prev = (char) lukija.read();
        c = (char) lukija.read();
        header = "" + prev + c;
        while (!(c == ':' && prev == ':')) {
            prev = c;
            c = (char) lukija.read();
            header += c;
        }
        return header;

    }

    /**
     * Metodi palauttaa edellisellä metodilla luodun kooditaulun
     * merkkijonoesitysen taulukon muotoon.
     *
     * @param mjono Kooditaulun merkkijonoesitys
     * @return kooditaulun taulukkoesitys
     */
    public static int muodostaKooditauluMerkkijonosta(String mjono, String[] kooditaulu) {
        char c, ed;
        String pituus = "";
        int koodinPituus;
        int arvo;
        String bittijono;
        int alkuperaisenPituus;
        String koodi = "";
        int tavu1, tavu2, tavu3, tavu4;
        int i = 0;
        while (mjono.charAt(i) != ':') {
            pituus += mjono.charAt(i++);
        }
        i++;
        alkuperaisenPituus = Integer.parseInt(pituus);
        while (i < mjono.length() - 1) {
            c = mjono.charAt(i++);
            koodinPituus = (int) mjono.charAt(i++);
            if (c == ':' && koodinPituus == 58) {
                break;
            }
            tavu1 = (int) mjono.charAt(i++);
            tavu2 = (int) mjono.charAt(i++);
            tavu3 = (int) mjono.charAt(i++);
            tavu4 = (int) mjono.charAt(i++);
            arvo=tavu4+256*tavu3+256*256*tavu2+256*256*256*tavu1;


            kooditaulu[c] = taydennaBittijono(arvo, koodinPituus);
            koodinPituus = 0;

        }
        return alkuperaisenPituus;
    }

    /**
     * Metodi koodaa lähdetiedostosta luettavan datan jokaisen merkin sen
     * kooditaulusta löytyvällä binääriesityksellä ja tallettaa näin saadun
     * datan toiseen tiedostoon.
     *
     * @param syote Lukija-olio, jonka avulla luetaan dataa
     * tiedostosta.
     * @param tuote Kirjoittaja-olio, jonka avulla kirjoitetaan dataa
     * tiedostoon.
     * @param kooditaulu jokaisen merkin binäärikoodin sisältävä taulukko
     * @return palauttaa viimeisen mahdollisesti vajaaksi jäävän tavun
     * tehollisten bittien määrän
     * @throws Exception
     */
    public static int koodaaBittijonoksi(Lukija syote, Kirjoittaja tuote, String[] kooditaulu) throws Exception {
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
     * Metodi lukee dataa pakatusta tiedostosta ja etsii vastaavia bittijaksoja
     * kooditaulusta. Kun vastaava koodi löytyy, kirjoitetaan vastaava tavu
     * kohdetiedostoon. Lukeminen lopetetaan, kun pakattu data loppuu tai on purettu
     * alkuperäisen tiedoston pituuden verran tavuja.
     *
     * @param pakattu Lukija-olio, jonka avulla luetaan dataa
     * tiedostosta
     * @param purettu Kirjoittaja-olio, jonka avulla kirjoitetaan dataa
     * tiedostoon

     * @param kooditaulu kunkin merkin binäärikoodauksen sisältävä taulukko
     * @param alkuperaisenPituus alkuperäisen, pakkaamattoman tiedoston pituus tavuina
     * @throws Exception
     */
    public static void puraMerkkijonoksi(Lukija pakattu, Kirjoittaja purettu, String[] kooditaulu, int alkuperaisenPituus) throws Exception {
        String tulosjono = "";
        String verrattava = "";
        String puskuri = "";
        int laskuri=0;
        BufferedBitStream bbs = new BufferedBitStream(pakattu);

        while (bbs.available() == true) {
            if(laskuri==alkuperaisenPituus)
                break;
            verrattava += bbs.seuraava();
            for (int a = 0; a < kooditaulu.length; a++) {
                if (verrattava.equals(kooditaulu[a])) {
//                    System.out.println("Löytyi: " + verrattava);
                    purettu.write(a);
                    laskuri++;
                    verrattava = "";
                    break;
                }
            }

        }
//        System.out.println(bbs.available());

    }

    /**
     *
     * Metodi muodostaa merkkijonomuuttujassa annetun bittijonon perusteella
     * 8-bittisen tavun tallennettavaksi binääritiedostoon.
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
     * Metodi täydentää viimeisen pakattavan tavun, jos siihen ei tule täyttä 8
     * bittiä. perään lisätään nollia, kunnes tavussa on 8 bittiä.
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
    
    /**
     * Metodi muodostaa halutun pituisen bittijonon, joka kuvaa annettua kokonaislukuarvoa.
     * @param arvo Bittijonon esittämän kokonaisluvun arvo
     * @param pituus Bittijonon pituus
     * @return muodostettu bittijono merkkijonoesityksenä
     */

    public static String taydennaBittijono(int arvo, int pituus) {
        String tulos = Integer.toBinaryString(arvo);
        while (tulos.length() < pituus) {
            tulos = "0" + tulos;
        }
        return tulos;
    }
    
        /**
     * Metodi täydentää merkkijonona annetun bittijonon halutun pituiseksi.
     * @param arvo Alkuperäinen bittijono merkkijonomuodossa
     * @param pituus Haluttu bittijonon pituus
     * @return halutun pituiseksi täydennetty bittijono
     */

    public static String taydennaBittijono(String arvo, int pituus) {
        String tulos = arvo;
        while (tulos.length() < pituus) {
            tulos = "0" + tulos;
        }
        return tulos;
    }

}
