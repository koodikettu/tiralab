/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.tiedostonhallinta;

import java.io.BufferedOutputStream;

/**
 *
 * @author Markku
 */
public class Tiedostonkirjoittaja implements Kirjoittaja {
    private BufferedOutputStream bos;
    
    public Tiedostonkirjoittaja(BufferedOutputStream b) {
        this.bos=b;
    }
    
    public void write(int b) throws Exception {
        bos.write(b);
    }
    
}
