/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.tiedostonhallinta;

import java.io.BufferedInputStream;

/**
 *
 * @author Markku
 */
public class Tiedostonlukija implements Lukija {
    
    private BufferedInputStream bis;
    
    public Tiedostonlukija(BufferedInputStream b) {
        this.bis=b;
    }

    @Override
    public int read() throws Exception {
        return this.bis.read();
    }

    @Override
    public int available() throws Exception {
        return this.bis.available();
    }
    
}
