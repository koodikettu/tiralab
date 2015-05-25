/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
