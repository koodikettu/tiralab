/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.tiedostonhallinta;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author Markku
 */
public class Tiedostonkirjoittaja implements Kirjoittaja {

    private File kohdetiedosto;
    private FileOutputStream fos;
    private boolean tiedostonhallinta;
    private BufferedOutputStream bos;

    public Tiedostonkirjoittaja(BufferedOutputStream b) {
        this.bos = b;
        this.tiedostonhallinta=false;
    }

    public Tiedostonkirjoittaja(String tiedostonNimi) throws Exception {
        kohdetiedosto=new File(tiedostonNimi);
        fos=new FileOutputStream(kohdetiedosto);
        bos=new BufferedOutputStream(fos);
        this.tiedostonhallinta=true;
        
    }

    public void write(int b) throws Exception {
        bos.write(b);
    }
    
    public void close() throws Exception {
        this.bos.close();
        this.fos.close();
    }

}
