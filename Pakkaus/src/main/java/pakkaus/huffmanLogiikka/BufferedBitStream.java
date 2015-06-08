/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.huffmanLogiikka;

import java.io.BufferedInputStream;

/**
 *
 * @author Markku
 */
public class BufferedBitStream {

    private BufferedInputStream bis;
    String tavuBitteina;
    int b;
    int vajaatavu;

    public BufferedBitStream(BufferedInputStream bis, int vajaatavu) throws Exception {
        this.bis = bis;
        this.vajaatavu = vajaatavu;
        if (bis.available() > 0) {
            System.out.println("Löytyy");
            tavuBitteina = Integer.toBinaryString(bis.read());
            taydenna();
        } else {
            System.out.println("Ei löydy mittään");
        }
    }

    public boolean available() {
        if (tavuBitteina.length() > 0) {
            return true;
        }
        return false;
    }

    public char seuraava() throws Exception {
        
        char a;
//        System.out.println("Seuraava pyydetty!");
//        System.out.println(this.tavuBitteina);
        if (tavuBitteina.length() == 1 && bis.available() == 1) {
            a = tavuBitteina.charAt(0);
            b=bis.read();
            tavuBitteina = Integer.toBinaryString(b);
            taydenna();
//            System.out.println("Käsittelyssä pakattu: " + b + ", " + tavuBitteina);
            tavuBitteina = tavuBitteina.substring(0, vajaatavu);
//            System.out.println("Käsitelty: " + tavuBitteina);
            return a;
        } else if (tavuBitteina.length() == 1 && bis.available() > 1) {
            a = tavuBitteina.charAt(0);
            tavuBitteina = Integer.toBinaryString(bis.read());
            taydenna();
            return a;
        } else if (tavuBitteina.length() == 1 && bis.available() ==0) {
            a = tavuBitteina.charAt(0);
            tavuBitteina="";
            return a;
        } else {
            a = tavuBitteina.charAt(0);
            tavuBitteina = tavuBitteina.substring(1, tavuBitteina.length());
            return a;
        }
    }
    
    public void taydenna() {
        while(tavuBitteina.length()<8)
            tavuBitteina="0"+tavuBitteina;
    }

}
