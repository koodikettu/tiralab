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
public class HuffmanBittijono implements Comparable<HuffmanBittijono>{
    
    private String bittijono;
    
    public HuffmanBittijono(String bs) {
        this.bittijono=bs;
    }
    
    public String get() {
        return this.bittijono;
    }

    @Override
    public int compareTo(HuffmanBittijono o) {
        if(this.bittijono.length()==o.get().length()) {
            return this.bittijono.compareTo(o.get());
        }
        return this.bittijono.length()-o.get().length();
    }
    
    
}
