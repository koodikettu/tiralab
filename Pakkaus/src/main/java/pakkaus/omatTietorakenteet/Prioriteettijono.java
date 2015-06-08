/**
 * Luokka Prioriteettijono sisältää oman toteutuksen
 * minimikeko-tietorakenteesta, jota tarvitaan Huffman-koodauksessa Huffman-puun
 * luomiseen. Kekoon talletetaan Huffman-solmu -olioita, joiden suuruusjärjestys
 * on määritelty luokan compareTo- metodissa.
 */
package pakkaus.omatTietorakenteet;

import pakkaus.huffmanLogiikka.HuffmanSolmu;

/**
 * Prioriteettijono-luokka on perinteinen minimikeko-toteutus, joka sisältää
 * tyypilliset minimikeko-operaatiot eli heap-insert, heap-del-min ja heapify.
 * Lisäksi on toteutettu metodi koko, joka kertoo minimikeossa olevien
 *
 * @author Markku
 */
public class Prioriteettijono {

    int koko;
    HuffmanSolmu[] taulukko;

    public Prioriteettijono() {
        taulukko = new HuffmanSolmu[256 + 256];
        koko = 0;
    }

    /**
     * Heap-insert -operaation toteuttava metodi
     *
     * @param lisattava kekoon lisättävä HuffmanSolmu-olio
     */
    public void insert(HuffmanSolmu lisattava) {
        if (koko == 0) {
            taulukko[1] = lisattava;
            koko++;
            return;
        }
        koko++;
        int i = koko;
        while (i > 1 && taulukko[parent(i)].compareTo(lisattava) > 0) {
            taulukko[i] = taulukko[parent(i)];
            i = parent(i);
        }
        taulukko[i] = lisattava;

    }

    /**
     * Heapify-operaation toteuttava metodi.
     *
     * @param i Sen solmun indeksi, josta rekursiivinen heapify-operaatio
     * käynnistyy.
     */
    public void heapify(int i) {
        int max;
        HuffmanSolmu temp;
        int left = left(i);
        int right = right(i);
        if (right <= koko) {
            if (taulukko[left].compareTo(taulukko[right]) < 0) {
                max = left;
            } else {
                max = right;
            }
            if (taulukko[i].compareTo(taulukko[max]) > 0) {
                temp = taulukko[i];
                taulukko[i] = taulukko[max];
                taulukko[max] = temp;
                heapify(max);
            }

        } else if (left == koko && taulukko[i].compareTo(taulukko[left]) > 0) {
            temp = taulukko[i];
            taulukko[i] = taulukko[left];
            taulukko[left] = temp;
        }
    }

    /**
     * Delete-min -keko-operaation toteuttava metodi.
     *
     * @return Palauttaa pienimmän eli indeksissä 1 sijaitsevan
     * HuffmanSolmu-olion ja myös poistaa sen keosta.
     */
    public HuffmanSolmu delMin() {
        HuffmanSolmu min = taulukko[1];
        taulukko[1] = taulukko[koko];
        koko--;
        heapify(1);
        return min;
    }

    /**
     * Palauttaa indeksissä i olevan solmun vasemman lapsen indeksin.
     *
     * @param i sen solmun indeksi, jonka lapsen indeksi halutaan selvittää
     * @return vasemman lapsisolmun indeksi taulukossa
     */
    public int left(int i) {
        return i * 2;
    }

    /**
     * Palauttaa indeksissä i olevan solmun oikean lapsen indeksin.
     *
     * @param i sen solmun indeksi, jonka lapsen indeksi halutaan selvittää
     * @return oikean lapsisolmun indeksi taulukossa
     */
    public int right(int i) {
        return i * 2 + 1;
    }
    
    /**
     * Metodi palauttaa indeksissä i olevan solmun vanhemman indeksin.
     * 
     * @param i sen solmun indeksi, jonka vanhemman indeksi halutaan selvittää
     * @return parent-solmun indeksi taulukossa
     */

    public int parent(int i) {
        if (i % 2 == 1) {
            return (i - 1) / 2;

        } else {
            return i / 2;
        }
    }
    
    /**
     * Metodi tulostaa keon sisällön. Metodia käytetään vain testaustarkoituksiin.
     * 
     */

    public void tulosta() {
        for (int i = 1; i <= koko; i++) {
            System.out.println(i + ": " + taulukko[i].getMerkki() + ", " + taulukko[i].getTiheys());
        }
    }
    
    /**
     * Metodi palauttaa keossa olevien HuffmanSolmu-olioiden määrän.
     * @return 
     */

    public int koko() {
        return this.koko;
    }

}
