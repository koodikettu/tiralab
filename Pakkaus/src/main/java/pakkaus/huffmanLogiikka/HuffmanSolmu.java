/**
 * HuffmanSolmu -luokka toteutta Huffman-puun solmun.
 */
package pakkaus.huffmanLogiikka;

/**
 *
 * @author Markku
 */
public class HuffmanSolmu implements Comparable<HuffmanSolmu> {
    
    private char merkki;
    private int tiheys;
    private HuffmanSolmu vasen;
    private HuffmanSolmu oikea;
    
    public HuffmanSolmu(char c, int esiintymistiheys, HuffmanSolmu vasenLapsi, HuffmanSolmu oikeaLapsi) {
        this.merkki=c;
        this.tiheys=esiintymistiheys;
        this.vasen=vasenLapsi;
        this.oikea=oikeaLapsi;
    }
    
    public HuffmanSolmu getVasen() {
        return this.vasen;
    }
    
    public HuffmanSolmu getOikea() {
        return this.oikea;
    }
    
    public int getTiheys() {
        return this.tiheys;
    }
    
    public char getMerkki() {
        return this.merkki;
    }
    
    /**
     * onLehti-metodi palauttaa tiedon, onko ko. solmu puun lehtisolmu vai ei
     * @return true, jos kyseessä on lehtisolmu, muuten false
     */
    
    public boolean onLehti() {
        if (this.vasen==null && this.oikea==null)
            return true;
        return false;
    }

    @Override
    public int compareTo(HuffmanSolmu o) {
        return this.tiheys - o.getTiheys();
    }
    
    
    
}
