/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.tiedostonhallinta;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author Markku
 */
public class Tiedostonlukija implements Lukija {

    private File lahdetiedosto;
    private FileInputStream syote;
    private BufferedInputStream bis;
    private boolean tiedostonhallinta;

    public Tiedostonlukija(BufferedInputStream b) {
        this.bis = b;
        tiedostonhallinta = false;
    }

    public Tiedostonlukija(String tiedostoNimi) throws Exception {
        File lahdetiedosto = new File(tiedostoNimi);
        FileInputStream syote = new FileInputStream(lahdetiedosto);
        this.bis = new BufferedInputStream(syote);
        tiedostonhallinta = true;
    }

    @Override
    public int read() throws Exception {
        return this.bis.read();
    }

    @Override
    public int available() throws Exception {
        return this.bis.available();
    }

    public void close() throws Exception {
        if (tiedostonhallinta == false) {
            return;
        }
        bis.close();
        syote.close();

    }

    public void mark(int a) {
        if (tiedostonhallinta == false) {
            return;
        }
        syote.mark(a);
    }

    public void reset() throws Exception {
        if (tiedostonhallinta == false) {
            return;
        }
        syote.reset();
    }

}
