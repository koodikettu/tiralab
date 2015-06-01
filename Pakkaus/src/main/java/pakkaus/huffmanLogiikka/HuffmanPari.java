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
public class HuffmanPari implements Comparable<HuffmanPari> {
    
    private char merkki;
    private int pituus;
    
    public HuffmanPari(char merkki, int pituus) {
        this.merkki=merkki;
        this.pituus=pituus;
    }
    
    public char getMerkki() {
        return this.merkki;
    }
    
    public int getPituus() {
        return this.pituus;
    }

    @Override
    public int compareTo(HuffmanPari o) {
        if(this.pituus==o.getPituus())
            return ((int) this.merkki) -((int) o.getMerkki());
        return this.pituus - o.getPituus();
    }
    
    
}
